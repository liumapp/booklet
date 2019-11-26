package com.liumapp.booklet.basic.missions.demo1;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * file BlockingMissionAdder.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/11/25
 */
public class BlockingMissionAdder<T> implements MissionAdder<T> {

    private long timeout;

    private TimeUnit unit;

    public BlockingMissionAdder(long timeout, TimeUnit unit) {
        this.timeout = timeout;
        this.unit = unit;
    }

    @Override
    public void add(PriorityBlockingQueue queue, T t) {
        queue.offer(t, timeout, unit);
    }
}
