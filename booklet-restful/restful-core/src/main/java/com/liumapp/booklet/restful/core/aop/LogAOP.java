package com.liumapp.booklet.restful.core.aop;

import com.alibaba.fastjson.JSONObject;
import com.liumapp.booklet.restful.core.annotations.Log;
import com.liumapp.booklet.restful.core.util.SPELUtil;
import com.liumapp.booklet.restful.core.util.UserUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

/**
 *  日志信息处理AOP
 *  把需要的信息从参数中提取出来，转成json字符串放到MDC中使用。
 *
 * file LogAOP.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/7/24
 */
@Slf4j
@Aspect
@Component
public class LogAOP {

    public static final String JSON_KEY = "logjson";

    @Pointcut("@annotation(com.liumapp.booklet.restful.core.annotations.Log)")
    public void LogHandler () {}

    @SneakyThrows
    @Around("LogHandler()")
    public Object LogHandlerMethod (ProceedingJoinPoint proceedingJoinPoint) {
        long startTime = System.currentTimeMillis();
        Object result;

        try {
            putLogInfo2MDC(proceedingJoinPoint);
            result = proceedingJoinPoint.proceed();
            // 本次操作用时（毫秒）
            long elapsedTime = System.currentTimeMillis() - startTime;
            log.info("[{}]use time: {}", proceedingJoinPoint.getSignature(), elapsedTime);
        } finally {
            clearMDC();
        }
        return result;
    }

    private void clearMDC() {
        MDC.remove(JSON_KEY);
    }

    private void putLogInfo2MDC(ProceedingJoinPoint pjp) {
        // 得到方法上的注解
        MethodSignature signature = (MethodSignature) pjp.getSignature();

        Log logAnnotation = signature.getMethod().getAnnotation(Log.class);


        SPELUtil spel = new SPELUtil(pjp);

        JSONObject json = new JSONObject();

        // 用户
        json.put("User", UserUtil.getUserIfLogin());

        // 操作
        json.put("Action", logAnnotation.action());

        // 对象类型
        json.put("Type", logAnnotation.itemType());

        // 对象id，spel表达式
        json.put("Id", spel.cacl(logAnnotation.itemId()));

        // 其他参数，spel表达式
        json.put("Params", spel.cacl(logAnnotation.param()));

        MDC.put(JSON_KEY, json.toJSONString());
    }

}
