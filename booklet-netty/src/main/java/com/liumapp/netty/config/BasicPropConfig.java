package com.liumapp.netty.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * file BasicPropConfig.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/11/7
 */
@Data
@Slf4j
@Configuration
@ConfigurationProperties(prefix = "basic")
@EnableConfigurationProperties(BasicPropConfig.class)
public class BasicPropConfig {

    private String vipNo;

    private String protocol;

    private String cityNo;

}
