package com.liumapp.netty.events.impl;

import lombok.Data;

import java.time.LocalDateTime;

/**
 *
 * 终端心跳类
 *
 *
 * file HeartBeatEvent.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/11/8
 */
@Data
public class HeartBeatEvent extends TerminalConnectEvent {

    public HeartBeatEvent() {
    }

    public HeartBeatEvent(String authCode, LocalDateTime localDateTime) {
        super(authCode, localDateTime);
    }
}
