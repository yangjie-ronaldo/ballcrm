package org.nothink.ballcrm.controller;

import org.nothink.ballcrm.entity.*;
import org.nothink.ballcrm.mapper.StuMapper;
import org.nothink.ballcrm.service.EmpInfoService;
import org.nothink.ballcrm.service.StuService;
import org.nothink.ballcrm.util.ComUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/ballapi")
public class StuController {

    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    EmpInfoService empInfoService;
    @Autowired
    StuService stuService;

    /**
     * 按学员编号查询某个学员
     *
     * @param sid
     * @return s
     */
    @GetMapping("/stu/{sid}")
    @ResponseBody
    public Map getStuById(@PathVariable(value = "sid") int sid) {
        return ComUtils.getResp(20000, "查询成功", stuService.findById(sid));
    }

    /**
     * 查询所有学员(有条件查询、分页)
     *
     * @param c
     * @return out
     */
    @GetMapping("/stu")
    @ResponseBody
    public Map getAll(StuEntity c) {
        return stuService.getAllByCriteria(c);
    }

    //查询无追踪的学员列表
    @GetMapping("/stunotrace")
    @ResponseBody
    public Map getNoTraceStu(StuEntity c) {
        return stuService.getNoTraceStu(c);
    }

    /**
     * 主管查询198学员情况列表
     * @param c
     * @return
     */
    @GetMapping("/stu198")
    @ResponseBody
    public Map getStu198List(StuEntity c){return stuService.getStu198List(c);}

    /**
     * 新增学员
     *
     * @param c
     * @return
     */
    @PostMapping("/stu")
    @ResponseBody
    public Map insertStu(StuEntity c) {
        return stuService.addOne(c);
    }

    /**
     * 修改学员基本信息
     *
     * @param c
     * @return
     */
    @PutMapping("/stu")
    @ResponseBody
    public Map updateStu(StuEntity c) {
        return stuService.updateStuBySid(c);
    }

    /**
     * 查询学员成长历史
     *
     * @return
     */
    @GetMapping("/stustatus")
    @ResponseBody
    public Map getStuStatusHis(StuEntity c) {
        return stuService.getStuStatusList(c);
    }

    /**
     * 查询学员课程列表
     *
     * @return
     */
    @GetMapping("/stucourse")
    @ResponseBody
    public Map getStuCourse(StuEntity c) {
        return stuService.getStuCourseList(c);
    }

    /**
     * 查询学员能约课程
     *
     * @return
     */
    @GetMapping("/stucoursetobook")
    @ResponseBody
    public Map getStuCourseToBook(StuEntity c) {
        return stuService.getStuCourseToBook(c);
    }

    /**
     * 学员买课
     *
     * @return
     */
    @PostMapping("/stucourse")
    @ResponseBody
    public Map buyCourse(StuCourseEntity sc) {
        return stuService.buyCourse(sc);
    }

    /**
     * 查询能买的课程列表
     *
     * @return
     */
    @GetMapping("/courseforbuy")
    @ResponseBody
    public Map courseBuyList() {
        return stuService.courseBuyList();
    }

    /**
     * 查询学员家庭信息
     *
     * @return
     */
    @GetMapping("/stufamily")
    @ResponseBody
    public Map getStuFamily(StuFamilyEntity c) {
        return stuService.getStuFamily(c);
    }

    /**
     * 修改学员家庭信息
     *
     * @return
     */
    @PutMapping("/stufamily")
    @ResponseBody
    public Map saveStuFamily(StuFamilyEntity c) {
        return stuService.saveStuFamily(c);
    }

    /**
     * 查询待转移学员列表
     *
     * @param c
     * @return
     */
    @GetMapping("/stutrans")
    @ResponseBody
    public Map getTransStuList(StuTransCriteria c) {
        return stuService.getTransStuList(c);
    }

    /**
     * 执行学员转移
     * @param c
     * @return
     */
    @PutMapping("/dotrans")
    @ResponseBody
    public Map doTrans(@RequestBody StuTransCriteria c) {
        return stuService.doTrans(c);
    }


//下面是演示用的根目录 ------------------------------------------------------

    //登录页面
    @GetMapping({"/"})
    @ResponseBody
    public String gotoLogin() {
        return "index";
    }

}
