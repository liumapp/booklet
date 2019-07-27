package com.liumapp.booklet.restful.portal.aop;

import com.liumapp.booklet.restful.core.annotations.UnTokenChk;
import com.liumapp.booklet.restful.core.db.entity.Users;
import com.liumapp.booklet.restful.core.exceptions.UnLegalTokenException;
import com.liumapp.booklet.restful.core.exceptions.UnLoginException;
import com.liumapp.booklet.restful.portal.util.TokenUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Locale;

import static com.liumapp.booklet.restful.core.util.UserUtil.*;

/**
 * file TokenAOP.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/7/27
 */
@Component
@Aspect
@Order(3)
public class TokenAOP {

    @Autowired
    private TokenUtil tokenUtils;

    @Pointcut("within(com.liumapp.booklet.restful.portal.controller..*)")
    public void checkToken () {}

    @Before("checkToken()")
    public void checkToken (JoinPoint joinPoint) {

        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        //获取当前访问的类方法
        Method targetMethod = signature.getMethod();
        //判断是否是注解修饰的类，如果是则不需要校验token
        if(!targetMethod.isAnnotationPresent(UnTokenChk.class)){

            //获取请求头中的token
            String token = tokenUtils.getToken();

            //判断请求头拿出的token是否为空
            if (token == null || token.equals("")) {
                throw new UnLoginException();
            }

            Users users = tokenUtils.getUsers();
            setUser(users);
            //统一设置为中文
            setLocale(Locale.CHINESE);
            //token 校验合法性
            if (!tokenUtils.checkToken(users)) {
                throw new UnLegalTokenException();
            }
        }

    }

}
