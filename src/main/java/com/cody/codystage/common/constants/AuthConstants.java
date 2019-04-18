package com.cody.codystage.common.constants;

/**
 * @Classname AuthConstants
 * @Description TODO
 * @Date 2019/4/17 23:35
 * @Created by ZQ
 */
public interface AuthConstants {
    String TOKEN_HEADER = "Authorization";
    String TOKEN_PREFIX = "Bearer ";
    String SECRET = "codyStage";
    String ISS = "echisan";
    //token过期时间
    Long EXPIRATION = 3600L*8;
    //选择记住我或移动端过期时间—7天
    long EXPIRATION_REMEMBER = 604800L;
}
