package com.liumapp.booklet.concurrent.service;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.concurrent.locks.Lock;

/**
 * file NameService.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/7/11
 */
@Service
@Scope("prototype")
public class NameService {

    private String name;

    public NameService() {
    }

    public NameService(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public NameService setName(String name) {
        this.name = name;
        return this;
    }
}
