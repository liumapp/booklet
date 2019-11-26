package com.liumapp.booklet.basic.missions.demo1;

import java.util.concurrent.PriorityBlockingQueue;

/**
 *
 * 只保留最新任务的添加者
 *
 * file KeepNewestMissionAdder.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/11/21
 */
public class KeepNewestMissionAdder <T> implements MissionAdder<T> {

    private int maxBufferSizePreProcess;

    public KeepNewestMissionAdder(int maxBufferSizePreProcess) {
        this.maxBufferSizePreProcess = maxBufferSizePreProcess;
    }

    @Override
    public void add(PriorityBlockingQueue queue, T t) {
        if (queue.size() >= maxBufferSizePreProcess) {
            queue.remove();
        }
        queue.offer(t);
    }

}
