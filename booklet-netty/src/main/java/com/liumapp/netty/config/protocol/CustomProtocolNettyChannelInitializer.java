package com.liumapp.netty.config.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * file CustomProtocolNettyChannelInitializer.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/11/6
 */
@Slf4j
public class CustomProtocolNettyChannelInitializer extends ChannelInitializer<SocketChannel> {

    private static final int MAX_FRAME_LENGTH = 65535;

    private static final ByteBuf TAIL = Unpooled.copiedBuffer(new byte[]{0x3D});

    private static final ByteBuf HEAD = Unpooled.copiedBuffer(new byte[]{0x3D});

    @Autowired
    private BubiaoInboundHandler bubiaoInboundHandler;

    @Autowired
    private BubiaoOutboundHandler bubiaoOutboundHandler;

    @Override
    protected void initChannel(SocketChannel ch) {
        ch.pipeline()
                .addLast(new DelimiterBasedFrameDecoder(MAX_FRAME_LENGTH, true, HEAD,TAIL))
                .addLast(new BbDecoder())
                .addLast(bubiaoInboundHandler)
                .addLast(bubiaoOutboundHandler)
                .addLast(new NettyExceptionHandler());
    }



}
