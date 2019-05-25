package com.cody.codystage.service;

import com.cody.codystage.common.constants.ResConstants;
import com.cody.codystage.common.exception.ServiceException;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Classname WishService
 * @Description TODO
 * @Date 2019/5/25 17:35
 * @Created by ZQ
 */
@Service
@Slf4j
@Transactional
public class WishService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private GoodsService goodsService;

    public void addWish(Long userId, Integer belong) {
        String key_set = "wish:" + userId+":set";
        String key_list = "wish:" + userId+":list";

        if(!redisTemplate.opsForSet().isMember(key_set,belong)){
            redisTemplate.opsForList().rightPush(key_list,belong);
            redisTemplate.opsForSet().add(key_set,belong);
        }else {
            throw new ServiceException(ResConstants.HTTP_RES_CODE_1229, ResConstants.HTTP_RES_CODE_1229_VALUE);
        }
    }

    public void deleteWish(Long userId, Integer belong) {
        String key_set = "wish:" + userId+":set";
        String key_list = "wish:" + userId+":list";

        redisTemplate.opsForList().remove(key_list,1,belong);
        redisTemplate.opsForSet().remove(key_set,belong);
    }

    public Boolean isWish(Long userId, Integer belong) {
        String key_set = "wish:" + userId+":set";
        String key_list = "wish:" + userId+":list";

        return redisTemplate.opsForSet().isMember(key_set,belong);
    }

    public List<Map<String, Object>> wishList(Long userId, int page, int limit) {
        String key_set = "wish:" + userId+":set";
        String key_list = "wish:" + userId+":list";

        List<Object> goodIdList = redisTemplate.opsForList().range(key_list, page, limit - 1);
        List<Integer> goodList= goodIdList.stream().map(e -> (Integer) e).collect(Collectors.toList());
        return goodsService.getGoodsDetailByList(goodList);
    }
}
