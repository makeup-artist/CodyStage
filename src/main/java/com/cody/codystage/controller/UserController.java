package com.cody.codystage.controller;

import com.alibaba.fastjson.JSONObject;
import com.cody.codystage.common.base.BaseApiService;
import com.cody.codystage.common.base.BaseResponse;
import com.cody.codystage.dto.input.UserInputDTO;
import com.cody.codystage.entity.User;
import com.cody.codystage.exception.ServiceException;
import com.cody.codystage.service.UserService;
import com.cody.codystage.utils.CodyBeanUtils;
import com.cody.codystage.utils.RequestUtil;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    public BaseResponse<JSONObject> addUser(@Valid UserInputDTO userInputDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ServiceException("401", "参数校验失败");
        }
        User user = CodyBeanUtils.beanCopyPropertoes(userInputDTO, User.class);
        Integer res = userService.userRegister(user);
        return res > 0 ? setResultSuccess("注册成功") : setResultError("注册失败");
    }

    @GetMapping("/register/check")
    @ApiOperation(value = "用户名重复检测")
    public Map<String, Object> UpdateUser(HttpServletRequest request) {
        Map<String, Object> resMap = Maps.newHashMap();
        String username = RequestUtil.getString(request, "username", "");
        if (StringUtils.isEmpty(username)) {
            resMap.put("repeat", true);
            return resMap;
        }
        Boolean isRepeat = userService.checkUsernameRepeat(username);
        resMap.put("repeat", isRepeat);
        return resMap;
    }

}
