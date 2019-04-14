package com.cody.codystage.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public void setString(String key, String data, Long timeout) {
        try {
            stringRedisTemplate.opsForValue().set(key, data);
            if (timeout != null) {
                stringRedisTemplate.expire(key, timeout, TimeUnit.SECONDS);
            }
        } catch (Exception e) {

        }
    }

    public void begin() {
        // 开启Redis 事务权限
        stringRedisTemplate.setEnableTransactionSupport(true);
        // 开启事务
        stringRedisTemplate.multi();
    }

    public void exec() {
        // 成功提交事务
        stringRedisTemplate.exec();
    }

    public void discard() {
        //回滚事务
        stringRedisTemplate.discard();
    }

    public void setString(String key, String data) {
        setString(key, data, null);
    }

    public String getString(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    public Boolean delKey(String key) {
        return stringRedisTemplate.delete(key);

    }


}
