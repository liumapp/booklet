package com.liumapp.booklet.restful.core.util;

import com.alibaba.fastjson.JSON;
import com.liumapp.booklet.restful.core.db.entity.Users;
import com.liumapp.booklet.restful.core.exceptions.UnLoginException;
import org.slf4j.MDC;

import java.util.Locale;

/**
 * 用户工具类
 *
 * file UserUtil.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/7/24
 */
public class UserUtil {

    private final static ThreadLocal<String> tlUser = new ThreadLocal<String>();

    private final static ThreadLocal<Locale> tlLocale = new ThreadLocal<Locale>() {
        protected Locale initialValue() {
            // 语言的默认值
            return Locale.CHINESE;
        };
    };

    public static final String KEY_LANG = "lang";

    public static final String KEY_USER = "user";

    public static void setUser(Users user) {
        String userJson = JSON.toJSONString(user);
        tlUser.set(userJson);
        // 把用户信息放到log4j
        MDC.put(KEY_USER, userJson);
    }

    /**
     * 如果没有登录，返回null
     *
     * @return
     */
    public static Users getUserIfLogin() {
        String userJson = tlUser.get();
        if (userJson == null) {
            return null;
        } else {
            return JSON.parseObject(tlUser.get(), Users.class);
        }
    }

    /**
     * 如果没有登录会抛出异常
     *
     * @return
     */
    public static Users getUser() {
        String userJson = tlUser.get();
        Users user = JSON.parseObject(userJson, Users.class);

        if (user == null) {
            throw new UnLoginException();
        }

        return user;
    }

    public static void setLocale(String locale) {
        setLocale(new Locale(locale));
    }

    public static void setLocale(Locale locale) {
        tlLocale.set(locale);
    }

    public static Locale getLocale() {
        return tlLocale.get();
    }

    public static void clearAllUserInfo() {
        tlUser.remove();
        tlLocale.remove();

        MDC.remove(KEY_USER);
    }

}
