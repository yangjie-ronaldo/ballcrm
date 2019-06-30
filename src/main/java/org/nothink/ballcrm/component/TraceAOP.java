package org.nothink.ballcrm.component;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

@Aspect
@Component
public class TraceAOP {
    static Logger logger = LoggerFactory.getLogger(TraceAOP.class);

    public static long startTime;
    public static long endTime;


    /**
     * 所有controller方法前后
     */
    @Pointcut("execution(*  org.nothink.ballcrm.controller.*.*(..))")
    public void executionService() {}

    @Before(value = "executionService()")
    public void doBefore(JoinPoint joinPoint){
        String requestId = String.valueOf(UUID.randomUUID());
        startTime = System.currentTimeMillis();
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String requestURI = request.getRequestURI();
        //String remoteAddr = request.getRemoteAddr();   //这个方法取客户端ip"不够好"
        //String requestMethod = request.getMethod();
        //String declaringTypeName = joinPoint.getSignature().getDeclaringTypeName();
        //String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        request.setAttribute("requestId",requestId);
        //logger.info("请求url=" + requestURI + ",客户端ip=" + remoteAddr + ",请求方式=" + requestMethod + ",请求的类名=" + declaringTypeName + ",方法名=" + methodName + ",入参=" + args);
        logger.info("------------------------------ begin ------------------------------------");
        logger.info("收到请求url = " + requestURI + ",全局跟踪号 = "+requestId);
        MDC.put("requestId",requestId);
        logger.info("请求参数为：{}",Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(pointcut = "executionService()",returning="returnValue")
    public void  doAfterReturning(JoinPoint joinPoint,Object returnValue){
        //返回加入全局跟踪号
        Map map=(Map) returnValue;
        map.put("requestId",MDC.get("requestId"));
        endTime = System.currentTimeMillis() - startTime;
        logger.info("本次接口耗时{}ms",endTime);
        logger.info("返回参数保密，全局跟踪号 = "+MDC.get("requestId"));
        MDC.clear();
        logger.info("------------------------------- end -------------------------------------");
    }
}
