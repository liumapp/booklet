package com.liumapp.booklet.basic.missions.demo1;

import com.google.common.base.Function;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;

/**
 *
 * 下分任务类
 *
 * 按照任务id的顺序，进行批量任务的处理
 *
 * file MissionDispatcher.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/11/21
 */
@Slf4j
@Data
public final class AsyncMissionDispatcher<T> implements MissionDispatcher<T> {

    // ============ 基本属性 ============
    private int maxProcessCount;

    private int maxBufferSizePreProcess;

    private Function<T, Boolean> process;

    private Function<List<T>, Boolean> batchProcess;

    private MissionAdder<T> missionAdder;

    private Map<Long, PriorityBlockingQueue<T>> mapQueue = new HashMap<>();

    public static <T> AsyncDispatcherBuilder<T> newBuilder() {
        return new AsyncDispatcherBuilder<>();
    }

    @Override
    public void addMission(T t, Long key) {
        long abs = Math.abs(key % maxProcessCount);
        PriorityBlockingQueue queue = mapQueue.get(abs);
        missionAdder.add(queue, t);
    }

    @Override
    public void start(Comparator<T> comparable) {
        ExecutorService executorService = Executors.newFixedThreadPool(maxProcessCount);

        for (long i = 0; i < maxProcessCount; i++) {
            final PriorityBlockingQueue<T> queue = new PriorityBlockingQueue(maxBufferSizePreProcess, comparable);

            mapQueue.put(i, queue);
            executorService.submit(new Runnable() {
                private PriorityBlockingQueue<T> blockingQueue = queue;

                @Override
                public void run() {
                    while (true) {
                        try {
                            T t = blockingQueue.take();
                            process.apply(t);
                        } catch (Throwable e) {
                            log.error("", e);
                        }
                    }
                }
            });

        }
    }

    @Override
    public void startBatch(Comparator<T> comparable) {
        ExecutorService executorService = Executors.newFixedThreadPool(maxProcessCount);

        for (long i = 0; i < maxProcessCount; i++) {
            final PriorityBlockingQueue<T> queue = new PriorityBlockingQueue(maxBufferSizePreProcess, comparable);
            mapQueue.put(i, queue);
            executorService.submit(new Runnable() {
                private PriorityBlockingQueue<T> blockingQueue = queue;
                private LinkedList<T> batch = new LinkedList<>();

                @Override
                public void run() {
                    while (true) {
                        try {
                            int size = blockingQueue.size();
                            if (size > 0) {
                                for (int i = 0; i < size; i++) {
                                    batch.add(blockingQueue.take());
                                }
                                batchProcess.apply(batch);
                                batch.clear();
                            } else {
                                batch.add(blockingQueue.take());
                                batchProcess.apply(batch);
                                batch.clear();
                            }
                        } catch (Throwable e) {
                            log.error("", e);
                        }
                    }
                }
            });

        }
    }
}
