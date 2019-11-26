package com.liumapp.booklet.basic.missions.demo1;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * file AsyncDispatcherBuilder.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/11/25
 */
public class AsyncDispatcherBuilder<T, P> implements DispatcherBuilder {

    private int maxProcessCount = 30;

    private int maxBufferSizePreProcess = 1000;

    private Function<T, Boolean> process;

    private Function<List<T>, Boolean> batchProcess;

    private MissionAdder<T> missionAdder;

    private Long timeout;

    private TimeUnit unit;

    private Comparator<T> comparable;

    public AsyncDispatcherBuilder() {
    }

    public AsyncDispatcherBuilder<T, P> setMaxProcessCount(int maxProcessCount) {
        this.maxProcessCount = maxProcessCount;
        return this;
    }

    public AsyncDispatcherBuilder setMaxBufferSizePreProcess(int maxBufferSizePreProcess) {
        this.maxBufferSizePreProcess = maxBufferSizePreProcess;
        return this;
    }

    public AsyncDispatcherBuilder setProcess(Function<T, Boolean> process) {
        this.process = process;
        return this;
    }

    public AsyncDispatcherBuilder setBatchProcess(Function<List<T>, Boolean> batchProcess) {
        this.batchProcess = batchProcess;
        return this;
    }

    public AsyncDispatcherBuilder setAdder(MissionAdder<T> missionAdder) {
        this.missionAdder = missionAdder;
        return this;
    }

    public AsyncDispatcherBuilder setAddBlockTimeout(long timeout, TimeUnit unit) {
        this.timeout = timeout;
        this.unit = unit;
        return this;
    }

    public AsyncDispatcherBuilder setComparable(Comparator<T> comparable) {
        this.comparable = comparable;
        return this;
    }

    @Override
    public MissionDispatcher<T> build() {
        Preconditions.checkState(this.process != null || this.batchProcess != null, "process can not be null");

        AsyncMissionDispatcher<T> asyncChannelProcess = new AsyncMissionDispatcher<T>();

        if (this.missionAdder == null) {
            if (timeout != null && unit != null) {
                this.missionAdder = new BlockingMissionAdder (timeout, unit);
            } else {
                this.missionAdder = new KeepNewestMissionAdder ();
            }
        }

        asyncChannelProcess.setProcess(this.process);
        asyncChannelProcess.setBatchProcess(this.batchProcess);
        asyncChannelProcess.setMissionAdder(this.missionAdder);
        asyncChannelProcess.setMaxBufferSizePreProcess(this.maxBufferSizePreProcess);
        asyncChannelProcess.setBatchProcess(this.batchProcess);
        asyncChannelProcess.setMissionAdder(this.missionAdder);
        asyncChannelProcess.setMaxBufferSizePreProcess(this.maxBufferSizePreProcess);
        asyncChannelProcess.setMaxProcessCount(this.maxProcessCount);

        if (this.process != null) {
            asyncChannelProcess.start(this.comparable);
        } else if (this.batchProcess != null) {
            asyncChannelProcess.startBatch(this.comparable);
        }
        return asyncChannelProcess;
    }

}
