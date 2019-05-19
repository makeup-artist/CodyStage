package com.cody.codystage.mapper;

import com.cody.codystage.bean.po.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Classname CommentMapper
 * @Description TODO
 * @Date 2019/5/12 21:45
 * @Created by ZQ
 */
@Mapper
public interface CommentMapper {

    @Insert("insert into `comment`(author,belong,type,content,grade,to_comment_id) values(#{author},#{belong},#{type},#{content},#{grade},#{to_comment_id})")
    Integer addComment(Comment comment);

    @Delete("delete  from `comment` where id=#{id} and author =#{userId}")
    Integer deleteComment(@Param("id") Integer id,@Param("userId") Long userId);

    @Select("select * from `comment` where belong=#{belong} and type=#{type}")
    List<Comment> getComment(@Param("belong") Integer belong,@Param("type") Integer type);

}
