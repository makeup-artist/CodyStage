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

    @Insert("INSERT INTO `user`(id,username,password,age,nickname,picture,description,updateTime,createTime,isAvailable,gender,tag,email,role) " +
            "VALUES (#{id},#{username},#{password},#{age},#{nickname},#{picture},#{description}," +
            "#{updateTime},#{createTime},#{isAvailable},#{gender},#{tag},#{email},#{role});")
    Integer register(User user);

    @Insert("insert into `user`(id,username,password,mobile,updateTime,createTime,isAvailable,role) values(#{id},#{username},#{password},#{mobile},#{updateTime},#{createTime},#{isAvailable},#{role})")
    Integer registerByCode(User user);

    User queryUserByName(@Param("username") String username);

    User queryUserById(@Param("id") Long id);

    @Select("SELECT * FROM `user` WHERE username=#{username} and password =#{password}")
    User login(@Param("username") String username, @Param("password") String password);

    @Update("update `user`" +
            "set username=#{user.username},gender=#{user.gender},age=#{user.age},nickname=#{user.nickname},picture=#{user.picture},description=#{user.description},tag=#{user.tag},email=#{user.email} " +
            "where id=#{userId}")
    Integer update(@Param("userId") Long userId, UserUpdateDTO user);

    @Update("update `user` set mobile=#{mobile} where id=#{userId}")
    void updateMobile(@Param("userId") Long userId,@Param("mobile") String mobile);

    @Update("update `user` set password=#{password} where username=#{username}")
    Integer alterPassword(@Param("username") String username, @Param("password") String password);

    @Select("select * from `user` where mobile=#{mobile}")
    User queryUserByMobile(@Param("mobile") String mobile);
}
