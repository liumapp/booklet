package com.liumapp.booklet.restful.core.config;

import com.liumapp.booklet.restful.core.util.CheckUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

/**
 * file MessageSourceConfig.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/7/25
 */
@Configuration
public class MessageSourceConfig implements InitializingBean {

    @Autowired
    private MessageSource messageSource;

    @Override
    public void afterPropertiesSet() throws Exception {
        CheckUtil.setResources(messageSource);
    }
}
