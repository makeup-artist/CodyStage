package com.cody.codystage.mapper;

import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * <p>Description: </p>
 * <p>Copyright: Copyright(c) 2019</p>
 * <p>Email: 1012872209@qq.com</p>
 *
 * @author ZQ
 * @date 2019/6/7:13:58
 */
@Mapper
public interface FollowMapper {


    @Insert("insert into follow(following,followed) values(#{userId},#{followId})")
    void follow(@Param("userId") Long userId,@Param("followId") Long followId);

    @Select("select * from follow where following=#{userId} and followed =#{followId}")
    Map<String,Object> isFollow(@Param("userId") Long userId,@Param("followId") Long followId);

    @Select("select count(*) from follow where following=#{id}")
    Integer queryFollowingNum(@Param("id") Long id);

    @Select("select count(*) from follow where followed=#{id}")
    Integer queryFollowedNum(@Param("id") Long id);

    @Select("select followed from follow where following=#{id} limit #{page},#{limit}")
    List<Long> getFollowing(@Param("id") Long userId,@Param("page")Integer page,@Param("limit")Integer limit);

    @Select("select following from follow where followed=#{id} limit #{page},#{limit}")
    List<Long> getFollowed(@Param("id") Long userId, @Param("page")Integer page,@Param("limit")Integer limit);

    @Delete("delete from follow where following=#{userId} and followed =#{followId}")
    void deleteFollow(@Param("userId") Long userId,@Param("followId") Long followId);
}
