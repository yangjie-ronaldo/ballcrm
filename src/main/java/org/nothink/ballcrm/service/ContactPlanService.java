package org.nothink.ballcrm.service;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.nothink.ballcrm.entity.ContactPlanEntity;
import org.nothink.ballcrm.entity.PagedResult;
import org.nothink.ballcrm.mapper.ContactPlanMapper;
import org.nothink.ballcrm.util.CodeDef;
import org.nothink.ballcrm.util.ComUtils;
import org.nothink.ballcrm.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ContactPlanService {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    ContactPlanMapper planMapper;
    @Autowired
    CacheService cache;
    @Autowired
    StuService stuService;

    /**
     * 添加联系计划
     * @param plan
     * @return
     */
    @Transactional
    public Map addNewPlan(ContactPlanEntity plan){
        if (StringUtils.isEmpty(plan.getEid()) || StringUtils.isEmpty(plan.getSid()))
            return ComUtils.getResp(40008,"无员工或学员编号",null);
        plan.setCreateDate(new Date());
        if (plan.getStatus()!=null && CodeDef.HANDLE_ABANDEN_STU.equals(plan.getStatus())){
            //放弃客户申请
        } else {
            //普通联系计划
            Date day30=new Date();
            //30天后
            day30=DateUtils.addDate(day30,0,0,30,0,0,0,0);
            if (DateUtils.isAfterOrEqual(plan.getPlanDate(),day30)){
                //30天后需审核
                plan.setStatus(CodeDef.HANDLE_WAITING_VERIFY);
            } else {
                plan.setStatus(CodeDef.HANDLE_WAITING);
            }
        }
        int i=planMapper.insert(plan);
        if (i>0)
            return ComUtils.getResp(20000,"新建联系计划成功",null);
        else
            return ComUtils.getResp(40008,"新建联系计划失败",null);
    }


    /**
     * 查询联系计划列表 通用
     * @param c
     * @return
     */
    public PagedResult<ContactPlanEntity> getPlanList(ContactPlanEntity c){
        Page p= PageHelper.startPage(c.getCurrentPage(), c.getPageSize());
        //执行查询
        if (c.getPlanDate()==null && c.getStartDate()==null && c.getEndDate()==null) {
            //默认查当天
            c.setPlanDate(DateUtils.getToday());
        }
        System.out.println(c);
        List<ContactPlanEntity> list = planMapper.getPlanList(c);
        PagedResult<ContactPlanEntity> result = new PagedResult<>(c.getCurrentPage(), c.getPageSize(), (int) p.getTotal());
        //翻译代码值
        if (list!=null)
            for (ContactPlanEntity plan:list)
                CodeTrans(plan);
        result.setItems(list);
        return result;
    }

    /**
     * 查询待审核联系计划列表 按主管id
     * @param c
     * @return
     */
    public PagedResult<ContactPlanEntity> getVerifyPlanByEid(ContactPlanEntity c){
        Page p= PageHelper.startPage(c.getCurrentPage(), c.getPageSize());
        List<ContactPlanEntity> list = planMapper.getVerifyPlanByEid(c);
        PagedResult<ContactPlanEntity> result = new PagedResult<>(c.getCurrentPage(), c.getPageSize(), (int) p.getTotal());
        //翻译代码值
        if (list!=null)
            for (ContactPlanEntity plan:list)
                CodeTrans(plan);
        result.setItems(list);
        return result;
    }

    /**
     * 修改联系计划计划内容 废弃
     * @param plan
     */
    public int updatePlanBase(ContactPlanEntity plan){
        Date today=DateUtils.getToday();
        if (plan.getPlanDate()!=null && DateUtils.isBefore(plan.getPlanDate(),today)){
            return 0;
        }

        ContactPlanEntity relPlan = planMapper.selectByPrimaryKey(plan.getPkid());
        if (relPlan==null){
            return 0;
        } else if (!relPlan.getStatus().equals(CodeDef.HANDLE_WAITING)){
            //不是待处理的联系计划 不能修改
            return 0;
        } else {
            //能修改
            relPlan.setPlanNote(plan.getPlanNote());
            relPlan.setPlanDate(plan.getPlanDate());
            return planMapper.updateByPrimaryKeySelective(relPlan);
        }
    }

    /**
     * 完成联系计划
     * @param plan
     */
    @Transactional
    public Map finishPlan(ContactPlanEntity plan){
        Date now=new Date();
        ContactPlanEntity rel=planMapper.selectByPrimaryKey(plan.getPkid());
        if (rel==null){
            return ComUtils.getResp(40008,"无此联系计划信息",null);
        }
        if (DateUtils.isBefore(now,rel.getPlanDate())){
            //处理时间小于计划时间  不能完成
            return ComUtils.getResp(40008,"未到计划联系时间，不能处理",null);
        }
        // 完成计划
        rel.setStatus(CodeDef.HANDLED);
        rel.setFinishDate(now);
        rel.setFinishNote(plan.getFinishNote());
        int r=planMapper.updateByPrimaryKeySelective(rel);
        if (r>0)
            return ComUtils.getResp(20000,"处理成功",null);
        else
            return ComUtils.getResp(40008,"处理失败",null);
    }

    /**
     * 审核联系计划
     * @param plan
     */
    @Transactional
    public int verifyPlan(ContactPlanEntity plan){
        Date now=new Date();
        ContactPlanEntity rel=planMapper.selectByPrimaryKey(plan.getPkid());
        if (rel==null){
            return 0;
        }
        // 审核联系计划
        rel.setStatus(plan.getStatus());
        rel.setVerifyEid(plan.getVerifyEid());
        rel.setVerifyNote(plan.getVerifyNote());
        if (CodeDef.HANDLE_ABANDEN_STU.equals(plan.getStatus())){
            System.out.println("进入放弃客户");
            //主管同意放弃客户，将客户变为 候选客户
            stuService.abandonStu(rel.getSid());
            rel.setStatus(CodeDef.HANDLED);
            rel.setFinishNote("同意放弃客户");
            rel.setFinishDate(now);
        }
        // 更新本联系计划
        int r=planMapper.updateByPrimaryKeySelective(rel);
        return r;
    }

    private void CodeTrans(ContactPlanEntity p){
        if (p!=null){
            //代码
            p.setStatusDef(cache.CodeDefCache().get(p.getStatus()));
            //员工名
            p.seteName(cache.EmpCache().get(p.getEid()));
            p.setVerifyEName(cache.EmpCache().get(p.getVerifyEid()));
        }

    }
}
