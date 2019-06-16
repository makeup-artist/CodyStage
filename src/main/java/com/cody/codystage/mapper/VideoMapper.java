package com.cody.codystage.mapper;

import com.cody.codystage.bean.po.Post;
import com.cody.codystage.bean.po.Video;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * @Classname VideoMapper
 * @Description TODO
 * @Date 2019/5/12 21:45
 * @Created by ZQ
 */
@Mapper
public interface VideoMapper {

    Integer addVideo(Video video);

    @Select("select * from `video` where id = #{id}")
    Map<String,Object> selectVideo(@Param("id") int id);

    @Update("update `video` set url=#{url},title=#{title},cover=#{cover} where id=#{id} and author=#{author}")
    int updateVideo(Video video);

    @Delete("delete from `video` where id=#{id} and author=#{author}")
    int deleteVideo(@Param("id") int id, @Param("author") long author);

    @Select("select * from  `video` limit #{page},#{limit}")
    List<Map<String,Object>> getVideo(@Param("page") int page, @Param("limit") int limit);


    @Select("select * from `video` where author=#{userId} limit #{page},#{limit} ")
    List<Map<String,Object>> getVideoByUserId(@Param("page") int page, @Param("limit") int limit, @Param("userId") long userId);

    @Select("select * from `video` where title like CONCAT('%',#{condition},'%') order by createTime desc limit #{page},#{limit} ")
    List<Map<String,Object>> searchVideo(@Param("condition") String condition, @Param("page") int page,@Param("limit") int limit);

    @Update("update `video` set star=star+1 where id=#{id}")
    Integer addLike(@Param("id") int id);

    @Update("update `video` set star=star-1 where id=#{id}")
    Integer deleteLike(@Param("id") int id);

    List<Video> selectVideoByList(List<Integer> list);
}
