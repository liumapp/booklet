package com.liumapp.booklet.basic.missions.demo1;

/**
 * file MissionDispatcher.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/11/25
 */
public interface MissionDispatcher <T> {

    /**
     * 添加任务
     * @param t
     * @param key long 取模
     */
    public void addMission (T t, Long key);

}
