package com.liumapp.spring.concurrents.demo1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

/**
 * file DemoBootstrap.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/12/3
 */
@Component
@Slf4j
public class DemoBootstrap implements InitializingBean {

    @Autowired
    private TaskExecutor taskExecutor;

    @Autowired
    private Apple apple;

    @Override
    public void afterPropertiesSet() throws Exception {
        taskExecutor.execute(() -> {
            for (int i = 2; i < 300000000; i++) {
                log.info("a new thread eat apple : {}", apple.getNumber());
                apple.setNumber(i);
            }
        });
        log.info("main thread keep going to say something");
        for (int j = 1; j < 10000000; j++) {
            log.info("this is main thread , said : {}", j);
        }
    }

}
