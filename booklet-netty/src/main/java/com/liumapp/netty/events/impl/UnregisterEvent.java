package com.liumapp.netty.events.impl;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 终端注销类
 */
@Data
public class UnregisterEvent extends TerminalConnectEvent {
    public UnregisterEvent() {
    }

    public UnregisterEvent(String authCode, LocalDateTime localDateTime) {
        super(authCode, localDateTime);
    }
}