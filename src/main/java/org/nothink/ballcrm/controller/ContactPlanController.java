package org.nothink.ballcrm.controller;

import org.nothink.ballcrm.entity.*;
import org.nothink.ballcrm.service.ContactPlanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@Controller
@RequestMapping("/ballapi")
public class ContactPlanController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    ContactPlanService planService;

    /**
     * 添加联系计划
     *
     * @param plan
     * @return
     */
    @PostMapping("/plan")
    @ResponseBody
    public Map addPlan(ContactPlanEntity plan) {
        return planService.addNewPlan(plan);
    }

    /**
     * 修改联系计划基本信息 废弃
     * @param plan
     * @return
     */
//    @PutMapping("/plan")
//    @ResponseBody
//    public ResponseMsg updatePlanBase(ContactPlanEntity plan){
//        ResponseMsg out=new ResponseMsg("ok");
//        int r=planService.updatePlanBase(plan);
//        if (r<=0){
//            out.setMsg("不能修改本计划");
//            out.setCode(30001);
//        }
//        return out;
//    }

    /**
     * 完成联系计划
     *
     * @param plan
     * @return
     */
    @PutMapping("/finishplan")
    @ResponseBody
    public Map finishPlan(ContactPlanEntity plan) {
        return planService.finishPlan(plan);
    }

    /**
     * 审核联系计划
     *
     * @param plan
     * @return
     */
    @PutMapping("/verifyplan")
    @ResponseBody
    public Map verifyPlan(ContactPlanEntity plan) {
        return planService.verifyPlan(plan);
    }

    /**
     * 查询联系计划列表 默认查当天
     *
     * @param c
     * @return
     */
    @GetMapping("plan")
    @ResponseBody
    public Map getPlanList(ContactPlanEntity c) {
        logger.info("查询联系计划，页数：" + c.getCurrentPage() + " 每页条数：" + c.getPageSize());
        return planService.getPlanList(c);
    }

    /**
     * 查询待审核的联系计划列表
     *
     * @param c
     * @return
     */
    @GetMapping("verifyplan")
    @ResponseBody
    public Map getVerifyPlanByEid(ContactPlanEntity c) {
        return planService.getVerifyPlanByEid(c);
    }

}
