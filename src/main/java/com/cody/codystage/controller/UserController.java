package com.cody.codystage.controller;

import com.cody.codystage.common.base.BaseApiService;
import com.cody.codystage.common.base.BaseResponse;
import com.cody.codystage.common.constants.ResConstants;
import com.cody.codystage.dto.input.UserInputDTO;
import com.cody.codystage.dto.output.UserOutDTO;
import com.cody.codystage.entity.User;
import com.cody.codystage.exception.ServiceException;
import com.cody.codystage.service.UserService;
import com.cody.codystage.utils.CodyBeanUtils;
import com.cody.codystage.utils.RequestUtil;
import com.google.common.collect.Maps;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;
import java.util.Objects;

/**
 * @Classname UserController
 * @Description TODO
 * @Date 2019/4/14 19:56
 * @Created by ZQ
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户API")
public class UserController extends BaseApiService<Object> {
    @Autowired
    UserService userService;

    @PostMapping("/register")
    @ApiOperation(value = "用户注册")
    public BaseResponse<Object> addUser(@Valid UserInputDTO userInputDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ServiceException(ResConstants.HTTP_RES_CODE_400, ResConstants.HTTP_RES_CODE_400_VALUE);
        }
        User user = CodyBeanUtils.beanCopyPropertoes(userInputDTO, User.class);
        User userInfo = userService.userRegister(user);
        UserOutDTO userOutDTO = CodyBeanUtils.beanCopyPropertoes(userInfo, UserOutDTO.class);
        return setResult(ResConstants.HTTP_RES_CODE_200, "注册成功", userOutDTO);
    }

    @GetMapping(value = "/register/check", params = "username")
    @ApiOperation(value = "用户名重复检测", notes = "返回0代表无重复")
    public Map<String, Object> checkUsername(HttpServletRequest request) {
        Map<String, Object> resMap = Maps.newHashMap();
        String username = RequestUtil.getString(request, "username", "");
        if (StringUtils.isEmpty(username)) {
            resMap.put("repeat", true);
            return resMap;
        }
        Boolean isRepeat = userService.checkUsernameRepeat(username);
        if (isRepeat) {
            resMap.put("repeat", 0);
        } else {
            resMap.put("repeat", 1);
        }

        return resMap;
    }

    @GetMapping(value = "/userInfo/username", params = "username")
    @ApiOperation(value = "根据用户名查询用户信息")
    public UserOutDTO getUserInfoByName(HttpServletRequest request) {
        if(request.getParameter("username").isEmpty()){
            throw new ServiceException(ResConstants.HTTP_RES_CODE_400, "传入参数格式错误");
        }
        String username = RequestUtil.getString(request, "username", "");
        User userInfo = userService.getUserInfo(username);
        if(Objects.isNull(userInfo)){
            throw new ServiceException(ResConstants.HTTP_RES_CODE_1202, ResConstants.HTTP_RES_CODE_12012_VALUE);
        }
        return CodyBeanUtils.beanCopyPropertoes(userInfo, UserOutDTO.class);
    }

    @GetMapping(value = "/userInfo/id", params = "id")
    @ApiOperation(value = "根据用户ID查询用户信息")
    public UserOutDTO getUserInfoById(HttpServletRequest request) {
        if(request.getParameter("id").isEmpty()){
            throw new ServiceException(ResConstants.HTTP_RES_CODE_401, ResConstants.HTTP_RES_CODE_401_VALUE);
        }
        Long id = RequestUtil.getLong(request, "id", 0L);
        User userInfo = userService.getUserInfo(id);
        if(Objects.isNull(userInfo)){
            throw new ServiceException(ResConstants.HTTP_RES_CODE_1202, ResConstants.HTTP_RES_CODE_12012_VALUE);
        }
        return CodyBeanUtils.beanCopyPropertoes(userInfo, UserOutDTO.class);
    }

}
