package com.liumapp.booklet.restful.core.annotations;



import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 通过注解处理日志
 *
 * file Log.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/7/24
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Log {

    /**
     * 操作类型
     */
    String action ();

    /**
     * 对象类型
     */
    String itemType () default "";

    /**
     * 对象id
     */
    String itemId () default "";

    /**
     * 参数
     */
    String param () default "";

}
