package com.liumapp.booklet.basic.missions.demo1;

import com.google.common.base.Function;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.concurrent.TimeUnit;

/**
 * file Demo1Executor.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/11/26
 */
@Slf4j
public class Demo1Executor {

    public static void main(String[] args) throws InterruptedException {
        //构建异步下发线程池
        MissionDispatcher<SimpleMissionData> missionDispatcher = AsyncMissionDispatcher.newBuilder()
                .setAddBlockTimeout(24, TimeUnit.HOURS)
                .setProcess(missionHandFunction)
                .setComparable(missionComparable)
                .build();

        int i = 0;
        while (true) {
            i++;
            missionDispatcher.addMission(new SimpleMissionData(i, "hello " + i), (long) i);
            Thread.sleep(500);
        }

    }

    /**
     * id小的先执行
     */
    public static Comparator<SimpleMissionData> missionComparable = (firstMission, secondMission) -> firstMission.getMsgId() < secondMission.getMsgId() ? 1 :-1;

    public static Function<SimpleMissionData, Boolean> missionHandFunction = dispatchTask -> {
        log.info("handle mission : mission id is {}, info is {}", dispatchTask.getMsgId(), dispatchTask.getMsgInfo());
        return true;
    };

    @Data
    public static class SimpleMissionData {

        private int msgId;

        private String msgInfo;

        public SimpleMissionData(int msgId, String msgInfo) {
            this.msgId = msgId;
            this.msgInfo = msgInfo;
        }
    }

}
