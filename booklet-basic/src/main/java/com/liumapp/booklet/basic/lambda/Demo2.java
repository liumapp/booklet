package com.liumapp.booklet.basic.lambda;

import com.liumapp.booklet.basic.App;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 *
 * https://www.jianshu.com/p/c204e3721733
 *
 * file Demo2.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/11/28
 */
@Slf4j
public class Demo2 {

    public static void main(String[] args) {
        Comparator<Apple> byWeight = (Apple a1, Apple a2) -> {
            return a1.getWeight() > a2.getWeight() ? 1 : -1;
        };

        List<Apple> appleList = new LinkedList<>();
        appleList.add(new Apple(5));
        appleList.add(new Apple(3));
        appleList.add(new Apple(1));
        appleList.sort(byWeight);
        appleList.forEach(item -> {
            log.info(item.getWeight() + "");
        });



    }

    @Data
    @AllArgsConstructor
    public static class Apple {
        private int weight;
    }

}
