package com.cody.codystage.service;

import com.cody.codystage.mapper.FollowMapper;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>Description: </p>
 * <p>Copyright: Copyright(c) 2019</p>
 * <p>Email: 1012872209@qq.com</p>
 *
 * @author ZQ
 * @date 2019/6/7:13:53
 */
@Service
@Slf4j
@Transactional
public class FollowService {

    @Resource
    private FollowMapper followMapper;

    public Map<String, Object> getUserFollowNum(Long userId) {

        Integer followingNum = followMapper.queryFollowingNum(userId);
        Integer followedNum = followMapper.queryFollowedNum(userId);
        Map<String, Object> resMap = Maps.newHashMap();
        resMap.put("followingNum", followingNum);
        resMap.put("followedNum", followedNum);
        return resMap;
    }

    public Boolean isFollow(Long userId, Long followId) {
        return followMapper.isFollow(userId, followId) != null;
    }

    public void follow(Long userId, Long followId) {

        followMapper.follow(userId, followId);
    }

    public List<Long> getFollowing(Long userId, Integer page, Integer limit) {
        return followMapper.getFollowing(userId, (page - 1) * limit, limit);
    }

    public List<Long> getFollowed(Long userId, Integer page, Integer limit) {
        return followMapper.getFollowed(userId, (page - 1) * limit, limit);
    }

    public void deleteFollow(Long userId, Long followId){
        followMapper.deleteFollow(userId,followId);
    }
}
