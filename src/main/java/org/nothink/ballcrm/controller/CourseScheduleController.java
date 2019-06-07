package org.nothink.ballcrm.controller;

import org.nothink.ballcrm.entity.*;
import org.nothink.ballcrm.service.CourseScheduleService;
import org.nothink.ballcrm.service.EmpInfoService;
import org.nothink.ballcrm.service.StuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@CrossOrigin
@Controller
public class CourseScheduleController {

    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    CourseScheduleService csService;

    /**
     * 约课
     * @param book
     * @return s
     */
    @PostMapping("/bookingcourse")
    @ResponseBody
    public ResponseMsg getStuById(CourseScheduleEntity book){
        ResponseMsg out=new ResponseMsg("ok");
        int i=csService.bookCourse(book);
        if (i==-1){
            out.setData("已有约课，不能再约");
        } else if (i==1){
            out.setData("成功");
        }
        return out;
    }

    /**
     * 查询学员上课历史
     * @return
     */
    @GetMapping("/courseschedule")
    @ResponseBody
    public ResponseMsg getCourseSchedule(CourseScheduleEntity c){
        ResponseMsg out=new ResponseMsg("ok");
        PagedResult<CourseScheduleEntity> pagelist=csService.CourseScheduleList(c);
        out.setData(pagelist);
        return out;
    }
}
