package com.cody.codystage.service;

import com.cody.codystage.bean.po.Post;
import com.cody.codystage.bean.po.Video;
import com.cody.codystage.mapper.VideoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Classname VideoService
 * @Description TODO
 * @Date 2019/5/12 21:42
 * @Created by ZQ
 */
@Service
@Slf4j
@Transactional
public class VideoService {

    @Resource
    private VideoMapper videoMapper;

    public Integer addVideo(Video video){

        return videoMapper.addVideo(video);
    }

    public Integer updateVideo(Video video){
        return videoMapper.updateVideo(video);
    }

    public Integer deleteVideo(int id,long userid){
        return videoMapper.deleteVideo(id,userid);
    }

    public Map<String,Object> selectVideo(int id){
        return videoMapper.selectVideo(id);
    }

    public List<Map<String,Object>> getVideo(int page, int limit){
        return videoMapper.getVideo(page,limit);
    }

    public List<Map<String,Object>> getVideoByUserId(int page,int limit,long userId){
        return videoMapper.getVideoByUserId(page,limit,userId);
    }

    public List<Map<String,Object>> searchVideo(String condition,int page,int limit){
        return  videoMapper.searchVideo(condition,page,limit);
    }

    public Integer addLike(Integer id){
        return videoMapper.addLike(id);
    }

    public Integer deleteLike(Integer id){
        return videoMapper.deleteLike(id);
    }

    public List<Video> selectVideoByList(List<Integer> videoList) {
        return videoMapper.selectVideoByList(videoList);
    }
}
