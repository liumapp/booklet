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

首先，安全检验问题，其实就是现在服务端配置账号密码，然后客户端在注册的时候提及账号密码进行登录来完成校验

这个过程的实现，作为Spring全家桶项目，推荐使用Spring Security来解决，所以如果出现校验失败，那多半是Spring Security的配置出现问题








## 注册成功但无法显示日志

## 注册成功但信息显示不全

## 