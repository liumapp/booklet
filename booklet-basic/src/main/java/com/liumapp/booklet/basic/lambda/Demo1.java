package com.liumapp.booklet.basic.lambda;

import lombok.extern.slf4j.Slf4j;

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

    public static void main(String[] args) {
        Adder adder = (x, y) -> x + y;
        log.info(adder.add(1, 2) + "");
    }


}
