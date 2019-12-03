package com.liumapp.spring.concurrents.demo1;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * file BeanConfiguration.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/12/3
 */
@Configuration
public class BeanConfiguration {

    @Bean
    public TaskExecutor getTaskExecutor () {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setMaxPoolSize(10);
        taskExecutor.setCorePoolSize(5);
        taskExecutor.setQueueCapacity(100);
        return taskExecutor;
    }

    @Bean
    public Apple getApple () {
        return new Apple(1);
    }


}
