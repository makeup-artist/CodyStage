package com.cody.codystage.controller;

import com.cody.codystage.bean.dto.in.*;
import com.cody.codystage.common.base.BaseApiService;
import com.cody.codystage.common.base.BaseResponse;
import com.cody.codystage.common.constants.ResConstants;
import com.cody.codystage.bean.dto.out.UserOutDTO;
import com.cody.codystage.bean.po.User;
import com.cody.codystage.common.exception.ServiceException;
import com.cody.codystage.service.CommonService;
import com.cody.codystage.service.UserService;
import com.cody.codystage.utils.CodyBeanUtils;
import com.cody.codystage.utils.JwtTokenUtil;
import com.cody.codystage.utils.RegexUtil;
import com.cody.codystage.utils.RequestUtil;
import com.google.common.collect.Maps;
import io.swagger.annotations.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.*;

/**
 * @Classname UserController
 * @Description TODO
 * @Date 2019/4/14 19:56
 * @Created by ZQ
 */
@RestController
@RequestMapping("/api/user")
@Api(tags = "用户API")
public class UserController extends BaseApiService<Object> {

    @Autowired
    UserService userService;

    @Resource
    private CommonService commonService;


    @PostMapping("/register/tradition")
    @ApiOperation(value = "传统用户注册")
    public BaseResponse<Object> addUserTradition(@RequestBody @Valid UserInputDTO userInputDTO, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) {
        HashMap<Object, Object> resMap = Maps.newHashMap();
        if (bindingResult.hasErrors()) {
            throw new ServiceException(ResConstants.HTTP_RES_CODE_1206, ResConstants.HTTP_RES_CODE_1206_VALUE);
        }
        User user = CodyBeanUtils.beanCopyPropertoes(userInputDTO, User.class);
        String token = userService.userRegister(user, response);
        UserOutDTO userOutDTO = CodyBeanUtils.beanCopyPropertoes(user, UserOutDTO.class);
        resMap.put("token", token);
        resMap.put("userInfo", userOutDTO);
        return setResult(ResConstants.HTTP_RES_CODE_200, "注册成功", resMap);
    }

    @PostMapping("/register/code")
    @ApiOperation(value = "手机验证码用户注册")
    public BaseResponse<Object> addUserCode(@RequestBody @Valid UserRegisterCodeInDTO userInputDTO, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> resMap;
        if (bindingResult.hasErrors()) {
            throw new ServiceException(ResConstants.HTTP_RES_CODE_1206, ResConstants.HTTP_RES_CODE_1206_VALUE);
        }

        resMap = userService.userRegisterByCode(userInputDTO);
        return setResult(ResConstants.HTTP_RES_CODE_200, "注册成功", resMap);
    }

    @GetMapping(value = "/code", params = "mobile")
    @ApiOperation(value = "获取验证码")
    public BaseResponse<Object> getCode(HttpServletRequest request, HttpServletResponse response) {
        String mobile = RequestUtil.getString(request, "mobile", "");
        if (!RegexUtil.checkMobile(mobile)) {
            return setResultError(ResConstants.HTTP_RES_CODE_1224, ResConstants.HTTP_RES_CODE_1224_VALUE);
        }
        String res = userService.getCode(mobile);
        return setResult(ResConstants.HTTP_RES_CODE_200, "发送成功", res);

    }


    @GetMapping(value = "/check/username", params = "username")
    @ApiOperation(value = "用户名重复检测", notes = "返回0代表无重复")
    public BaseResponse<Object> checkUsername(HttpServletRequest request, HttpServletResponse response) {

        String username = RequestUtil.getString(request, "username", "");
        Boolean isRepeat = userService.checkUsernameRepeat(username);
        if (isRepeat) {
            return setResultError(ResConstants.HTTP_RES_CODE_1207, ResConstants.HTTP_RES_CODE_1207_VALUE);
        } else {
            return setResultError(ResConstants.HTTP_RES_CODE_1208, ResConstants.HTTP_RES_CODE_1208_VALUE);
        }
    }

    @GetMapping(value = "/check/mobile", params = "mobile")
    @ApiOperation(value = "手机号重复检测", notes = "返回0代表无重复")
    public BaseResponse<Object> checkMobile(HttpServletRequest request, HttpServletResponse response) {
        String mobile = RequestUtil.getString(request, "mobile", "");
        if (!RegexUtil.checkMobile(mobile)) {
            return setResultError(ResConstants.HTTP_RES_CODE_1224, ResConstants.HTTP_RES_CODE_1224_VALUE);
        }
        Boolean isRepeat = userService.checkMobileRepeat(mobile);
        if (isRepeat) {
            return setResultError(ResConstants.HTTP_RES_CODE_1222, ResConstants.HTTP_RES_CODE_1222_VALUE);
        } else {
            return setResultError(ResConstants.HTTP_RES_CODE_1223, ResConstants.HTTP_RES_CODE_1223_VALUE);
        }
    }


    @GetMapping(value = "/userInfo/username", params = "username")
    @ApiOperation(value = "根据用户名查询用户信息")
    public UserOutDTO getUserInfoByName(HttpServletRequest request, HttpServletResponse response) {
        if (request.getParameter("username").isEmpty()) {
            throw new ServiceException(ResConstants.HTTP_RES_CODE_1206, ResConstants.HTTP_RES_CODE_1206_VALUE);
        }
        String username = RequestUtil.getString(request, "username", "");
        User userInfo = userService.getUserInfo(username);
        if (Objects.isNull(userInfo)) {
            throw new ServiceException(ResConstants.HTTP_RES_CODE_1202, ResConstants.HTTP_RES_CODE_1202_VALUE);
        }
        return CodyBeanUtils.beanCopyPropertoes(userInfo, UserOutDTO.class);
    }

