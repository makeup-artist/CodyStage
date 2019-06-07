package com.cody.codystage.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.cody.codystage.bean.dto.in.*;
import com.cody.codystage.bean.dto.out.UserOutDTO;
import com.cody.codystage.common.constants.RedisConstants;
import com.cody.codystage.common.constants.ResConstants;
import com.cody.codystage.bean.po.User;
import com.cody.codystage.common.exception.ServiceException;
import com.cody.codystage.mapper.UserMapper;
import com.cody.codystage.utils.CodyBeanUtils;
import com.cody.codystage.utils.DateUtil;
import com.cody.codystage.utils.JwtTokenUtil;
import com.cody.codystage.utils.MD5Util;
import com.cody.codystage.utils.twitter.SnowflakeIdUtil;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
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
@Transactional
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    RedisService redisService;

    @Value("${user.pwd.salt}")
    String salt;

    @Value("${aliyun.message.accessKeyId}")
    private String accessKeyId;

    @Value("${aliyun.message.accessKeySecret}")
    private String accessSecret;

    @Value("${aliyun.message.SignName}")
    private String SignName;

    @Value("${aliyun.message.TemplateCode}")
    private String TemplateCode;

//    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;


    public String userRegister(User user, HttpServletResponse httpServletResponse) {

        user.setId(SnowflakeIdUtil.nextId());
        user.setCreateTime(DateUtil.getCurrentTimeStamp());
        user.setUpdateTime(DateUtil.getCurrentTimeStamp());
        user.setIsAvailable(0);

        //用户名加盐加密
        String password = user.getPassword();
        user.setPassword(MD5Util.getSaltMD5(password, salt));

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
        redisService.set(RedisConstants.USERINFO + user.getId(), JSONObject.toJSONString(user, SerializerFeature.WriteNullStringAsEmpty));

        return JwtTokenUtil.createToken(user.getId(), user.getRole(), true);
    }

    public Map<String, Object> userRegisterByCode(UserRegisterCodeInDTO userInputDTO) {
        Map<String, Object> resMap = Maps.newHashMap();
        String key = "message:" + userInputDTO.getMobile();

        Boolean checkRes = checkUsernameRepeat(userInputDTO.getUsername());
        if (!checkRes) {
            throw new ServiceException(ResConstants.HTTP_RES_CODE_1201, ResConstants.HTTP_RES_CODE_1201_VALUE);
        }

        if (!checkMobileRepeat(userInputDTO.getMobile())) {
            throw new ServiceException(ResConstants.HTTP_RES_CODE_1226, ResConstants.HTTP_RES_CODE_1226_VALUE);
        }

        Integer code = (Integer) redisService.get(key);

        if (Objects.isNull(code)) {
            throw new ServiceException(ResConstants.HTTP_RES_CODE_1228, ResConstants.HTTP_RES_CODE_1228_VALUE);
        }

        if (userInputDTO.getCode().equals(code.toString())) {
            User user = new User();
            Long id = SnowflakeIdUtil.nextId();
            user.setId(id);
            user.setIsAvailable(0);
            user.setUsername(userInputDTO.getUsername());
            user.setPassword(MD5Util.getSaltMD5(userInputDTO.getPassword(), salt));
            user.setCreateTime(DateUtil.getCurrentTimeStamp());
            user.setUpdateTime(DateUtil.getCurrentTimeStamp());
            user.setRole("ROLE_USER");
            user.setMobile(userInputDTO.getMobile());
            userMapper.registerByCode(user);
            redisService.set(RedisConstants.USERINFO + user.getId(), JSONObject.toJSONString(user, SerializerFeature.WriteNullStringAsEmpty));
            resMap.put("token", JwtTokenUtil.createToken(user.getId(), user.getRole(), true));
            resMap.put("user", user);
        } else {
            throw new ServiceException(ResConstants.HTTP_RES_CODE_1227, ResConstants.HTTP_RES_CODE_1227_VALUE);
        }
        return resMap;
    }

    /**
     * @param username
     * @return true 代表不重复
     */
    public Boolean checkUsernameRepeat(String username) {
        try {

            User user = userMapper.queryUserByName(username);
            return Objects.isNull(user);
        } catch (Exception e) {
            log.error("查询用户失败,mag= ", e);
            throw new ServiceException(ResConstants.HTTP_RES_CODE_500, "查询用户失败");
        }
    }

    public String getCode(String mobile) {
        String key = "message:" + mobile;
        long expireTime = redisService.getExpire(key);
        if (expireTime > 4 * 60) {
            throw new ServiceException(ResConstants.HTTP_RES_CODE_1225, ResConstants.HTTP_RES_CODE_1225_VALUE);
        }

        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessSecret);
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", mobile);
        request.putQueryParameter("TemplateCode", TemplateCode);
        request.putQueryParameter("SignName", SignName);

//        随机产生规定范围内数字(1000,9999)
        int num = (int) (Math.random() * 8998) + 1000 + 1;
        JSONObject templateParam = new JSONObject();
        templateParam.put("code", num);
