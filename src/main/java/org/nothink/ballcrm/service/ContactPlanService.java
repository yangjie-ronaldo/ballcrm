package org.nothink.ballcrm.service;

import ch.qos.logback.classic.Logger;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.nothink.ballcrm.entity.ContactPlanEntity;
import org.nothink.ballcrm.entity.PagedResult;
import org.nothink.ballcrm.mapper.ContactPlanMapper;
import org.nothink.ballcrm.util.CodeDef;
import org.nothink.ballcrm.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

@Service
public class ContactPlanService {
    @Autowired
    ContactPlanMapper planMapper;
    @Autowired
    CacheService cache;

    /**
     * 添加联系计划
     * @param plan
     * @return
     */
    @Transactional
    public int addNewPlan(ContactPlanEntity plan){
        if (StringUtils.isEmpty(plan.getEid()) || StringUtils.isEmpty(plan.getSid()))
            return 0;
        plan.setStatus(CodeDef.WAITPLAN);
        return planMapper.insert(plan);
    }


    /**
     * 查询联系计划列表
     * @param c
     * @return
     */
    public PagedResult<ContactPlanEntity> getPlanList(ContactPlanEntity c){
        Page p= PageHelper.startPage(c.getCurrentPage(), c.getPageSize());
        //执行查询
        if (c.getPlanDate()==null)
            //默认查当天
            c.setPlanDate(DateUtils.getToday());
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
     * 修改联系计划计划内容
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
        } else if (!relPlan.getStatus().equals(CodeDef.WAITPLAN)){
            //不是待处理的联系计划 不能修改
            return 0;
        } else {
            //能修改
            relPlan.setPlanNote(plan.getPlanNote());
            relPlan.setPlanDate(plan.getPlanDate());
            return planMapper.updateByPrimaryKeySelective(relPlan);
        }
    }

    private void CodeTrans(ContactPlanEntity p){
        p.setStatusDef(cache.CodeDefCache().get(p.getStatus()));
    }
}
