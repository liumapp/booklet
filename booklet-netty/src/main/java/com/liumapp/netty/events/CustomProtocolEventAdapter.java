package com.liumapp.netty.events;

import com.google.common.eventbus.EventBus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * file CustomProtocolEventAdapter.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/11/7
 */
@Component
public class CustomProtocolEventAdapter extends EventBus implements InitializingBean {

    @Autowired
    private ApplicationContext context;

    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, AbstractEventObserver> parsers = context.getBeansOfType(AbstractEventObserver.class);
        if (parsers.isEmpty())
            return;
        for (AbstractEventObserver abstractEventObsever : parsers.values())
            this.register(abstractEventObsever);
    }



    /**
     * 终端注册类
     */
    @Data
    public static class RegisterEvent extends TeriminalConnectEvent {
        private TerminalChannelFactory.Terminal terminal;
        private TerminalChannelFactory.Status status;

        public RegisterEvent(TerminalChannelFactory.Terminal terminal, TerminalChannelFactory.Status status) {
            this.terminal = terminal;
            this.status = status;
            super.authCode = terminal.getAuthCode();
        }

        public RegisterEvent(String authCode, LocalDateTime localDateTime, TerminalChannelFactory.Terminal terminal, TerminalChannelFactory.Status status) {
            super(authCode, localDateTime);
            this.terminal = terminal;
            this.status = status;
        }
    }

    /**
     * 终端消息基类
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TeriminalMsgEvent extends BaseEvent {
        private String terminalNo;
        private String msgType;
    }

    /**
     * 终端消息基类
     */
    @Data
    public static class  TeriminalMsgDispatchEvent <T extends ServerJTAbstractDataGram> extends TeriminalMsgEvent {

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


}
