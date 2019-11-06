package com.liumapp.netty.config.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PreDestroy;

/**
 * file NettyTcpServer.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/11/6
 */
@Slf4j
public class NettyTcpServer implements Server {

    private int port;

    private ServerBootstrap bootstrap;

    private EventLoopGroup bossGroup;

    private EventLoopGroup workerGroup;

    private Channel channel;

    private ChannelInitializer childHandler;

    private NettyTcpServer() {
    }

    public NettyTcpServer(int port, ChannelInitializer childHandler) {
        this.childHandler = childHandler;
        this.port = port;
        try {
            doOpen();
            log.info("--------netty bind port:[{}] success-------", port);
        } catch (Throwable t) {
            try {
                close();
            } catch (Throwable t2) {
                log.error("[close] error", t2);
            }
            log.error("[doOpen] error", t);
        }
    }

    @Override
    public void doOpen() throws Throwable {
        if (bootstrap != null) {
            return;
        }
        bootstrap = new ServerBootstrap();
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childOption(ChannelOption.TCP_NODELAY, Boolean.TRUE)
                .childOption(ChannelOption.SO_REUSEADDR, Boolean.TRUE)
                .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(childHandler);
        ChannelFuture channelFuture = bootstrap.bind(port);
        channelFuture.syncUninterruptibly();
        channel = channelFuture.channel();
    }


    @Override
    @PreDestroy
    public void close() throws Throwable {
        try {
            if (channel != null) {
                channel.close();
            }
        } catch (Throwable e) {
            log.error(e.getMessage(), e);
        }
        try {
            if (workerGroup != null) {
                workerGroup.shutdownGracefully();
            }
            if (bossGroup != null) {
                bossGroup.shutdownGracefully();
            }
        } catch (Throwable e) {
            log.error(e.getMessage(), e);
        }
    }
}
