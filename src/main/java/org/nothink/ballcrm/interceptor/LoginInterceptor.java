package org.nothink.ballcrm.interceptor;

import org.nothink.ballcrm.entity.LoginTokenEntity;
import org.nothink.ballcrm.mapper.LoginTokenMapper;
import org.nothink.ballcrm.util.ComUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Date;

/**
 * 定义了一个登录的拦截器，在SpringMVC配置类里注册后拦截所有请求
 */
public class LoginInterceptor implements HandlerInterceptor {
    static Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);
    @Autowired
    LoginTokenMapper ltMapper;

    public void returnResp(HttpServletResponse response, JSONObject res) throws Exception {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = response.getWriter();
        out.append(res.toString());
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url=request.getRequestURL().toString();
        logger.info("get new request : ["+url+"]");
        logger.info("start login token check...");
        String token = request.getHeader("token");
        String eid = request.getHeader("eid");
        JSONObject res = new JSONObject();
//        System.out.println("拦截器拿到请求头的token：" + token);
//        System.out.println("拦截器拿到请求头的eid：" + eid);

        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(eid)) {
            res.put("code", 50008);
            res.put("msg", "非法token，无权限操作");
            returnResp(response, res);
            return false;
        } else {
            try {
                LoginTokenEntity bean = ltMapper.selectByPrimaryKey(Integer.parseInt(eid));
                if (bean == null) {
                    res.put("code", 50008);
                    res.put("msg", "非法token，无权限操作");
                    returnResp(response, res);
                    return false;
                } else if (!bean.getToken().equals(token)) {
                    res.put("code", 50012);
                    res.put("msg", "已在其它客户端登录");
                    returnResp(response, res);
                    return false;
                } else if (bean.getStatus() != 1) {
                    res.put("code", 50012);
                    res.put("msg", "已登出");
                    returnResp(response, res);
                    return false;
                } else if (bean.getExpired().before(new Date())) {
                    res.put("code", 50014);
                    res.put("msg", "已超时");
                    returnResp(response, res);
                    return false;
                } else {
                    //权限验证通过
                    return true;
                }
            } catch (Exception e) {
                res.put("code", 50008);
                res.put("msg", "非法token，无权限操作");
                returnResp(response, res);
                return false;
            }
        }
    }
}
