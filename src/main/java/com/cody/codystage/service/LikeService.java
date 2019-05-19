package com.cody.codystage.service;

import com.cody.codystage.bean.po.Like;
import com.cody.codystage.bean.po.Post;
import com.cody.codystage.bean.po.Video;
import com.cody.codystage.mapper.LikeMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Classname LikeService
 * @Description TODO
 * @Date 2019/5/19 22:23
 * @Created by ZQ
 */
@Service
@Slf4j
public class LikeService {

    @Resource
    private LikeMapper likeMapper;

    @Resource
    private PostService postService;

    @Resource
    private VideoService videoService;

    public void addLike(Like like) {
        likeMapper.addLike(like);
    }

    public Map<String, Object> selectLike(Like like) {
        return likeMapper.selectLike(like);
    }

    public void deleteLike(Like like) {
        likeMapper.deleteLike(like);
    }

    public Map<String,Object> likeList(Long userId) {
        Map<String,Object> resMap= Maps.newHashMap();

        List<Like> likes = likeMapper.likeList(userId);
        List<Integer> postList = Lists.newArrayList();
        List<Integer> videoList = Lists.newArrayList();
        likes.forEach(e -> {
            if(e.getType()==1){
                postList.add(e.getBelong());
            }else{
                videoList.add(e.getBelong());
            }
        });

        List<Post> posts = postService.selectPostByList(postList);
        List<Video> videos = videoService.selectVideoByList(videoList);
        resMap.put("posts",posts);
        resMap.put("videos",videos);

        return resMap;
    }
}
