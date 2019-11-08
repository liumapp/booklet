package com.liumapp.netty.events.impl;

import lombok.Data;

/**
 *
 * 终端消息基类
 *
 * file TeriminalMsgDispatchEvent.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/11/8
 */
@Data
public class TeriminalMsgDispatchEvent <T extends ServerJTAbstractDataGram> extends TeriminalMsgEvent {

    private T t;

    private Boolean send;

    public TeriminalMsgDispatchEvent(String terminalNo, String msgType, T t, Boolean send) {
        super(terminalNo, msgType);
        this.t = t;
        this.send = send;
    }

    public TeriminalMsgDispatchEvent(T t, Boolean send) {
        this.t = t;
        this.send = send;
    }
}
