package org.nothink.ballcrm.controller;

import org.nothink.ballcrm.entity.EmpInfoEntity;
import org.nothink.ballcrm.entity.StatisticsCriteria;
import org.nothink.ballcrm.service.EmpInfoService;
import org.nothink.ballcrm.service.StatisticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class EmpController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    EmpInfoService eService;
    @Autowired
    StatisticsService statisticsService;

    /**
     * 查询本店员工列表 不分页
     * @param e
     * @return
     */
    @GetMapping("/emp")
    @ResponseBody
    public Map getEmpList(EmpInfoEntity e){
        return eService.getEmpList(e);
    }

    /**
     * 查询本店员工列表 分页
     * @param e
     * @return
     */
    @GetMapping("/emppaged")
    @ResponseBody
    public Map getEmpListPaged(EmpInfoEntity e){
        return eService.getEmpListPaged(e);
    }

    //添加员工 暂时不用
//    @PostMapping("/emp")
//    @ResponseBody
//    public Map addEmp(EmpInfoEntity e){
//        return eService.addEmp(e);
//    }

    /**
     * 注册用户
     */
    @PostMapping("/reg")
    @ResponseBody
    public Map regEmp(EmpInfoEntity e){
        return eService.register(e);
    }

    /**
     * 用户登录
     */
    @PostMapping("/login")
    @ResponseBody
    public Map loginEmp(EmpInfoEntity e){
        return eService.loginin(e);
    }

    /**
     * 修改用户信息
     */
    @PostMapping("/editemp")
    @ResponseBody
    public Map editEmp(@RequestBody EmpInfoEntity e){
        return eService.editEmp(e);
    }

    /**
     * 查询所有角色列表
     */
    @GetMapping("/roles")
    @ResponseBody
    public Map getRoles(){
        return eService.getAllRoles();
    }

    @GetMapping("/ccstatistics")
    @ResponseBody
    public Map getccStatistics(StatisticsCriteria c){return statisticsService.getCcStatistics(c);}

    /**
     * 注销用户
     */
    @GetMapping("/logout")
    @ResponseBody
    public Map logout(Integer eid){
        return eService.logout(eid);
    }

    @GetMapping("/emptodo")
    @ResponseBody
    public Map empTodoNum(Integer eid){
        return eService.getTodoNums(eid);
    }

    @GetMapping("/empinfo")
    @ResponseBody
    public Map getEmpInfo(EmpInfoEntity e){
        return eService.getEmpInfo(e);
    }

}
