package com.liumapp.booklet.restful.core.aop;

import com.liumapp.booklet.restful.core.beans.ResultBean;
import com.liumapp.booklet.restful.core.exceptions.CheckException;
import com.liumapp.booklet.restful.core.exceptions.NoPermissionException;
import com.liumapp.booklet.restful.core.exceptions.UnLegalTokenException;
import com.liumapp.booklet.restful.core.exceptions.UnLoginException;
import com.liumapp.booklet.restful.core.util.UserUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 *
 * 处理和包装异常
 *
 * 注意：所有异常一律抛出处理，不允许catch
 *
 * file ExceptionsAOP.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/7/24
 */
@Component
@Aspect
@Order(2)
public class ExceptionsAOP {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionsAOP.class);

    @Autowired
    private MessageSource messageSource;

    /**
     * 要求所有Controller统一返回ResultBean格式的对象
     * 同时ResultBean不允许在Controller以外的地方传递
     */
    @Pointcut("execution(public com.liumapp.booklet.restful.core.beans.ResultBean *(..))")
    public void excudeService() {
    }

    @Around("excudeService()")
    public Object handlerControllerMethod(ProceedingJoinPoint pjp) {
        long startTime = System.currentTimeMillis();

        ResultBean<?> result;

        try {
            result = (ResultBean<?>) pjp.proceed();

            // 如果需要打印入参，可以从这里取出打印
            // Object[] args = pjp.getArgs();

            // 本次操作用时（毫秒）
            long elapsedTime = System.currentTimeMillis() - startTime;
            logger.info("[{}]use time: {}", pjp.getSignature(), elapsedTime);
        } catch (Throwable e) {
            /**
             * 所有异常统一处理
             * 不允许在此方法以外的地方catch异常
             */
            result = handlerException(pjp, e);
        }

        return result;
    }

    /**
     * 异常统一处理
     * 对已知的异常进行参数包装
     * 对未知的异常直接抛出，并做邮件等提醒功能（此类异常的出现，开发人员必须第一时间处理 ）
     */
    private ResultBean<?> handlerException(ProceedingJoinPoint pjp, Throwable e) {
        ResultBean<?> result = new ResultBean();

        /**
         * 已知异常【注意：已知异常不要打印堆栈，否则会干扰日志】
         * 校验出错，参数非法
         * 如果要返回详细的错误码，则定义新的RunTimeException进行捕获
         */
        if (e instanceof CheckException || e instanceof IllegalArgumentException) {
            result.setMsg(e.getLocalizedMessage());
            result.setCode(ResultBean.CHECK_FAIL);
        }
        // 没有登陆
        else if (e instanceof UnLoginException) {
            result.setMsg(messageSource.getMessage("account.need.login", null, UserUtil.getLocale()));
            result.setCode(ResultBean.NO_LOGIN);
        }
        // 没有权限
        else if (e instanceof NoPermissionException) {
            result.setMsg("NO PERMISSION");
            result.setCode(ResultBean.NO_PERMISSION);
        }
        //无效的token
        else if (e instanceof UnLegalTokenException) {
            result.setMsg("token expired");
            result.setCode(ResultBean.TOKEN_EXPIRED);
        } else {
            logger.error(pjp.getSignature() + " error ", e);
            // TODO 未知的异常，应该格外注意，可以发送邮件通知等
            result.setMsg(e.toString());
            result.setCode(ResultBean.UNKNOWN_EXCEPTION);
        }

        return result;
    }

}
