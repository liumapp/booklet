package com.liumapp.booklet.basic.missions.demo1;

import com.google.common.base.Function;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public final class AsyncMissionDispatcher<T> {

    // ============ 基本属性 ============
    private int maxProcessCount;

    private int maxBufferSizePreProcess;

    private Function<T, Boolean> process;

    private Function<List<T>, Boolean> batchProcess;

    private MissionAdder<T> missionAdder;

    private Map<Long, PriorityBlockingQueue<T>> mapQueue = new HashMap<>();







}
