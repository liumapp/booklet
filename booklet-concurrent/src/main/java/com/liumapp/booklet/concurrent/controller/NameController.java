package com.liumapp.booklet.concurrent.controller;

import com.liumapp.booklet.concurrent.service.NameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * file NameController.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/7/11
 */
@RestController
@RequestMapping("name")
@Scope("request")
public class NameController {

    @Autowired
    private NameService nameService;

    @RequestMapping("")
    public String changeAndReadName (@RequestParam String name) throws InterruptedException {
        System.out.println("get new request: " + name);
        String result = "";
//        synchronized (this) {
        nameService.setName(name);
        Thread.sleep(300);
        result = nameService.getName();
//        };
        return result;
    }

}
