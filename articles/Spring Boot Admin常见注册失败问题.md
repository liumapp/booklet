# Spring Boot Admin排坑指南

> Spring Boot Admin 1.x其简陋的页面让人不忍直视，但更新到2.x系列后，像脱胎换骨一般好用

这篇博客记录我个人在使用Spring Boot Admin过程中遇到过的坑，每个坑位都会附上详细的填坑办法

环境参数: 

* Spring Boot 2.x

* Spring Boot Admin 2.x

* JDK1.8+

* CentOS

## 服务直接注册失败

常见的注册失败问题可以分为以下两种

* Spring Boot Admin服务端与客户端不在同一台服务器上

* 提示安全校验不通过
 
第一种问题的解决办法:

必须在客户端配置boot.admin.client.instance.service-url属性，让Spring Boot Admin服务端可以通过网络获取客户端的数据

````yaml
  boot:
    admin:
      client:
        url: ${your spring boot admin url}
        username: ${your spring boot admin username}
        password: ${your spring boot admin password}
        instance:
          prefer-ip: true
          service-url: ${your spring boot client url} 
````

第二种问题的解决办法：

首先，安全检验问题，其实就是现在服务端配置账号密码，然后客户端在注册的时候提供账号密码进行登录来完成校验

这个过程的实现，作为Spring全家桶项目，推荐使用Spring Security来解决，所以如果出现校验失败，那多半是Spring Security的配置出现问题

接下来介绍如何分别配置服务端与客户端来处理这个问题

### 服务端配置

通过maven加载Spring Security依赖

````yaml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-security</artifactId>
</dependency>
````

设置服务端的用户名和密码（客户端来注册时使用此账号密码进行登录）

````yaml
spring:
  security:
    user:
      name: liumapp
      password: superliumapp
````

编写Spring Security配置类

````java
import de.codecentric.boot.admin.server.config.AdminServerProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

/**
 * file SecuritySecureConfig.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/11/29
 */
@Configuration
public class SecuritySecureConfig extends WebSecurityConfigurerAdapter {
    private final String adminContextPath;

    public SecuritySecureConfig(AdminServerProperties adminServerProperties) {
        this.adminContextPath = adminServerProperties.getContextPath();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
        successHandler.setTargetUrlParameter("redirectTo");
        successHandler.setDefaultTargetUrl(adminContextPath + "/");

        http.authorizeRequests()
                .antMatchers(adminContextPath + "/assets/**").permitAll()
                .antMatchers(adminContextPath + "/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage(adminContextPath + "/login").successHandler(successHandler).and()
                .logout().logoutUrl(adminContextPath + "/logout").and()
                .httpBasic().and()
                .csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .ignoringAntMatchers(
                        adminContextPath + "/instances",
                        adminContextPath + "/actuator/**"
                );
        // @formatter:on
    }
}
````

上面这段代码，需要大家注意的就一个AdminServerProperties类，通过浏览它的部分源代码:

````java
@ConfigurationProperties("spring.boot.admin")
public class AdminServerProperties {
    /**
     * The context-path prefixes the path where the Admin Servers statics assets and api should be
     * served. Relative to the Dispatcher-Servlet.
     */
    private String contextPath = "";
    
    /**
     * The metadata keys which should be sanitized when serializing to json
     */
    private String[] metadataKeysToSanitize = new String[]{".*password$", ".*secret$", ".*key$", ".*$token$", ".*credentials.*", ".*vcap_services$"};

    /**
     * For Spring Boot 2.x applications the endpoints should be discovered automatically using the actuator links.
     * For Spring Boot 1.x applications SBA probes for the specified endpoints using an OPTIONS request.
     * If the path differs from the id you can specify this as id:path (e.g. health:ping).
     */
    private String[] probedEndpoints = {"health", "env", "metrics", "httptrace:trace", "httptrace", "threaddump:dump", "threaddump", "jolokia", "info", "logfile", "refresh", "flyway", "liquibase", "heapdump", "loggers", "auditevents", "mappings", "scheduledtasks", "configprops", "caches", "beans"};
    
    //以下省略...
    
}
````

可以发现AdminServerProperties定义了Spring Boot Admin的配置属性，登录自然也是其中之一，所以我们在编写Spring Security配置类的时候，务必要引入AdminServerProperties

到这里，Spring Boot Admin服务端对于Spring Security的配置便结束了，接下来让我们开始客户端的Security配置

### 客户端配置








## 注册成功但无法显示日志

## 注册成功但信息显示不全

## 