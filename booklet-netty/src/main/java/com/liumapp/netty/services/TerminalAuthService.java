package com.liumapp.netty.services;

import com.liumapp.netty.config.BasicPropConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

/**
 * file TerminalAuthService.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/11/7
 */
@Service
public class TerminalAuthService {

    @Autowired
    private BasicPropConfig basicPropConfig;

    public String getAuthCode(String terminalNo) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(basicPropConfig.getVipNo())
                .append(basicPropConfig.getProtocol())
                .append(basicPropConfig.getCityNo())
                .append(terminalNo);
        // hash运算
        return String.valueOf(
                Math.abs(
                        DigestUtils.md5DigestAsHex(
                                stringBuffer.toString().getBytes()
                        ).hashCode()
                )
        );
    }


}
