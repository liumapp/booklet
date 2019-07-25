package com.liumapp.booklet.restful.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

/**
 * file I18nConfig.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/7/25
 */
@Configuration
public class I18nConfig extends WebMvcConfigurerAdapter {

    /**
     * Session 区域解析器
     */
    @Bean(value = "localeResolver")
    public LocaleResolver sessionLocaleResolver() {
        SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
        sessionLocaleResolver.setDefaultLocale(Locale.CHINESE);
        return sessionLocaleResolver;
    }

    /**
     * 注册 LocaleChangeInterceptor 拦截器，并将其引用到任何需要支持多种语言的处理器映射
     */
    private LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("locale");
        return localeChangeInterceptor;
    }

    /**
     * LocaleChangeInterceptor 拦截器注册
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

    /**
     * 加载国际化配置文件资源
     */
    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        //设置国际化配置文件存放目录
        messageSource.setBasename("classpath:i18n/messages");
        //设置加载资源的缓存失效时间，-1表示永久有效，默认为-1
        messageSource.setCacheSeconds(-1);
        //设定 Resource Bundle 编码方式，默认为UTF-8
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}
