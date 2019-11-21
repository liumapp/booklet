package com.liumapp.booklet.basic.missions.demo1;

import java.util.concurrent.PriorityBlockingQueue;

/**
 *
 * 任务添加处理类
 *
 * file MissionAdder.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/11/21
 */
public interface MissionAdder <T> {

    public void add (PriorityBlockingQueue queue, T t);

}
