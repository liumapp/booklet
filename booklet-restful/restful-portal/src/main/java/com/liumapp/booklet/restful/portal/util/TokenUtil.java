package com.liumapp.booklet.restful.portal.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.liumapp.booklet.restful.core.db.entity.Users;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * file TokenUtil.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/7/27
 */
@Component
@Slf4j
public class TokenUtil {

    /**
     * 默认token有效时间两小时
     */
    private final static Long TOKEN_EXPIRESE = 120 * 60 * 1000L;

    @Autowired
    private HttpServletRequest request;

    /**
     * 获取登录token
     * @param users
     */
    public String generateToken(Users users){
        Date date = new Date(System.currentTimeMillis() + TOKEN_EXPIRESE);
        Algorithm algorithm = Algorithm.HMAC256(users.getPhone());

        return JWT.create()
                .withClaim("phone", users.getPhone())
                .withClaim("id",users.getId())
                .withExpiresAt(date)
                .sign(algorithm);
    }


    /**
     * 校验token合法性会自动校验是否过期
     */
    public Boolean checkToken(Users users) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(users.getPhone());
            JWTVerifier jwtVerifier = JWT.require(algorithm)
                    .withClaim("phone",users.getPhone())
                    .withClaim("id",users.getId())
                    .build();
            jwtVerifier.verify(getToken());
        } catch (Exception e) {
            log.error("token is invalid{}", e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 解析token 获取username
     * @return
     */
    public  String getUserName() {
        try {
            DecodedJWT jwt = JWT.decode(this.getToken());
            System.out.println(jwt.getExpiresAt());
            return jwt.getClaim("username").asString();
        } catch (JWTDecodeException e) {
            log.error("error：{}", e.getMessage());
            return null;
        }
    }

    /**
     * 获取adminId
     * @return
     */
    public Long getAdminId() {
        try {
            DecodedJWT jwt = JWT.decode(this.getToken());
            System.out.println(jwt.getExpiresAt());
            return jwt.getClaim("id").asLong();
        } catch (JWTDecodeException e) {
            log.error("error：{}", e.getMessage());
            return 1l;
        }
    }

    public  String getToken() {
        return request.getHeader("token");
    }
}
