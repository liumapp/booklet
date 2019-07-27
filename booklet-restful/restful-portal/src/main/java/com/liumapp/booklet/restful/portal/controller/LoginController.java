package com.liumapp.booklet.restful.portal.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * 登陆不一定要用账号密码，也可以凭借第三方授权
 *
 * 登陆成功的用户信息，通过jwt生成token，token可反向解析出用户信息
 *
 * 用户信息存放于ThreadLocal中
 *
 * 前端请求接口，将token置于请求头中
 *
 * file LoginController.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/7/27
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    

}
