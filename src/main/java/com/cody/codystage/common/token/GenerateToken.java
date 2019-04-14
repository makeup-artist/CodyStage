package com.cody.codystage.common.token;

import com.cody.codystage.utils.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class GenerateToken {
    @Autowired
    private RedisUtil redisUtil;

    public String createToken(String keyPrefix, String redisValue) throws Exception {
        return createToken(keyPrefix, redisValue, null);
    }

    public String createToken(String keyPrefix, String redisValue, Long time) throws Exception {
        if (StringUtils.isEmpty(redisValue)) {
            throw new Exception("redisValue Not nul");
        }
        String token = keyPrefix + UUID.randomUUID().toString().replace("-", "");
        redisUtil.setString(token, redisValue, time);
        return token;
    }

    public String getToken(String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        return redisUtil.getString(token);
    }

    public Boolean removeToken(String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        return redisUtil.delKey(token);

    }
}
