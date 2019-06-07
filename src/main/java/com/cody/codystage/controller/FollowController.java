package com.cody.codystage.controller;

import com.cody.codystage.common.base.BaseApiService;
import com.cody.codystage.common.base.BaseResponse;
import com.cody.codystage.common.constants.ResConstants;
import com.cody.codystage.service.CommonService;
import com.cody.codystage.service.FollowService;
import com.cody.codystage.utils.JwtTokenUtil;
import com.cody.codystage.utils.RequestUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @Classname AttentionController
 * @Description TODO
 * @Date 2019/6/2 21:26
 * @Created by ZQ
 */
@RestController
@RequestMapping("/api/follow/")
@Api(tags = "关注API")
public class FollowController extends BaseApiService<Object> {

    @Resource
    private FollowService followService;

    @Resource
    private CommonService commonService;

    @GetMapping(value = "/num", params = "id")
    @ApiOperation(value = "获取用户关注和被关注的数")
    public BaseResponse<Object> getUserFollowNum(HttpServletRequest request, HttpServletResponse response) {
        commonService.checkId(request);

        Long id = RequestUtil.getLong(request, "id", 0L);
        Map<String, Object> userFollowNum = followService.getUserFollowNum(id);

        return setResult(ResConstants.HTTP_RES_CODE_200, ResConstants.HTTP_RES_CODE_200_VALUE, userFollowNum);
    }

    @GetMapping(value = "/follow", params = "id")
    @ApiOperation(value = "关注用户 (token yes)")
    public BaseResponse<Object> follow(HttpServletRequest request, HttpServletResponse response) {
        commonService.checkId(request);

        Long userId = JwtTokenUtil.getUserId(request);
        Long followId = RequestUtil.getLong(request, "id", 0L);

        if (userId.equals(followId)) {
            return setResult(ResConstants.HTTP_RES_CODE_1232, ResConstants.HTTP_RES_CODE_1232_VALUE);
        }

        if (followService.isFollow(userId, followId)) {
            return setResult(ResConstants.HTTP_RES_CODE_1231, ResConstants.HTTP_RES_CODE_1231_VALUE);
        }

        followService.follow(userId, followId);
        return setResult(ResConstants.HTTP_RES_CODE_200, ResConstants.HTTP_RES_CODE_200_VALUE);
    }


    @GetMapping(value = "/followOrNot", params = "id")
    @ApiOperation(value = "是否关注用户 (token yes)")
    public BaseResponse<Object> followOrNot(HttpServletRequest request, HttpServletResponse response) {
        commonService.checkId(request);
        Long userId = JwtTokenUtil.getUserId(request);
        Long followId = RequestUtil.getLong(request, "id", 0L);

        if (userId.equals(followId)) {
            return setResult(ResConstants.HTTP_RES_CODE_1232, ResConstants.HTTP_RES_CODE_1232_VALUE);
        }
        if (followService.isFollow(userId, followId)) {
            return setResult(ResConstants.HTTP_RES_CODE_1231, ResConstants.HTTP_RES_CODE_1231_VALUE);
        } else {
            return setResult(ResConstants.HTTP_RES_CODE_1233, ResConstants.HTTP_RES_CODE_1233_VALUE);
        }
    }

    @GetMapping(value = "/following", params = {"page","limit"})
    @ApiOperation(value = "关注列表 (token yes)")
    public BaseResponse<Object> getFollowing(HttpServletRequest request, HttpServletResponse response) {
        commonService.checkPageAndLimit(request);
        Long userId = JwtTokenUtil.getUserId(request);
        int page = RequestUtil.getInt(request, "page", 1);
        int limit = RequestUtil.getInt(request, "limit", 10);
        List<Long> idList=followService.getFollowing(userId,page,limit);
        return  setResult(ResConstants.HTTP_RES_CODE_200, ResConstants.HTTP_RES_CODE_200_VALUE,idList);
    }

    @GetMapping(value = "/followed", params = {"page","limit"})
    @ApiOperation(value = "被关注列表 (token yes)")
    public BaseResponse<Object> getFollowed(HttpServletRequest request, HttpServletResponse response) {
        commonService.checkPageAndLimit(request);
        Long userId = JwtTokenUtil.getUserId(request);
        int page = RequestUtil.getInt(request, "page", 1);
        int limit = RequestUtil.getInt(request, "limit", 10);

        List<Long> idList=followService.getFollowed(userId,page,limit);
        return  setResult(ResConstants.HTTP_RES_CODE_200, ResConstants.HTTP_RES_CODE_200_VALUE,idList);
    }

    @DeleteMapping(value = "/delete", params = {"id"})
    @ApiOperation(value = "取消关注 (token yes)")
    public BaseResponse<Object> deleteFollow(HttpServletRequest request, HttpServletResponse response) {
        commonService.checkId(request);
        Long userId = JwtTokenUtil.getUserId(request);
        Long followId = RequestUtil.getLong(request, "id", 0L);

        if (!followService.isFollow(userId, followId)) {
            return setResult(ResConstants.HTTP_RES_CODE_1233, ResConstants.HTTP_RES_CODE_1233_VALUE);
        }else {
            followService.deleteFollow(userId,followId);
        }
        return  setResult(ResConstants.HTTP_RES_CODE_200, ResConstants.HTTP_RES_CODE_200_VALUE);
    }

}