//        templateParam.toJSONString()
        request.putQueryParameter("TemplateParam", templateParam.toJSONString());

        CommonResponse response = null;
        JSONObject jsonObject;
        try {
            response = client.getCommonResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        } finally {
            assert response != null;
            jsonObject = JSON.parseObject(response.getData());
            if ("OK".equals(jsonObject.get("Code"))) {
                redisService.set(key, num, 5 * 60);
            } else {
                throw new ServiceException(ResConstants.HTTP_RES_CODE_400, (String) jsonObject.get("Message"));
            }
        }


        return (String) jsonObject.get("Message");
    }

    /**
     * @param mobile
     * @return true 代表不重复
     */
    public Boolean checkMobileRepeat(String mobile) {
        try {
            User user = userMapper.queryUserByMobile(mobile);
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
            String key = RedisConstants.USERINFO + id;
            User user = JSON.parseObject((String) redisService.get(key), User.class);
            if (Objects.isNull(user)) {
                user = userMapper.queryUserById(id);
            }
            return user;
        } catch (Exception e) {
            log.error("查询用户失败,mag= ", e);
            throw new ServiceException(ResConstants.HTTP_RES_CODE_500, "查询用户失败");
        }
    }

    public User updateUserInfo(Long userId, UserUpdateDTO user) {
        if (userMapper.update(userId, user) == 0) {
            throw new ServiceException(ResConstants.HTTP_RES_CODE_1209, ResConstants.HTTP_RES_CODE_1209_VALUE);
        }
        User userInfo = getUserInfo(user.getUsername());
        redisService.set(RedisConstants.USERINFO + userInfo.getId(), JSONObject.toJSONString(userInfo, SerializerFeature.WriteNullStringAsEmpty));
        return userInfo;
    }

    public Map<String, Object> login(UserLoginDTO userLoginDTO) {
        Map<String, Object> resMap = Maps.newHashMap();

        String password = userLoginDTO.getPassword();
        userLoginDTO.setPassword(MD5Util.getSaltMD5(password, salt));
        User user = userMapper.login(userLoginDTO.getUsername(), userLoginDTO.getPassword());
        UserOutDTO userOutDTO = CodyBeanUtils.beanCopyPropertoes(user, UserOutDTO.class);

        if (Objects.isNull(user)) {
            throw new ServiceException(ResConstants.HTTP_RES_CODE_1202, ResConstants.HTTP_RES_CODE_1202_VALUE);
        } else {
            resMap.put("token", JwtTokenUtil.createToken(user.getId(), user.getRole(), true));
            resMap.put("user", userOutDTO);
        }
        return resMap;
    }

    public Map<String, Object> loginByCode(UserLoginCodeInDTO inputDTO) {
        Map<String, Object> resMap = Maps.newHashMap();
        String key = "message:" + inputDTO.getMobile();
        Integer code = (Integer) redisService.get(key);

        if (Objects.isNull(code)) {
            throw new ServiceException(ResConstants.HTTP_RES_CODE_1228, ResConstants.HTTP_RES_CODE_1228_VALUE);
        }
        if (inputDTO.getCode().equals(code.toString())) {
            User user = userMapper.queryUserByMobile(inputDTO.getMobile());
            UserOutDTO userOutDTO = CodyBeanUtils.beanCopyPropertoes(user, UserOutDTO.class);
            if (Objects.isNull(user)) {
                throw new ServiceException(ResConstants.HTTP_RES_CODE_1202, ResConstants.HTTP_RES_CODE_1202_VALUE);
            }
            resMap.put("token", JwtTokenUtil.createToken(user.getId(), user.getRole(), true));
            resMap.put("user", userOutDTO);
            return resMap;
        } else {
            throw new ServiceException(ResConstants.HTTP_RES_CODE_1227, ResConstants.HTTP_RES_CODE_1227_VALUE);
        }
    }

    public Boolean alterPassword(UserAlterDTO userAlterDTO) {

        userAlterDTO.setNewPassword(MD5Util.getSaltMD5(userAlterDTO.getNewPassword(), salt));
        userAlterDTO.setOldPassword(MD5Util.getSaltMD5(userAlterDTO.getOldPassword(), salt));

        User user = userMapper.login(userAlterDTO.getUsername(), userAlterDTO.getOldPassword());

        if (Objects.isNull(user)) {
            throw new ServiceException(ResConstants.HTTP_RES_CODE_1205, ResConstants.HTTP_RES_CODE_1205_VALUE);
        }

        Integer integer = userMapper.alterPassword(userAlterDTO.getUsername(), userAlterDTO.getNewPassword());
        if (integer == 1) {
            User userInfo = userMapper.queryUserByName(userAlterDTO.getUsername());
            redisService.set(RedisConstants.USERINFO + userInfo.getId(), JSONObject.toJSONString(userInfo, SerializerFeature.WriteNullStringAsEmpty));
            return true;
        } else {
            return false;
        }
    }

    public void updateMobile(UserLoginCodeInDTO userInputDTO, Long userId) {

        if (!checkMobileRepeat(userInputDTO.getMobile())) {
            throw new ServiceException(ResConstants.HTTP_RES_CODE_1226, ResConstants.HTTP_RES_CODE_1226_VALUE);
        }
        String key = "message:" + userInputDTO.getMobile();
        Integer code = (Integer) redisService.get(key);

        if (Objects.isNull(code)) {
            throw new ServiceException(ResConstants.HTTP_RES_CODE_1228, ResConstants.HTTP_RES_CODE_1228_VALUE);
        } else {
            userMapper.updateMobile(userId, userInputDTO.getMobile());
            User user = userMapper.queryUserByMobile(userInputDTO.getMobile());
            redisService.set(RedisConstants.USERINFO + user.getId(), JSONObject.toJSONString(user, SerializerFeature.WriteNullStringAsEmpty));
        }
    }

    public List<UserOutDTO> getUserInfoByList(UserIdListInDto userIdListInDto) {

        return userMapper.queryUserInfoByList(userIdListInDto.getIdList());

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
