package com.cody.codystage.service;

import com.alibaba.fastjson.JSONObject;
import com.cody.codystage.common.constants.ResConstants;
import com.cody.codystage.dto.output.UserOutDTO;
import com.cody.codystage.entity.User;
import com.cody.codystage.exception.ServiceException;
import com.cody.codystage.mapper.UserMapper;
import com.cody.codystage.security.JwtUser;
import com.cody.codystage.utils.DateUtil;
import com.cody.codystage.utils.MD5Util;
import com.cody.codystage.utils.RedisUtil;
import com.cody.codystage.utils.twitter.SnowflakeIdUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Objects;

/**
 * @Classname UserService
 * @Description TODO
 * @Date 2019/4/14 21:52
 * @Created by ZQ
 */

@Service
@Slf4j
public class UserService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public User userRegister(User user) {
        user.setId(SnowflakeIdUtil.nextId());
        user.setCreateTime(DateUtil.getCurrentTimeStamp());
        user.setUpdateTime(DateUtil.getCurrentTimeStamp());
        user.setIsAvailable(0);

        //用户名加盐加密
        String password = user.getPassword();
        user.setPassword(bCryptPasswordEncoder.encode(password));

        //权限
        user.setRole("ROLE_USER");

        //检查名称是否重复
        Boolean checkRes = checkUsernameRepeat(user.getUsername());
        if (!checkRes) {
            throw new ServiceException(ResConstants.HTTP_RES_CODE_1201, ResConstants.HTTP_RES_CODE_1201_VALUE);
        }
        redisUtil.begin();
        userMapper.register(user);
        redisUtil.setString(user.getUsername(), JSONObject.toJSONString(user));
//        redisUtil.exec();
        return user;
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
            String redisUserInfo=redisUtil.getString(username);
            if(StringUtils.isEmpty(redisUserInfo)){
                return userMapper.queryUserByName(username);
            }else {
                return (User)JSONObject.parse(redisUserInfo);
            }

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

//    public UserOutDTO cupdateUserInfoByName(String username){
//
//    }

    /**
     * spring security 实现接口
     * @param s
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userMapper.queryUserByName(s);
        return new JwtUser(user);
    }
}
