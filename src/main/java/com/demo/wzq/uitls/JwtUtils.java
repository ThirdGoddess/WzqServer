package com.demo.wzq.uitls;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author ThirdGoddess
 * @version 1.0.0
 * @time 2023/3/28 17:07
 * @desc JWT工具类
 */
public class JwtUtils {


    private static final String key = "5ffc47efd29754f599d6e0c0527da196876aef27";

    public static String createToken(int id) {
        Map<String, Object> param = new HashMap<>();
        param.put("salt", getRandomString(18));
        param.put("userId", id);
        return createJwtToken(String.valueOf(id), getRandomString(4), param);
    }

    private static String createJwtToken(String id, String name, Map<String, Object> map) {
        //设置失效时间
        //获取当前时间
        long now = System.currentTimeMillis();
        //当前时间+有效时间=过期时间
        //签名有效时间
        long ttl = 315360000000L;
        long exp = now + ttl;
        //创建JwtBuilder

        JwtBuilder jwtBuilder = Jwts.builder().setId(id).setSubject(name).setIssuedAt(new Date()).signWith(SignatureAlgorithm.HS256, key);

        //根据map设置clamis
        jwtBuilder.setClaims(map);
        //设置失效时间
        jwtBuilder.setExpiration(new Date(exp));
        return jwtBuilder.compact();
    }

    public static String getRandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    public static int getUserId(String token) {
        Claims claims = parseToken(token);
        if (null != claims) {
            Object userId = claims.get("userId");
            return Integer.parseInt(userId.toString());
        } else {
            return -1;
        }

    }

    private static Claims parseToken(String token) {
        try {
            return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            return null;
        }
    }


}
