package com.liumapp.spring.concurrents.demo1;

import lombok.Data;

/**
 * file Apple.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/12/3
 */
@Data
public class Apple {

    private int number;

    public Apple() {
    }

    public Apple(int number) {
        this.number = number;
    }
}
