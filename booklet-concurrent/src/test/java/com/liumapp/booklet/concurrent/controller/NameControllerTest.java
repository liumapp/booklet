package com.liumapp.booklet.concurrent.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.liumapp.qtools.http.HttpTool;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * file NameControllerTest.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/7/11
 */
public class NameControllerTest {

    @Test
    public void changeAndReadName() throws Exception {
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(200, 300 , 2000, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(200));
        for (int i = 0; i < 200; i++) {
            poolExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println(Thread.currentThread().getName() + " begin");
                        Map<String, String> headers = new HashMap<String, String>();
                        Map<String, String> querys = new HashMap<String, String>();

                        querys.put("name", Thread.currentThread().getName());
                        headers.put("Content-Type", "text/plain;charset=UTF-8");
                        HttpResponse response = HttpTool.doGet("http://localhost:8080",
                                "/name",
                                "GET",
                                headers,
                                querys);
                        String res = EntityUtils.toString(response.getEntity());

                        if (!Thread.currentThread().getName().equals(res)) {
                            System.out.println("WE FIND BUG !!!");
                            Assert.assertEquals(true, false);
                        } else {
                            System.out.println(Thread.currentThread().getName() + " get received " + res);
                        }
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        while(true) {
            Thread.sleep(100);
        }
    }

}