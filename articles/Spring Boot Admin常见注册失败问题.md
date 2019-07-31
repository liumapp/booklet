# Spring Boot Admin排坑指南

> Spring Boot Admin 1.x其简陋的页面让人不忍直视，但更新到2.x系列后，像脱胎换骨一般好用

这篇博客记录我个人在使用Spring Boot Admin过程中遇到过的坑，每个坑都会详细附上填坑办法。

环境参数: 

* Spring Boot 2.x

* Spring Boot Admin 2.x

* JDK1.8+

* CentOS

## 服务注册失败

常见的注册失败问题可以分为以下三种

* Spring Boot Admin服务端与客户端不在同一台服务器上

* 客户端配置了SSL数字证书

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




## 无法显示日志

## 信息显示不全

## 