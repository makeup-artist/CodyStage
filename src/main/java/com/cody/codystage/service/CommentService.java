package com.cody.codystage.service;

import com.cody.codystage.bean.po.Comment;
import com.cody.codystage.mapper.CommentMapper;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @Classname CommentService
 * @Description TODO
 * @Date 2019/5/19 19:45
 * @Created by ZQ
 */
@Service
@Slf4j
public class CommentService {

    @Resource
    private CommentMapper commentMapper;

    public Integer addComment(Comment comment) {
        return commentMapper.addComment(comment);
    }

    public Integer deleteComment(Integer id, Long userId) {
        return commentMapper.deleteComment(id,userId);
    }

    public Map<Integer,Object> getComment(Integer belong,Integer type){
        Map<Integer,Object> resMap= Maps.newHashMap();
        List<Comment> comments = commentMapper.getComment(belong, type);

        comments.forEach(e->{
            if (e.getGrade()==1){
                List<Comment> list=new LinkedList<>();
                list.add(e);
                resMap.put(e.getId(),list);
            }
        });

        comments.forEach(e->{
            if (e.getGrade()==2){
                List<Comment> list = (List<Comment>) resMap.get(e.getTo_comment_id());
                list.add(e);
            }
        });
        return resMap;
    }
}
