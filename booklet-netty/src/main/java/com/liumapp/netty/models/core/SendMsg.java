package com.liumapp.netty.models.core;

import io.netty.buffer.ByteBuf;

/**
 * file SendMsg.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/11/8
 */
public interface SendMsg {

    /**
     * 下行消息需要生成校验码
     * @return byte
     */
    public byte generateVerifyCode ();

    /**
     * 转义消息
     * @return ByteBuf
     */
    public ByteBuf toByteBufMsg ();


}
