package org.nothink.ballcrm.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 定义了一个登录的拦截器，在SpringMVC配置类里注册后拦截所有请求
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object username=request.getSession().getAttribute("username");
        if (username!=null){
            // 已经登录，直接放行
            return true;
        } else {
            // 未登录，转发到登录页面
            request.setAttribute("msg", "无权限访问，请登录后访问");
            request.getRequestDispatcher("/").forward(request, response);
            return false;
        }
    }
}
