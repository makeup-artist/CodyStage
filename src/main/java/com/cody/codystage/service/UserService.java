package com.cody.codystage.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.cody.codystage.common.constants.RedisConstants;
import com.cody.codystage.common.constants.ResConstants;
import com.cody.codystage.dto.input.UserAlterDTO;
import com.cody.codystage.dto.input.UserLoginDTO;
import com.cody.codystage.dto.input.UserUpdateDTO;
import com.cody.codystage.dto.output.UserOutDTO;
import com.cody.codystage.entity.User;
import com.cody.codystage.exception.ServiceException;
import com.cody.codystage.mapper.UserMapper;
import com.cody.codystage.utils.DateUtil;
import com.cody.codystage.utils.JwtTokenUtil;
import com.cody.codystage.utils.MD5Util;
import com.cody.codystage.utils.twitter.SnowflakeIdUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @Classname UserService
 * @Description TODO
 * @Date 2019/4/14 21:52
 * @Created by ZQ
 * implements UserDetailsService spring security账户验证
 */

@Service
@Slf4j
public class UserService  {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    RedisService redisService;

    @Value("${user.pwd.salt}")
    String salt;

//    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public String userRegister(User user, HttpServletResponse httpServletResponse) {

        user.setId(SnowflakeIdUtil.nextId());
        user.setCreateTime(DateUtil.getCurrentTimeStamp());
        user.setUpdateTime(DateUtil.getCurrentTimeStamp());
        user.setIsAvailable(0);

        //用户名加盐加密
        String password = user.getPassword();
        user.setPassword(MD5Util.getSaltMD5(password,salt));

        //spring security加密密码
//        user.setPassword(bCryptPasswordEncoder.encode(password));

        //权限
        user.setRole("ROLE_USER");

        //检查名称是否重复
        Boolean checkRes = checkUsernameRepeat(user.getUsername());
        if (!checkRes) {
            throw new ServiceException(ResConstants.HTTP_RES_CODE_1201, ResConstants.HTTP_RES_CODE_1201_VALUE);
        }

        //同时插往数据库和redis
        userMapper.register(user);
        redisService.set(RedisConstants.USERINFO+user.getId(), JSONObject.toJSONString(user, SerializerFeature.WriteNullStringAsEmpty));

        return JwtTokenUtil.createToken(user.getId(), user.getRole(), true);
    }

    /**
     *
     * @param username
     * @return true 代表不重复
     */
    public Boolean checkUsernameRepeat(String username) {
        try {

            User user = userMapper.queryUserByName(username);
            System.out.println(user);
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
            String key=RedisConstants.USERINFO+id;
            User user = JSON.parseObject((String) redisService.get(key),User.class);
            if(Objects.isNull(user)){
                user = userMapper.queryUserById(id);
            }
            return user;
        } catch (Exception e) {
            log.error("查询用户失败,mag= ", e);
            throw new ServiceException(ResConstants.HTTP_RES_CODE_500, "查询用户失败");
        }
    }

    public User updateUserInfo(Long userId,UserUpdateDTO user){
        if(userMapper.update(userId, user)==0){
            throw new ServiceException(ResConstants.HTTP_RES_CODE_1209, ResConstants.HTTP_RES_CODE_1209_VALUE);
        }
        User userInfo = getUserInfo(user.getUsername());
        redisService.set(RedisConstants.USERINFO+userInfo.getId(), JSONObject.toJSONString(userInfo, SerializerFeature.WriteNullStringAsEmpty));
        return userInfo;
    }

    public String login(UserLoginDTO userLoginDTO){

        String password = userLoginDTO.getPassword();
        userLoginDTO.setPassword(MD5Util.getSaltMD5(password,salt));
        User user = userMapper.login(userLoginDTO.getUsername(), userLoginDTO.getPassword());

        if(Objects.isNull(user)){
            return null;
        }else {
            return JwtTokenUtil.createToken(user.getId(), user.getRole(), true);
        }
    }

    public Boolean alterPassword(UserAlterDTO userAlterDTO){

        userAlterDTO.setNewPassword(MD5Util.getSaltMD5(userAlterDTO.getNewPassword(),salt));
        userAlterDTO.setOldPassword(MD5Util.getSaltMD5(userAlterDTO.getOldPassword(),salt));

        User user = userMapper.login(userAlterDTO.getUsername(),userAlterDTO.getOldPassword());

        if(Objects.isNull(user)){
            throw new ServiceException(ResConstants.HTTP_RES_CODE_1205,ResConstants.HTTP_RES_CODE_1205_VALUE);
        }

        Integer integer = userMapper.alterPassword(userAlterDTO.getUsername(),userAlterDTO.getNewPassword());
        if(integer==1){
            User userInfo = userMapper.queryUserByName(userAlterDTO.getUsername());
            redisService.set(RedisConstants.USERINFO+userInfo.getId(), JSONObject.toJSONString(userInfo, SerializerFeature.WriteNullStringAsEmpty));
            return true;
        }else {
            return false;
        }
    }

    /**
     * spring security 实现接口
     * @param s
     * @return
     * @throws UsernameNotFoundException
     */
//    @Override
//    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
//        User user = userMapper.queryUserByName(s);
//        return new JwtUser(user);
//    }
}
