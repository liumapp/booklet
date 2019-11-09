package com.liumapp.netty.models.datagram;

import lombok.Data;

/**
 *
 * 所有报文的头，需要按照协议要求来进行封装
 *
 * file Header.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/11/8
 */
@Data
public class Header {

    public Header(short msgId, String terminalPhone, short flowId) {
        this.msgId = msgId;
        this.terminalPhone = terminalPhone;
        this.flowId = flowId;
    }

    /**
     * 消息ID 2字节
     */
    private short msgId;

    /**
     * 消息体属性 2字节
     */
    private short msgBodyProps;

    /**
     * 终端手机号 6字节
     */
    private String terminalPhone;

    /**
     * 流水号 2字节
     */
    private short flowId;

    /**
     * 获取包体长度
     *
     * @return
     */
    public short getMsgBodyLength() {
        return (short) (msgBodyProps & 0x3ff);
    }

    /**
     * 获取加密类型 3bits
     *
     * @return
     */
    public byte getEncryptionType() {
        return (byte) ((msgBodyProps & 0x1c00) >> 10);
    }

    /**
     * 是否分包
     *
     * @return
     */
    public boolean hasSubPackage() {
        return ((msgBodyProps & 0x2000) >> 13) == 1;
    }


}
