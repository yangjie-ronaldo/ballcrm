package org.nothink.ballcrm.interceptor;

import org.nothink.ballcrm.entity.LoginTokenEntity;
import org.nothink.ballcrm.mapper.LoginTokenMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Date;

/**
 * 定义了一个登录的拦截器，在SpringMVC配置类里注册后拦截所有请求
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    LoginTokenMapper ltMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token=request.getHeader("token");
        JSONObject res=new JSONObject();
        System.out.println("拦截器拿到请求头的token："+token);

        if(token != null ){
            //开始验证token
            LoginTokenEntity bean=ltMapper.selectByToken(token);
            if (bean==null){
                // 无此token 非法token
                res.put("msg","token非法或用户已在别处登录，请重新登录");
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json; charset=utf-8");
                PrintWriter out = response.getWriter();
                out.append(res.toString());
                return false;
            }

            if (bean.getStatus()!=1 || bean.getExpired().before(new Date())){
                //用户已退出，或已过期
                res.put("msg","用户超时或已退出，请重新登录");
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json; charset=utf-8");
                PrintWriter out = response.getWriter();
                out.append(res.toString());
                return false;
            }
            //验证通过
            System.out.println("验证通过");
            return true;
        } else {
            //无token
            //用户已退出，或已过期
            res.put("msg","请求非法，请先登录");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            PrintWriter out = response.getWriter();
            out.append(res.toString());
            return false;
        }
    }
}
