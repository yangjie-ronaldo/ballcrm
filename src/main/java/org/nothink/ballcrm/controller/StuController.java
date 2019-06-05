package org.nothink.ballcrm.controller;

import org.nothink.ballcrm.entity.PagedResult;
import org.nothink.ballcrm.entity.StuCriteria;
import org.nothink.ballcrm.entity.StuEntity;
import org.nothink.ballcrm.entity.StuStatusEntity;
import org.nothink.ballcrm.service.EmpInfoService;
import org.nothink.ballcrm.service.StuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
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
     * @param sid
     * @return s
     */
    @GetMapping("/stu/{sid}")
    @ResponseBody
    public StuEntity getStuById(@PathVariable(value = "sid") int sid){
        StuEntity s=stuService.findById(sid);
        logger.info(s==null?"查无学员信息":"查询单个学员："+s.toString());
        return s;
    }

    /**
     * 查询所有学员(有条件查询、分页)
     * @param c
     * @return out
     */
    @GetMapping("/stu")
    @ResponseBody
    public PagedResult<StuEntity> getAll(StuCriteria c){
        logger.info("查询学员，页数："+c.getCurrentPage()+" 每页条数："+c.getPageSize());
        PagedResult<StuEntity> out=stuService.getAllByCriteria(c);
        return out;
    }

    /**
     * 新增学员
     * @param c
     * @return
     */
    @PostMapping("/stu")
    @ResponseBody
    public int insertStu(StuCriteria c){
        logger.info(c.toString());
        return stuService.addOne(c);
    }

    /**
     * 查询学员成长历史
     * @return
     */
    @GetMapping("/stustatus")
    @ResponseBody
    public PagedResult<StuStatusEntity> getStuStatusHis(StuCriteria c){
        logger.info("查询状态历史，页数："+c.getCurrentPage()+" 每页条数："+c.getPageSize());
        PagedResult<StuStatusEntity> out=stuService.getStuStatusList(c);
        return out;
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
