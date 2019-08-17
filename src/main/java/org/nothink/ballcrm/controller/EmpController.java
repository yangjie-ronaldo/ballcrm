package org.nothink.ballcrm.controller;

import org.nothink.ballcrm.entity.EmpInfoEntity;
import org.nothink.ballcrm.entity.EmpPass;
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
@RequestMapping("/ballapi")
public class EmpController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    EmpInfoService eService;
    @Autowired
    StatisticsService statisticsService;

    /**
     * 查询本店员工列表 不分页
     *
     * @param e
     * @return
     */
    @GetMapping("/emp")
    @ResponseBody
    public Map getEmpList(EmpInfoEntity e) {
        return eService.getEmpList(e);
    }

    /**
     * 查询本店员工列表 分页
     *
     * @param e
     * @return
     */
    @GetMapping("/emppaged")
    @ResponseBody
    public Map getEmpListPaged(EmpInfoEntity e) {
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
    public Map regEmp(EmpInfoEntity e) {
        return eService.register(e);
    }

    /**
     * 删除用户
     */
    @DeleteMapping("/emp")
    @ResponseBody
    public Map deleteEmp(EmpInfoEntity e) {
        return eService.delEmp(e);
    }

    /**
     * 用户登录
     */
    @PostMapping("/login")
    @ResponseBody
    public Map loginEmp(EmpInfoEntity e) {
        return eService.loginin(e);
    }

    /**
     * 修改用户信息
     */
    @PostMapping("/editemp")
    @ResponseBody
    public Map editEmp(@RequestBody EmpInfoEntity e) {
        return eService.editEmp(e);
    }

    /**
     * 修改用户密码
     */
    @PutMapping("/editpass")
    @ResponseBody
    public Map editPass(EmpPass pass) {
        return eService.editPass(pass);
    }

    /**
     * 重置用户密码
     */
    @PutMapping("/resetpass")
    @ResponseBody
    public Map resetPass(EmpPass pass) {
        return eService.resetPass(pass);
    }

    /**
     * 查询所有角色列表
     */
    @GetMapping("/roles")
    @ResponseBody
    public Map getRoles() {
        return eService.getAllRoles();
    }

    /**
     * 用户登出
     */
    @GetMapping("/logout")
    @ResponseBody
    public Map logout(Integer eid) {
        return eService.logout(eid);
    }

    @GetMapping("/emptodo")
    @ResponseBody
    public Map empTodoNum(Integer eid) {
        return eService.getTodoNums(eid);
    }

    @GetMapping("/empinfo")
    @ResponseBody
    public Map getEmpInfo(EmpInfoEntity e) {
        return eService.getEmpInfo(e);
    }


    /**
     * 查询常规统计数量 版本1
     */
    @GetMapping("/ccstatistics")
    @ResponseBody
    public Map getccStatistics(StatisticsCriteria c) {
        return statisticsService.getCcStatistics(c);
    }

    /**
     * 查询常规统计数量 版本2
     */
    @GetMapping("/normalstatistics")
    @ResponseBody
    public Map getnormalStatistics(StatisticsCriteria c) {
        return statisticsService.getNormalStatistics(c);
    }

    /**
     * 查询学员按状态统计数量
     */
    @GetMapping("/stustatusnums")
    @ResponseBody
    public Map getStuStatusNums(StatisticsCriteria c) {
        return statisticsService.getStatusStatistics(c);
    }

    /**
     * 查询按课程买课关单统计
     */
    @GetMapping("/courseclosenums")
    @ResponseBody
    public Map getCourseCloseNums(StatisticsCriteria c) {
        return statisticsService.getCourseCloseNumStatistics(c);
    }

    /**
     * 查询课程买课配合老师统计
     */
    @GetMapping("/coursecloseteachernums")
    @ResponseBody
    public Map getCourseCloseTeacherNums(StatisticsCriteria c) {
        return statisticsService.getCourseCloseTeacherStatistics(c);
    }

    /**
     * 查询报表点击详情
     */
    @GetMapping("/statisticsdetails")
    @ResponseBody
    public Map getStatisticsDetails(StatisticsCriteria c) {
        return statisticsService.getStuDetailfromStatistics(c);
    }

}
