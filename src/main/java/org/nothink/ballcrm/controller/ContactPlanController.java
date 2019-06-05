package org.nothink.ballcrm.controller;

import org.nothink.ballcrm.entity.*;
import org.nothink.ballcrm.service.ContactPlanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    @GetMapping("plan")
    @ResponseBody
    public ResponseMsg getPlanList(PlanCriteria c){
        ResponseMsg out=new ResponseMsg("ok");
        logger.info("查询联系计划，页数："+c.getCurrentPage()+" 每页条数："+c.getPageSize());
        PagedResult<ContactPlanEntity> pagelist=planService.getPlanList(c);
        out.setData(pagelist);
        return out;
    }
}
