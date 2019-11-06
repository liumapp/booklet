package com.liumapp.netty.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * file CustomProtocolConfig.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/11/6
 */
@ConditionalOnProperty(prefix = "protocol", name = {"port"})
@Configuration
@EnableScheduling
@EnableConfigurationProperties(CustomProtocolConfig.ProtocolProperties.class)
@Slf4j
public class CustomProtocolConfig {



    @Data
    @ConfigurationProperties(prefix = "protocol")
    public static class ProtocolProperties {
        private int port;
        private long mySourceAddress;
        private long dispatchCenterAddr;
        private String eventTopic;
    }

}
