package com.car.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.car.constants.TokenConstant.*;

/**
 * @Author  : ZNLCCY
 * @Version : 1.0.0
 * @Date    : 2023-12-20 16:05
 * @FileName: JwtUtil.java
 * @Desc    : Jwt工具
 **/
public class JwtUtil {

    /**
     * 生成token
     * @param username
     * @param password
     * @return
     */
    public static String generateToken(String username, String password) {

        // 过期时间
        Date expiredDate = Date.from(Instant.now().plus(ACCESS_EXPIRED, ChronoUnit.HOURS));

        // 载荷
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", username);
        claims.put("password", password);

        return Jwts.builder()
                // 载荷
                .claims(claims)
                // 获取时间
                .expiration(expiredDate)
                // 签发时间
                .issuedAt(new Date())
                // 主题
                .subject(SUBJECT)
                // 签发者
                .issuer(JWT_ISS)
                // 签名
                .signWith(KEY, ALGORITHM)
                .compact();
    }

    /**
     * 获取载荷
     * @param token
     * @return
     */
    public static Claims getClaimsFromToken(String token) {
        return Jwts.parser().verifyWith(KEY).build().parseSignedClaims(token).getPayload();
    }

    /**
     * 获取用户名
     * @param token
     * @return
     */
    public static String getUsernameByToken(String token) {
        return (String) getClaimsFromToken(token).get("username");
    }

    /**
     * 校验token是否合法
     * @param token
     * @return
     */
    public static Boolean validateToken(String token) {
        // 验证token是否
        if (token == null || expiredToken(token)) {
            return false;
        }
        return true;
    }

    /**
     * 验证token是否过期
     * @param token
     * @return
     */
    private static boolean expiredToken(String token) {
        Date expiredDate = getExpiredDateFromToken(token);
        return expiredDate.before(new Date());
    }

    /**
     * 获取token过期时间
     * @param token
     * @return
     */
    private static Date getExpiredDateFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }
}
