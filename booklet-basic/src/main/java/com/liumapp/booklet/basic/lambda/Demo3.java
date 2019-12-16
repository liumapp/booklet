package com.liumapp.booklet.basic.lambda;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * 将给定的一组用户中，名称为lisi的用户打包
 *
 * file Demo3.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/12/16
 */
public class Demo3 {

    public static void main(String[] args) {
        List<Person> personList = new ArrayList<>();
        Consumer<Person> personConsumer = x -> {
            if (x.name.equals("lisi")) {
                personList.add(x);
            }
        };
        Stream.of(new Person(21, "zhangsan"),
                new Person(22, "lisi"),
                new Person(23, "lisi"),
                new Person(24, "wangwu")).forEach(personConsumer);

        System.out.println(JSON.toJSON(personList));
    }

    @Data
    @AllArgsConstructor
    public static class Person {

        Integer id;

        String name;

    }

}
