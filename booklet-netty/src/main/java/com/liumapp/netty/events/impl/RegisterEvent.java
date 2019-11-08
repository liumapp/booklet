package com.liumapp.netty.events.impl;

import com.liumapp.netty.factories.TerminalChannelFactory;
import lombok.Data;
import java.time.LocalDateTime;


/**
 *
 * 终端注册类
 *
 *
 * file RegisterEvent.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/11/8
 */
@Data
public class RegisterEvent extends TerminalConnectEvent {
    private TerminalChannelFactory.Terminal terminal;
    private TerminalChannelFactory.Status status;

    public RegisterEvent(TerminalChannelFactory.Terminal terminal, TerminalChannelFactory.Status status) {
        this.terminal = terminal;
        this.status = status;
        super.setAuthCode(terminal.getAuthCode());
    }

    public RegisterEvent(String authCode, LocalDateTime localDateTime, TerminalChannelFactory.Terminal terminal, TerminalChannelFactory.Status status) {
        super(authCode, localDateTime);
        this.terminal = terminal;
        this.status = status;
    }
}