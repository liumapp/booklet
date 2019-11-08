package com.liumapp.netty.models.core;

/**
 *
 * 上行消息处理接口
 *
 * file ParseMsg.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/11/8
 */
public interface ParseMsg {

    /**
     * 上行消息整体报文重写
     */
    public void parse ();

    /**
     * 上行消息头请求报文重写
     */
    public void parseHead ();

    /**
     * 上行消息体请求报文重写
     */
    public void parseBody ();

    /**
     * 上行消息需要从ByteBuf中切割校验码
     */
    public void cuttingVerifyCode();


}
