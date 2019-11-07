package com.liumapp.netty.factories;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.liumapp.netty.services.TerminalAuthService;
import io.netty.buffer.ByteBuf;
import io.netty.channel.socket.SocketChannel;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * file TerminalChannelFactory.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/11/7
 */
@Slf4j
public class TerminalChannelFactory {

    private Cache<String, TerminalChannel> CHANNEL_CACHE = null;

    @Autowired
    private TerminalAuthService terminalAuthService;

    @Autowired
    private BubiaoEventAdpater bubiaoEventAdpater;

    @PostConstruct
    public void initCache() {
        CHANNEL_CACHE = CacheBuilder.newBuilder()
                .maximumSize(10000)
                .removalListener((RemovalListener<String, TerminalChannel>) removalNotification ->
                        log.info("[factory] expire connect .. {}", removalNotification.getKey()))
                .expireAfterAccess(3, TimeUnit.MINUTES).build();
    }

    /**
     * @param terminal
     * @param socketChannel
     * @return
     */
    public TerminalChannel newChannel(Terminal terminal, SocketChannel socketChannel) {
        Preconditions.checkNotNull(terminal, "terminal must not null");
        Preconditions.checkNotNull(socketChannel, "socketChannel must not null");
        String authCode = terminalAuthService.getAuthCode(terminal.getTerminalNo());
        terminal.setAuthCode(authCode);

        TerminalChannel store = CHANNEL_CACHE.getIfPresent(authCode);
        if (store != null) {
            if (socketChannel == store.socketChannel) {
                return store;
            } else {
                store.close();
            }
        }
        TerminalChannel terminalChannel = new TerminalChannel(terminal, socketChannel, this);
        CHANNEL_CACHE.put(authCode, terminalChannel);
        bubiaoEventAdpater.post(new BubiaoEventAdpater.RegisterEvent(terminal, terminalChannel.status));
        return terminalChannel;
    }

    /**
     * @param channel
     */
    public void remove(TerminalChannel channel) {
        Preconditions.checkNotNull(channel, "channel is null");
        CHANNEL_CACHE.invalidate(channel.getTerminal().getAuthCode());
        bubiaoEventAdpater.post(new BubiaoEventAdpater.UnregisterEvent(channel.terminal.getAuthCode(), LocalDateTime.now()));
        log.info("[factory] disconnect..{}", JSON.toJSONString(channel.terminal));
    }


    public TerminalChannel getChannel(String authCode) {
        if (authCode == null) {
            return null;
        }
        return CHANNEL_CACHE.getIfPresent(authCode);
    }


    public enum Status {
        REGISTER,       //注册
        AUTH,           //认证
        ACTIVE,         //激活
        UNREGISTER,     //注销
    }


    @Slf4j
    @Data
    public static class TerminalChannel {

        private static final List ignoreWriteStatus = Arrays.asList(
                Status.ACTIVE, Status.REGISTER, Status.AUTH
        );

        private TerminalChannelFactory factory;

        private Terminal terminal;

        private SocketChannel socketChannel;

        private Status status;

        private TerminalChannel(Terminal terminal, SocketChannel socketChannel, TerminalChannelFactory factory) {
            this.terminal = terminal;
            this.socketChannel = socketChannel;
            this.factory = factory;
            status = Status.REGISTER;
        }

        /**
         * @param byteBuf
         */
        public void write(ByteBuf byteBuf) {
            Preconditions.checkNotNull(socketChannel, "socketChannel must not null");
            if (!ignoreWriteStatus.contains(this.getStatus())) {
                this.close();
                throw new IllegalArgumentException("channel status error");
            }

            if (!socketChannel.isActive()) {
                log.error("[TerminalChannel] active, terminal is {}", terminal.getTerminalNo());
                this.close();
                throw new IllegalArgumentException("channel is not active");
            }
            socketChannel.writeAndFlush(byteBuf);
        }

        public void close() {
            factory.remove(this);
            this.socketChannel.close();
        }

    }


    @Data
    public static class Terminal {

        private String terminalNo;

        private String remoteAddress;

        private String phone;

        //创建channel的时候分配
        private String authCode;

        private final AtomicInteger flowId = new AtomicInteger(0);

        public Terminal() {

        }

        public Terminal(String remoteAddress, String terminalNo, String terminalType, String phone) {
            this.terminalNo = terminalNo;
            this.remoteAddress = remoteAddress;
            this.phone = phone;
        }



    }

}
