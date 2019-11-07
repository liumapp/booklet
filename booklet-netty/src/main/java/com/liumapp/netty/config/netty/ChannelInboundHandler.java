package com.liumapp.netty.config.netty;

import com.liumapp.netty.config.BasicPropConfig;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * file ChannelInboundHandler.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/11/7
 */
@ChannelHandler.Sharable
@Slf4j
public class ChannelInboundHandler extends SimpleChannelInboundHandler<ByteBuf> {

    @Autowired
    private BasicPropConfig basicPropConfig;

//    @Autowired
//    private BbParserFactory bbParserFactory;
//
    @Autowired
    private TerminalChannelFactory factory;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {

    }
}
