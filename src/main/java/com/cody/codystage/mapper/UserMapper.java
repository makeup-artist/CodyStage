package com.cody.codystage.mapper;

import com.cody.codystage.bean.dto.in.UserUpdateDTO;
import org.apache.ibatis.annotations.*;
import com.cody.codystage.bean.po.User;

/**
 * @Classname UserMapper
 * @Description TODO
 * @Date 2019/4/14 22:43
 * @Created by ZQ
 */
@Mapper
public interface UserMapper {

    @Insert("INSERT INTO `user`(id,username,password,age,nickname,picture,description,updateTime,createTime,isAvailable,gender,tag,mobile,email,role) " +
            "VALUES (#{id},#{username},#{password},#{age},#{nickname},#{picture},#{description}," +
            "#{updateTime},#{createTime},#{isAvailable},#{gender},#{tag},#{mobile},#{email},#{role});")
    Integer register(User user);

    User queryUserByName(@Param("username") String username);

    User queryUserById(@Param("id") Long id);

    @Select("SELECT * FROM `user` WHERE username=#{username} and password =#{password}")
    User login(@Param("username") String username, @Param("password") String password);

    @Update("update `user`" +
            "set username=#{user.username},gender=#{user.gender},age=#{user.age},mobile=#{user.mobile},nickname=#{user.nickname},picture=#{user.picture},description=#{user.description},tag=#{user.tag},email=#{user.email} " +
            "where id=#{userId}")
    Integer update(@Param("userId") Long userId,UserUpdateDTO user);


    @Update("update `user` set password=#{password} where username=#{username}")
    Integer alterPassword(@Param("username") String username, @Param("password") String password);
}
