package com.liumapp.booklet.restful.core.util;

import com.liumapp.booklet.restful.core.exceptions.CheckException;
import org.springframework.context.MessageSource;

/**
 *
 * 静态方法校验工具类
 *
 * file CheckUtil.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/7/24
 */
public class CheckUtil {

    private static MessageSource resources;

    public static void setResources(MessageSource resources) {
        CheckUtil.resources = resources;
    }

    public static void check(boolean condition, String msgKey, Object... args) {
        if (!condition) {
            fail(msgKey, args);
        }
    }

    public static void notEmpty(String str, String msgKey, Object... args) {
        if (str == null || str.isEmpty()) {
            fail(msgKey, args);
        }
    }

    //非负数
    public static void notNegativeInteger (Integer val, String msgKey, Object... args) {
        notNull(val, msgKey, args);
        if (val < 0) {
            fail(msgKey, args);
        }
    }

    public static void notNull(Object obj, String msgKey, Object... args) {
        if (obj == null) {
            fail(msgKey, args);
        }
    }

    private static void fail(String msgKey, Object... args) {
        throw new CheckException(resources.getMessage(msgKey, args, UserUtil.getLocale()));
    }

}
