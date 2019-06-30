package org.nothink.ballcrm.component;

import ch.qos.logback.core.net.SyslogOutputStream;
import org.nothink.ballcrm.entity.CommonException;
import org.nothink.ballcrm.util.ComUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 全局异常处理类
 */
@ControllerAdvice
public class MyExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(MyExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Map handleException(HttpServletRequest request, Exception e) {
        if (e instanceof CommonException) {
            CommonException commonE = (CommonException) e;
            logger.warn("请求路径 " + request.getRequestURL() + " 抛出自定义异常：" + commonE.getCode() + " " + commonE.getMsg());
            commonE.printStackTrace();
            Map out=ComUtils.getResp(commonE.getCode(), commonE.getMsg(), null);
            out.put("requestId",request.getAttribute("requestId"));
            return out;
        } else {
            logger.error("请求路径 " + request.getRequestURL() + " 抛出系统级异常：" + e.getMessage());
            e.printStackTrace();
            Map out=ComUtils.getResp(50000, e.getMessage(), null);
            out.put("requestId",request.getAttribute("requestId"));
            return out;
        }
    }
}
