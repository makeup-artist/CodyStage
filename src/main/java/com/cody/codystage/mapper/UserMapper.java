package com.cody.codystage.mapper;
import org.apache.ibatis.annotations.Insert;
import com.cody.codystage.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Classname UserMapper
 * @Description TODO
 * @Date 2019/4/14 22:43
 * @Created by ZQ
 */
@Mapper
public interface UserMapper {

    @Insert("INSERT INTO `user`(id,username,password,age,nickname,picture," +
            "description,updateTime,createTime,isAvailable,gender,tag,mobile,email) " +
            "VALUES (#{id},#{username},#{password},#{age,nickname},#{picture},#{description}," +
            "#{updateTime},#{createTime},#{isAvailable},#{gender},#{tag},#{mobile},#{email});")
    int register(User user);

}