    @GetMapping(value = "/userInfo/id", params = "id")
    @ApiOperation(value = "根据用户ID查询用户信息")
    public BaseResponse<Object> getUserInfoById(HttpServletRequest request) {
        if (request.getParameter("id").isEmpty()) {
            throw new ServiceException(ResConstants.HTTP_RES_CODE_401, ResConstants.HTTP_RES_CODE_401_VALUE);
        }

        Long id = RequestUtil.getLong(request, "id", 0L);
        User userInfo = userService.getUserInfo(id);

        if (Objects.isNull(userInfo)) {
            throw new ServiceException(ResConstants.HTTP_RES_CODE_1202, ResConstants.HTTP_RES_CODE_1202_VALUE);
        }
        UserOutDTO userOutDTO = CodyBeanUtils.beanCopyPropertoes(userInfo, UserOutDTO.class);

        return setResult(ResConstants.HTTP_RES_CODE_200, ResConstants.HTTP_RES_CODE_200_VALUE, userOutDTO);
    }

    @PostMapping(value = "/userInfo/list")
    @ApiOperation(value = "用户id的列表获取用户信息")
    public BaseResponse<Object> getUserInfoByIdList(@RequestBody @Valid UserIdListInDto userIdListInDto,BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) {

        commonService.checkDto(bindingResult);

        List<UserOutDTO> userInfoByList = userService.getUserInfoByList(userIdListInDto);

        return setResult(ResConstants.HTTP_RES_CODE_200, ResConstants.HTTP_RES_CODE_200_VALUE, userInfoByList);
    }


    @PutMapping(value = "/update")
    @ApiOperation(value = "用户更改用户信息 (Token yes)")
    public BaseResponse<Object> updateUserInfoByName(@RequestBody @Valid UserUpdateDTO inputDTO, BindingResult bindingResult, HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            throw new ServiceException(ResConstants.HTTP_RES_CODE_1206, ResConstants.HTTP_RES_CODE_1206_VALUE);
        }

        Long userId = JwtTokenUtil.getUserId(request);
        if (inputDTO.getUsername().equals(userService.getUserInfo(userId).getUsername()) || userService.checkUsernameRepeat(inputDTO.getUsername())) {
            User userInfo = userService.updateUserInfo(userId, inputDTO);
            return setResult(ResConstants.HTTP_RES_CODE_200, "修改信息成功", CodyBeanUtils.beanCopyPropertoes(userInfo, UserOutDTO.class));
        } else {
            return setResultError(ResConstants.HTTP_RES_CODE_1208, ResConstants.HTTP_RES_CODE_1208_VALUE);
        }

    }

    @PostMapping(value = "/login")
    @ApiOperation(value = "客户端登录")
    public BaseResponse<Object> login(@RequestBody @Valid UserLoginDTO inputDTO, BindingResult bindingResult, HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            throw new ServiceException(ResConstants.HTTP_RES_CODE_1206, ResConstants.HTTP_RES_CODE_1206_VALUE);
        }

        Map<String, Object> resMap = userService.login(inputDTO);
        return setResult(ResConstants.HTTP_RES_CODE_200, "登录成功", resMap);

    }

    @PostMapping(value = "/login/code")
    @ApiOperation(value = "客户端登录")
    public BaseResponse<Object> login(@RequestBody @Valid UserLoginCodeInDTO inputDTO, BindingResult bindingResult, HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            throw new ServiceException(ResConstants.HTTP_RES_CODE_1206, ResConstants.HTTP_RES_CODE_1206_VALUE);
        }
        Map<String, Object> resMap = userService.loginByCode(inputDTO);
        return setResult(ResConstants.HTTP_RES_CODE_200, "登录成功", resMap);
    }

    @PutMapping(value = "/AterPassword")
    @ApiOperation(value = "修改用户密码")
    public BaseResponse<Object> alterPassword(@RequestBody @Valid UserAlterDTO userAlterDTO, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            throw new ServiceException(ResConstants.HTTP_RES_CODE_1206, ResConstants.HTTP_RES_CODE_1206_VALUE);
        }

        Boolean res = userService.alterPassword(userAlterDTO);
        if (res) {
            return setResultSuccess("修改密码成功");
        } else {
            return setResultError(ResConstants.HTTP_RES_CODE_1210, ResConstants.HTTP_RES_CODE_1210_VALUE);
        }

    }

    @PutMapping(value = "/update/mobile")
    @ApiOperation(value = "用户更换手机号 (Token yes)")
    public BaseResponse<Object> alterMobile(@RequestBody @Valid UserLoginCodeInDTO userInputDTO, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) {

        if (bindingResult.hasErrors()) {
            throw new ServiceException(ResConstants.HTTP_RES_CODE_1206, ResConstants.HTTP_RES_CODE_1206_VALUE);
        }
        Long userId = JwtTokenUtil.getUserId(request);
        userService.updateMobile(userInputDTO, userId);
        return setResultSuccess("更换手机号成功");
    }

}
