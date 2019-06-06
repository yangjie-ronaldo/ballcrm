package org.nothink.ballcrm.controller;

import org.nothink.ballcrm.entity.*;
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
    public ResponseMsg getStuById(@PathVariable(value = "sid") int sid){
        ResponseMsg out=new ResponseMsg("ok");
        StuEntity s=stuService.findById(sid);
        logger.info(s==null?"查无学员信息":"查询单个学员："+s.toString());
        out.setData(s);
        return out;
    }

    /**
     * 查询所有学员(有条件查询、分页)
     * @param c
     * @return out
     */
    @GetMapping("/stu")
    @ResponseBody
    public ResponseMsg getAll(StuEntity c){
        ResponseMsg out=new ResponseMsg("ok");
        logger.info("查询学员，页数："+c.getCurrentPage()+" 每页条数："+c.getPageSize());
        PagedResult<StuEntity> pageout=stuService.getAllByCriteria(c);
        out.setData(pageout);
        return out;
    }

    /**
     * 新增学员
     * @param c
     * @return
     */
    @PostMapping("/stu")
    @ResponseBody
    public ResponseMsg insertStu(StuEntity c){
        ResponseMsg out=new ResponseMsg("ok");
        logger.info(c.toString());
        int i=stuService.addOne(c);
        if (i<=0){
            out.setMsg("新增不成功");
            out.setCode(30001);
        }
        return out;
    }

    /**
     * 修改学员基本信息
     * @param c
     * @return
     */
    @PutMapping("/stu")
    @ResponseBody
    public ResponseMsg updateStu(StuEntity c){
        ResponseMsg out=new ResponseMsg("ok");
        logger.info(c.getSid().toString());
        int i=0;
        if (c.getSid()!=0){
            i=stuService.updateStuBySid(c);
        }
        if (i<=0){
            out.setMsg("修改失败");
            out.setCode(30001);
        }
        return out;
    }

    /**
     * 查询学员成长历史
     * @return
     */
    @GetMapping("/stustatus")
    @ResponseBody
    public ResponseMsg getStuStatusHis(StuEntity c){
        ResponseMsg out=new ResponseMsg("ok");
        logger.info("查询状态历史，页数："+c.getCurrentPage()+" 每页条数："+c.getPageSize());
        PagedResult<StuStatusEntity> pagelist=stuService.getStuStatusList(c);
        out.setData(pagelist);
        return out;
    }

    /**
     * 查询学员已买课程
     * @return
     */
    @GetMapping("/stucourse")
    @ResponseBody
    public ResponseMsg getStuCourse(StuEntity c){
        ResponseMsg out=new ResponseMsg("ok");
        List<StuCourseEntity> pagelist=stuService.getStuCourseList(c);
        out.setData(pagelist);
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
