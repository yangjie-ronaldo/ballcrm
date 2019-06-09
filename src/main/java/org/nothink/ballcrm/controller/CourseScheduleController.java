package org.nothink.ballcrm.controller;

import org.nothink.ballcrm.entity.*;
import org.nothink.ballcrm.service.CourseScheduleService;
import org.nothink.ballcrm.util.ComUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@CrossOrigin
@Controller
public class CourseScheduleController {

    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    CourseScheduleService csService;

    //学员约课
    @PostMapping("/bookingcourse")
    @ResponseBody
    public Map getStuById(CourseScheduleEntity book){
        return csService.bookCourse(book);
    }

    /**
     * 查询学员上课历史
     * @return
     */
    @GetMapping("/courseschedule")
    @ResponseBody
    public ResponseMsg getCourseSchedule(CourseScheduleEntity c) {
        ResponseMsg out = new ResponseMsg("ok");
        PagedResult<CourseScheduleEntity> pagelist = csService.CourseScheduleList(c);
        out.setData(pagelist);
        return out;
    }

    /**
     * 查看明日上课提醒列表
     */
    @GetMapping("/notifylist")
    @ResponseBody
    public Map notifyScheduleList(CourseScheduleEntity cs){
        Map resp=ComUtils.getResp(20000,"查询成功",null);
        PagedResult list=csService.notifyScheduleList(cs);
        resp.put("data",list);
        return resp;
    }

    /**
     * 查看本日上课课程表
     */
    @GetMapping("/coursetoday")
    @ResponseBody
    public Map scheduleListToday(CourseScheduleEntity cs){
        Map resp=ComUtils.getResp(20000,"查询成功",null);
        PagedResult list=csService.scheduleListToday(cs);
        resp.put("data",list);
        return resp;
    }

    /**
     * 处理明日上课提醒
     */
    @PutMapping("/handlenotify")
    @ResponseBody
    public Map handleNotifySchedule(CourseScheduleEntity cs){
        Map resp=ComUtils.getResp(20000,"查询成功",null);
        int i=csService.handleScheduleNotify(cs);
        return resp;
    }

    /**
     * 本日上课跟进处理
     */
    @PutMapping("/handletrace")
    @ResponseBody
    public Map handleTrace(CourseScheduleEntity cs){
        Map resp=ComUtils.getResp(20000,"查询成功",null);
        int i=csService.handleScheduleToday(cs);
        return resp;
    }

    /**
     * 签到
     */
    @PutMapping("/signin")
    @ResponseBody
    public Map signIn(CourseScheduleEntity cs){
        return csService.signIn(cs);
    }
}
