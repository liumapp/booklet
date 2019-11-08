package com.liumapp.netty.events.impl;

import com.liumapp.netty.events.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * 终端消息基类
 *
 *
 * file TeriminalMsgEvent.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/11/8
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeriminalMsgEvent implements BaseEvent {

    private String terminalNo;

    private String msgType;

}