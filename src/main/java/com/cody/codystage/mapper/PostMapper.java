package com.cody.codystage.mapper;

import com.cody.codystage.bean.po.Post;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * @Classname PostMapper
 * @Description TODO
 * @Date 2019/5/12 21:44
 * @Created by ZQ
 */
@Mapper
public interface PostMapper {

    Integer addPost(Post post);

    Map<String,Object> selectPost(@Param("id") int id);

    @Update("update `post` set title=#{title},content=#{content} where id=#{id} and author=#{author}")
    int updatePost(Post post);

    @Delete("delete from `post` where id=#{id} and author=#{author}")
    int deletePost(@Param("id") int id,@Param("author") long author);

    @Select("select * from post limit #{page},#{limit}")
    List<Map<String,Object>> getPost(@Param("page") int page,@Param("limit") int limit);


    @Select("select * from post where author=#{userId} limit #{page},#{limit} ")
    List<Map<String,Object>> getPostByUserId(@Param("page") int page, @Param("limit") int limit, @Param("userId") long userId);

    @Select("select * from post where title like CONCAT('%',#{condition},'%') order by createTime desc limit #{page},#{limit} ")
    List<Map<String,Object>> searchPost(@Param("condition") String condition, @Param("page") int page,@Param("limit") int limit);

    @Update("update `post` set star=star+1 where id=#{id}")
    Integer addLike(@Param("id") int id);

    @Update("update `post` set star=star-1 where id=#{id}")
    Integer deleteLike(@Param("id") int id);

    List<Post> selectPostByList(List<Integer> list);
}
