package com.liumapp.booklet.basic.lambda;

import lombok.extern.slf4j.Slf4j;

import java.util.function.Supplier;

/**
 * file Demo1.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/11/28
 */
@Slf4j
public class Demo1 {

    interface Adder {
        int add (int x, int y);
    }

    //https://www.oschina.net/question/2680454_2187139
    public static void main(String[] args) {
        Adder adder = (x, y) -> x + y;
        log.info(adder.add(1, 2) + "");

        //在java中匿名函数只能引用外部的final变量
        final int  factor = 10;
        int worker = 5;
//        worker = worker * 10;
        Adder adder2 = (x, y) -> x + y * factor + worker;
        log.info(adder2.add(1, 2) + "");

        // 通过jdk8的Supplier实现惰性求值
        // 一般而言成员变量在实例创建时就会被初始化
        // 而惰性求值可以将初始化的过程延迟到变量的第一次使用
        // 对于成员变量的值需要经过大量计算的类来说可以有效加快实例的创建过程
        Supplier lazyField = () -> {
            int sum = 0;
            for (int i = 1; i <= 100; i++) {
                sum += i;
            }
            return sum;
        };

    }


}
