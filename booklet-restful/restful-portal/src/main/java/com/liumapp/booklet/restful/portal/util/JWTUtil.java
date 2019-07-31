package com.liumapp.booklet.restful.portal.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * file JWTUtil.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/7/27
 */
@Component
@Slf4j
public class JWTUtil {
    /**
     * 默认token有效时间30分钟
     */
    private final static Long TOKEN_EXPIRESE = 120 * 60 * 1000L;

    @Autowired
    private HttpServletRequest request;

    /**
     * 生成token
     * @param userName 用户名
     * @param secret 密码
     * @return
     */
    public static String generalToken(String userName,String secret) {
        Date date = new Date(System.currentTimeMillis() + TOKEN_EXPIRESE);
        Algorithm algorithm = Algorithm.HMAC256(secret);

        return JWT.create()
                .withClaim("username", userName)
                .withExpiresAt(date)
                .sign(algorithm);
    }

    /**
     * 校验token合法性会自动校验是否过期
     * @param token
     * @param userName
     * @param secret
     * @return
     */
    public static Boolean checkToken(String token,String userName,String secret) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier jwtVerifier = JWT.require(algorithm)
                    .withClaim("username",userName)
                    .build();
            jwtVerifier.verify(token);
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
    public static String getUserName(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            System.out.println(jwt.getExpiresAt());
            return jwt.getClaim("username").asString();
        } catch (JWTDecodeException e) {
            log.error("error：{}", e.getMessage());
            return null;
        }
    }

    public  String getToken() {
        return request.getHeader("token");
    }
}
