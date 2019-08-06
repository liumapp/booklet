package com.liumapp.booklet.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = BookletRedisMain.class)
@RunWith(SpringRunner.class)
public class BookletRedisMainTest {

    @Resource
    private ListOperations<String, Object> listOperations;

    @Test
    public void leftPushTest () {
        List<String> list = new ArrayList<>();
        list.add("hello world2");

        listOperations.leftPush("listKey", list.toString());
    }

}