package org.nothink.ballcrm.controller;

import org.nothink.ballcrm.entity.ContactPlanEntity;
import org.nothink.ballcrm.entity.EmpInfoEntity;
import org.nothink.ballcrm.entity.PagedResult;
import org.nothink.ballcrm.entity.ResponseMsg;
import org.nothink.ballcrm.service.ContactPlanService;
import org.nothink.ballcrm.service.EmpInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class EmpController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    EmpInfoService eService;

    /**
     * 查询本店员工列表
     * @param e
     * @return
     */
    @GetMapping("/emp")
    @ResponseBody
    public ResponseMsg getEmpList(EmpInfoEntity e){
        ResponseMsg out=new ResponseMsg("ok");
        PagedResult<EmpInfoEntity> pagelist=eService.getEmpList(e);
        out.setData(pagelist);
        return out;
    }

    @PostMapping("/emp")
    @ResponseBody
    public ResponseMsg addEmp(EmpInfoEntity e){
        ResponseMsg out=new ResponseMsg("ok");
        int r=eService.addEmp(e);
        return out;
    }

}
