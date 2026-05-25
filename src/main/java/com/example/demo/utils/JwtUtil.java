package com.example.demo.utils;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;


import java.util.Date;
import java.util.Map;

/**
 * JWT 工具类
 * 生成 Token 与解析 Token
 */
public class JwtUtil {

    // 密钥（生产环境建议从配置文件读取，硬编码仅用于测试）
    private static final String KEY = "itheima";

    /**
     * 生成 JWT Token
     *
     * @param claims 自定义载荷（业务数据）
     * @return 生成的 Token 字符串
     */
    public static String genToken(Map<String, Object> claims) {
        // 12小时过期时间：1000ms * 60s * 60min * 12h
        long expireTime = System.currentTimeMillis() + 1000 * 60 * 60 * 12;

        return JWT.create()
                // 注入自定义载荷
                .withClaim("claims", claims)
                // 设置过期时间
                .withExpiresAt(new Date(expireTime))
                // 指定 HMAC256 算法签名
                .sign(Algorithm.HMAC256(KEY));
    }

    /**
     * 解析并验证 JWT Token
     *
     * @param token 待解析的 Token 字符串
     * @return 解析后的自定义载荷 Map
     */
    public static Map<String, Object> parseToken(String token) {
        // 构建验证器，指定密钥
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(KEY)).build();

        // 验证 Token 合法性并解析
        DecodedJWT decodedJWT = verifier.verify(token);

        // 获取自定义载荷并转换为 Map
        return decodedJWT.getClaim("claims").asMap();
    }
}