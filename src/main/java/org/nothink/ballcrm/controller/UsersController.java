package org.nothink.ballcrm.controller;

import org.nothink.ballcrm.entity.PageBean;
import org.nothink.ballcrm.entity.ResponseMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class UsersController {
    // 日志对象
    Logger logger = LoggerFactory.getLogger(getClass());



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
