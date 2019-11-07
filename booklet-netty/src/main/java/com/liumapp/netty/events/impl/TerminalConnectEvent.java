package com.liumapp.netty.events.impl;

import com.liumapp.netty.events.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * file TerminalConnectEvent.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/11/7
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TerminalConnectEvent implements BaseEvent {

    private String authCode;

    private LocalDateTime localDateTime;

}
