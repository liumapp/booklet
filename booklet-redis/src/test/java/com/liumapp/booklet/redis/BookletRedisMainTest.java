package com.liumapp.booklet.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootTest(classes = BookletRedisMain.class)
@RunWith(SpringRunner.class)
public class BookletRedisMainTest {

    @Resource
    private ListOperations<String, Object> listOperations;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void leftPushTest () {
        List<String> list = new ArrayList<>();

        list.add("hello world");
        listOperations.leftPush("listKey", list);

//        list.add("hello world2");
//        listOperations.leftPush("listKey", list.toString());
    }

    @Test
    public void stringListTest () {
        String mapKey1 = "OFF_LINE_3303", mapKey2 = "OFF_LINE_3304";

        String field1 = "01", field2 = "02", field3 = "03", field4 = "04";

        String jsonContent1 = "01-content", jsonContent2 = "02-content", jsonContent3 = "03-content", jsonContent4 = "04-content";

        stringRedisTemplate.opsForHash().put(mapKey1, field1, jsonContent1);
        stringRedisTemplate.opsForHash().put(mapKey1, field2, jsonContent2);
        stringRedisTemplate.opsForHash().put(mapKey2, field3, jsonContent3);
        stringRedisTemplate.opsForHash().put(mapKey2, field4, jsonContent4);

        Map<Object, Object> maps = stringRedisTemplate.opsForHash().entries(mapKey1);
        maps.forEach((Object key, Object value) -> {
            System.out.println("key is " + key);
            System.out.println("value is " + value);

        });
        System.out.println(maps);
    }





}