package com.cody.codystage.service;

import com.cody.codystage.common.constants.ResConstants;
import com.cody.codystage.entity.User;
import com.cody.codystage.exception.ServiceException;
import com.cody.codystage.mapper.UserMapper;
import com.cody.codystage.utils.DateUtil;
import com.cody.codystage.utils.MD5Util;
import com.cody.codystage.utils.twitter.SnowflakeIdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @Classname UserService
 * @Description TODO
 * @Date 2019/4/14 21:52
 * @Created by ZQ
 */

@Service
@Slf4j
public class UserService {

    @Value("${user.pwd.salt}")
    private String salt;

    @Autowired
    private UserMapper userMapper;


    public User userRegister(User user) {
        user.setId(SnowflakeIdUtil.nextId());
        user.setCreateTime(DateUtil.getCurrentTimeStamp());
        user.setUpdateTime(DateUtil.getCurrentTimeStamp());
        user.setIsAvailable(0);

        //用户名加盐加密
        String password = user.getPassword();
        user.setPassword(MD5Util.getSaltMD5(password, salt));

        //检查名称是否重复
        Boolean checkRes = checkUsernameRepeat(user.getUsername());
        if (!checkRes) {
            throw new ServiceException(ResConstants.HTTP_RES_CODE_1201, ResConstants.HTTP_RES_CODE_1201_VALUE);
        }

        userMapper.register(user);

        User userInfo = userMapper.queryUserByName(user.getUsername());

        return userInfo;
    }

    public Boolean checkUsernameRepeat(String username) {
        try {
            User user = userMapper.queryUserByName(username);
            return Objects.isNull(user);
        } catch (Exception e) {
            log.error("查询用户失败,mag= ", e);
            throw new ServiceException(ResConstants.HTTP_RES_CODE_500, "查询用户失败");
        }
    }

    public User getUserInfo(String username) {
        try {
            return userMapper.queryUserByName(username);
        } catch (Exception e) {
            log.error("查询用户失败,mag= ", e);
            throw new ServiceException(ResConstants.HTTP_RES_CODE_500, "查询用户失败");
        }
    }

    public User getUserInfo(Long id) {
        try {
            return userMapper.queryUserById(id);
        } catch (Exception e) {
            log.error("查询用户失败,mag= ", e);
            throw new ServiceException(ResConstants.HTTP_RES_CODE_500, "查询用户失败");
        }
    }
}
