package com.cody.codystage.service;

import com.cody.codystage.entity.User;
import com.cody.codystage.mapper.UserMapper;
import com.cody.codystage.utils.DateUtil;
import com.cody.codystage.utils.MD5Util;
import com.cody.codystage.utils.twitter.SnowflakeIdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Classname UserService
 * @Description TODO
 * @Date 2019/4/14 21:52
 * @Created by ZQ
 */

@Service
@Transactional
public class UserService {

    @Autowired
    private UserMapper userMapper;


    public Integer userRegister(User user){
        String password=user.getPassword();
        password= MD5Util.MD5(password);
        user.setPassword(password);
        user.setId(SnowflakeIdUtil.nextId());
        user.setCreateTime(DateUtil.getCurrentTimeStamp());
        user.setUpdateTime(DateUtil.getCurrentTimeStamp());
        user.setIsAvailable(0);
        return userMapper.register(user);
    }

}
