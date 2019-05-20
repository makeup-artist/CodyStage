package com.cody.codystage.service;

import com.cody.codystage.bean.dto.in.PostUpdateInDTO;
import com.cody.codystage.bean.po.Post;
import com.cody.codystage.mapper.PostMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Classname PostService
 * @Description TODO
 * @Date 2019/5/12 21:42
 * @Created by ZQ
 */
@Service
@Slf4j
@Transactional
public class PostService {

    @Resource
    PostMapper postMapper;

    public Integer addPost(Post post) {

        return postMapper.addPost(post);
    }

    public Integer updatePost(Post post) {
        return postMapper.updatePost(post);
    }

    public Integer deletePost(int id, long userid) {
        return postMapper.deletePost(id, userid);
    }

    public Map<String, Object> selectPost(int id) {
        return postMapper.selectPost(id);
    }

    public List<Map<String, Object>> getPost(int page, int limit) {
        return postMapper.getPost(page, limit);
    }

    public List<Map<String, Object>> getPostByUserId(int page, int limit, long userId) {
        return postMapper.getPostByUserId(page, limit, userId);
    }

    public List<Map<String, Object>> searchPost(String condition, int page, int limit) {
        return postMapper.searchPost(condition, page, limit);
    }

    public Integer addLike(Integer id) {
        return postMapper.addLike(id);
    }

    public Integer deleteLike(Integer id) {
        return postMapper.deleteLike(id);
    }

    public List<Post> selectPostByList(List<Integer> postList) {
        return postMapper.selectPostByList(postList);
    }
}
