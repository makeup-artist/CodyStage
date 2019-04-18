package com.cody.codystage.utils;

import com.cody.codystage.common.constants.AuthConstants;
import com.cody.codystage.common.constants.ResConstants;
import com.cody.codystage.exception.ServiceException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Objects;

/**
 * @Classname JwtTokenUtil
 * @Description TODO
 * @Date 2019/4/17 23:25
 * @Created by ZQ
 */
public class JwtTokenUtil {

    /**
     * 生成token
     * @param id 用户ID
     * @param isRemember true时间更长(选择记住或移动端)
     * @return
     */
    public static String createToken(Long id, boolean isRemember) {
        long expiration = isRemember ? AuthConstants.EXPIRATION_REMEMBER : AuthConstants.EXPIRATION;
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, AuthConstants.SECRET)
                .setIssuer(AuthConstants.ISS)
                .setSubject(id.toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * expiration))
                .compact();
    }

    /**
     * 从token获取用户ID
     * @param token
     * @return
     */
    public static Long getUserId(String token) {
        String id = getTokenBody(token).getSubject();
        if(Objects.isNull(id)){
            throw new ServiceException(ResConstants.HTTP_RES_CODE_401,ResConstants.HTTP_RES_CODE_401_VALUE);
        }
        return Long.valueOf(id);
    }

    /**
     * 是否过期
     *
     * @param token
     * @return
     */
    public static boolean isExpiration(String token) {
        return getTokenBody(token).getExpiration().before(new Date());
    }

    private static Claims getTokenBody(String token) {
        return Jwts.parser()
                .setSigningKey(AuthConstants.SECRET)
                .parseClaimsJws(token)
                .getBody();
    }
}
