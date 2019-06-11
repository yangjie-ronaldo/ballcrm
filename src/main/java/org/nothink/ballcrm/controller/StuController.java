package org.nothink.ballcrm.controller;

import org.nothink.ballcrm.entity.*;
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

@CrossOrigin
@Controller
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
        return ComUtils.getResp(20000,"查询成功",stuService.findById(sid));
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
     * 查询学员已买课程
     *
     * @return
     */
    @GetMapping("/stucourse")
    @ResponseBody
    public Map getStuCourse(StuEntity c) {
        return stuService.getStuCourseList(c);
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
     * @return
     */
    @GetMapping("/courseforbuy")
    @ResponseBody
    public Map courseBuyList(){
        return stuService.courseBuyList();
    }


//下面是演示用登录------------------------------------------------------

    //登录页面
    @GetMapping({"/", "/pages/login"})
    public String gotoLogin() {
        return "login";
    }

    //首页页面
    @GetMapping("/pages/main")
    public String gotoMain() {
        return "main";
    }

    //登录处理
    @PostMapping("/action/login")
    public String doLogin(@RequestParam("username") String username,
                          @RequestParam("password") String password,
                          Map<String, Object> map,
                          HttpSession session) {
        logger.info("登录名为：" + username + " 登录密码为：" + password);
        if (!StringUtils.isEmpty(password) && password.equals("123")) {
            //登录成功
            session.setAttribute("username", username);
            //重定向到主页
            return "redirect:/pages/main";
        } else {
            map.put("msg", "用户名或密码错误");
            // 带出错信息到跳转到登录页
            return "login";
        }
    }

    //退出登录处理
    @GetMapping("/action/logout")
    public String doLogout(HttpSession session) {
        session.removeAttribute("username");
        session.invalidate();
        return "redirect:/";
    }
}
