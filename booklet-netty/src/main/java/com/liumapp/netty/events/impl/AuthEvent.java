package com.liumapp.netty.events.impl;

import com.liumapp.netty.factories.TerminalChannelFactory;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 认证事件
 */
@Data
public class AuthEvent extends TerminalConnectEvent {
    private TerminalChannelFactory.Terminal terminal;
    private TerminalChannelFactory.Status status;

    public AuthEvent(TerminalChannelFactory.Terminal terminal, TerminalChannelFactory.Status status) {
        this.terminal = terminal;
        this.status = status;
        super.setAuthCode(terminal.getAuthCode());
    }

    public AuthEvent(String authCode, LocalDateTime localDateTime, TerminalChannelFactory.Terminal terminal, TerminalChannelFactory.Status status) {
        super(authCode, localDateTime);
        this.terminal = terminal;
        this.status = status;
    }
}