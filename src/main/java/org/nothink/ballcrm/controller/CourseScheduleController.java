package org.nothink.ballcrm.controller;

import org.nothink.ballcrm.entity.*;
import org.nothink.ballcrm.service.CourseScheduleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/ballapi")
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
    public Map getCourseSchedule(CourseScheduleEntity c) {
        return csService.CourseScheduleList(c);
    }

    /**
     * 查看明日上课提醒列表
     */
    @GetMapping("/notifylist")
    @ResponseBody
    public Map notifyScheduleList(CourseScheduleEntity cs){
        return csService.notifyScheduleList(cs);
    }

    /**
     * 查看本日上课课程表
     */
    @GetMapping("/coursetoday")
    @ResponseBody
    public Map scheduleListToday(CourseScheduleEntity cs){
        return csService.scheduleListToday(cs);
    }

    /**
     * 处理明日上课提醒
     */
    @PutMapping("/handlenotify")
    @ResponseBody
    public Map handleNotifySchedule(CourseScheduleEntity cs){
        return csService.handleScheduleNotify(cs);
    }

    /**
     * 本日上课跟进处理
     */
    @PutMapping("/handletrace")
    @ResponseBody
    public Map handleTrace(CourseScheduleEntity cs){
        return csService.handleScheduleToday(cs);
    }

    /**
     * 签到
     */
    @PutMapping("/signin")
    @ResponseBody
    public Map signIn(CourseScheduleEntity cs){
        return csService.signIn(cs);
    }

    /**
     * 撤销签到
     * @param cs
     * @return
     */
    @PutMapping("/signreverse")
    @ResponseBody
    public Map SignInReverse(CourseScheduleEntity cs){return csService.SignInReverse(cs);}
}
