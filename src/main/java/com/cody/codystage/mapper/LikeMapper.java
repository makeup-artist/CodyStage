package com.cody.codystage.mapper;

import com.cody.codystage.bean.po.Like;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * @Classname LikeMapper
 * @Description TODO
 * @Date 2019/5/19 22:58
 * @Created by ZQ
 */
@Mapper
public interface LikeMapper {

    @Insert("insert into `like`(author,type,belong) values(#{author},#{type},#{belong})")
    Integer addLike(Like like);

    @Select("select * from `like` where author=#{author} and type=#{type} and belong=#{belong}")
    Map<String,Object> selectLike(Like like);

    @Delete("delete from `like` where author=#{author} and type=#{type} and belong=#{belong} ")
    void deleteLike(Like like);

    @Select("select * from `like` where author=#{userId} limit #{page},#{limit} ")
    List<Like> likeList(@Param("page") int page, @Param("limit") int limit,@Param("userId") Long userId);
}
