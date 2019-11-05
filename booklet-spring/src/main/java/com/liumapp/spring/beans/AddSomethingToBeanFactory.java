package com.liumapp.spring.beans;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * file AddSomethingToBeanFactory.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/11/5
 */
@Component
public class AddSomethingToBeanFactory implements ApplicationListener<ContextRefreshedEvent> {

    /**
     * 在Spring容器将所有的Bean都初始化完成之后，就会执行该方法
     * @param contextRefreshedEvent
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        

    }
}
