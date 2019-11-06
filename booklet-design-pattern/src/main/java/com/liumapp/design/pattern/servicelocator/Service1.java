package com.liumapp.design.pattern.servicelocator;

import lombok.extern.slf4j.Slf4j;

/**
 * file Service1.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/11/6
 */
@Slf4j
public class Service1 implements Service {

    @Override
    public String getName() {
        return "Service1";
    }

    @Override
    public void execute() {
        log.info("Executing service1");
    }
}
