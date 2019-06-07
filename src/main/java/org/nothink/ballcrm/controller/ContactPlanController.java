package org.nothink.ballcrm.controller;

import org.nothink.ballcrm.entity.*;
import org.nothink.ballcrm.service.ContactPlanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ContactPlanController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    ContactPlanService planService;

    /**
     * 添加联系计划
     * @param plan
     * @return
     */
    @PostMapping("/plan")
    @ResponseBody
    public ResponseMsg addPlan(ContactPlanEntity plan){
        ResponseMsg out=new ResponseMsg("ok");
        int r=planService.addNewPlan(plan);
        if (r<=0){
            out.setMsg("失败");
            out.setCode(30001);
        }
        return out;
    }

    /**
     * 修改联系计划基本信息
     * @param plan
     * @return
     */
    @PutMapping("/plan")
    @ResponseBody
    public ResponseMsg updatePlanBase(ContactPlanEntity plan){
        ResponseMsg out=new ResponseMsg("ok");
        int r=planService.updatePlanBase(plan);
        if (r<=0){
            out.setMsg("不能修改本计划");
            out.setCode(30001);
        }
        return out;
    }

    /**
     * 完成联系计划
     * @param plan
     * @return
     */
    @PutMapping("/finishplan")
    @ResponseBody
    public ResponseMsg finishPlan(ContactPlanEntity plan){
        ResponseMsg out=new ResponseMsg("ok");
        int r=planService.finishPlan(plan);
        if (r==-1){
            out.setMsg("未到时间，不能完成");
            out.setCode(30001);
        }
        if (r==0){
            out.setMsg("完成出错");
            out.setCode(30001);
        }
        return out;
    }

    /**
     * 审核联系计划
     * @param plan
     * @return
     */
    @PutMapping("/verifyplan")
    @ResponseBody
    public ResponseMsg verifyPlan(ContactPlanEntity plan){
        ResponseMsg out=new ResponseMsg("ok");
        int r=planService.verifyPlan(plan);
        if (r==0){
            out.setMsg("审核出错");
            out.setCode(30001);
        }
        return out;
    }

    /**
     * 查询联系计划列表 默认查当天
     * @param c
     * @return
     */
    @GetMapping("plan")
    @ResponseBody
    public ResponseMsg getPlanList(ContactPlanEntity c){
        ResponseMsg out=new ResponseMsg("ok");
        logger.info("查询联系计划，页数："+c.getCurrentPage()+" 每页条数："+c.getPageSize());
        PagedResult<ContactPlanEntity> pagelist=planService.getPlanList(c);
        out.setData(pagelist);
        return out;
    }

    /**
     * 查询待审核的联系计划列表
     * @param c
     * @return
     */
    @GetMapping("verifyplan")
    @ResponseBody
    public ResponseMsg getVerifyPlanByEid(ContactPlanEntity c){
        ResponseMsg out=new ResponseMsg("ok");
        PagedResult<ContactPlanEntity> pagelist=planService.getVerifyPlanByEid(c);
        out.setData(pagelist);
        return out;
    }

}
