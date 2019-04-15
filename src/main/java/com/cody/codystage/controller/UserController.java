package com.cody.codystage.controller;

import com.alibaba.fastjson.JSONObject;
import com.cody.codystage.common.base.BaseApiService;
import com.cody.codystage.common.base.BaseResponse;
import com.cody.codystage.dto.input.UserInputDTO;
import com.cody.codystage.entity.User;
import com.cody.codystage.service.UserService;
import com.cody.codystage.utils.CodyBeanUtils;
import com.cody.codystage.utils.MD5Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

/**
 * @Classname UserController
 * @Description TODO
 * @Date 2019/4/14 19:56
 * @Created by ZQ
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户API")
public class UserController extends BaseApiService<JSONObject> {
    @Autowired
    UserService userService;

    @PostMapping("/register")
    @ApiOperation(value = "用户注册")
    public BaseResponse<JSONObject> addUser(@Valid UserInputDTO userInputDTO, BindingResult bindingResult){
        User user= CodyBeanUtils.beanCopyPropertoes(userInputDTO,User.class);
        Integer res = userService.userRegister(user);
        return res>0?setResultSuccess("注册成功"):setResultError("注册失败");
    }

}
