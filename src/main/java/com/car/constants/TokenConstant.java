package com.car.constants;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecureDigestAlgorithm;

import javax.crypto.SecretKey;

/**
 * @Author  : ZNLCCY
 * @Version : 1.0.0
 * @Date    : 2023-12-20 22:25
 * @FileName: TokenConstant.java
 * @Desc    : Token常量
 **/
public class TokenConstant {

    // 秘钥
    public static final String SECRET = "123456789ABCDEFGHIJKLMNOPQRSTuVWXYZ";

    // 加密算法
    public final static SecureDigestAlgorithm<SecretKey, SecretKey> ALGORITHM = Jwts.SIG.HS256;

    // 秘钥实例
    public static final SecretKey KEY = Keys.hmacShaKeyFor(SECRET.getBytes());

    // JWT签发者
    public static final String JWT_ISS = "zc";

    // JWT主题
    public static final String SUBJECT = "zccar";

    // 过期时间
    public static final Long ACCESS_EXPIRED = 60l;

}
