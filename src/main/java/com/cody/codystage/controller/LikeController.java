package com.cody.codystage.controller;

import com.cody.codystage.bean.po.Like;
import com.cody.codystage.common.base.BaseApiService;
import com.cody.codystage.common.base.BaseResponse;
import com.cody.codystage.common.constants.ResConstants;
import com.cody.codystage.service.CommonService;
import com.cody.codystage.service.LikeService;
import com.cody.codystage.service.PostService;
import com.cody.codystage.service.VideoService;
import com.cody.codystage.utils.JwtTokenUtil;
import com.cody.codystage.utils.RequestUtil;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Classname LikeController
 * @Description TODO
 * @Date 2019/5/19 22:22
 * @Created by ZQ
 */
@RestController
@RequestMapping("/api/like")
@Api(tags = "点赞API")
public class LikeController extends BaseApiService<Object> {

    @Resource
    private CommonService commonService;

    @Resource
    private LikeService likeService;

    @Resource
    private PostService postService;

    @Resource
    private VideoService videoService;

    @GetMapping(value = "/add", params = {"type", "id"})
    @ApiOperation(value = "用户点赞 type为帖子(1)或视频(2),id为帖子或视频的id (token yes)")
    public BaseResponse<Object> addLike(HttpServletRequest request, HttpServletResponse response) {
        commonService.checkIdAndType(request);

        int id = RequestUtil.getInt(request, "id", 0);
        int type = RequestUtil.getInt(request, "type", 0);
        Like like = Like.builder().author(JwtTokenUtil.getUserId(request)).belong(id).type(type).build();
        if (like.getType() == 1) {
            if (Objects.isNull(postService.selectPost(like.getBelong()))) {
                return setResult(ResConstants.HTTP_RES_CODE_1220, ResConstants.HTTP_RES_CODE_1221_VALUE);
            }
        } else {
            if (Objects.isNull(videoService.selectVideo(like.getBelong()))) {
                return setResult(ResConstants.HTTP_RES_CODE_1221, ResConstants.HTTP_RES_CODE_1221_VALUE);
            }
        }

        if (!Objects.isNull(likeService.selectLike(like))) {
            return setResult(ResConstants.HTTP_RES_CODE_1217, ResConstants.HTTP_RES_CODE_1217_VALUE);
        }

        likeService.addLike(like);
        if (like.getType() == 1) {
            postService.addLike(like.getBelong());
        } else {
            videoService.addLike(like.getBelong());
        }

        return setResult(ResConstants.HTTP_RES_CODE_200, ResConstants.HTTP_RES_CODE_200_VALUE_1);

    }

    @GetMapping(value = "/delete", params = {"type", "id"})
    @ApiOperation(value = "用户取消点赞 type为帖子(1)或视频(2),id为帖子或视频的id (token yes)")
    public BaseResponse<Object> deleteLike(HttpServletRequest request, HttpServletResponse response) {
        commonService.checkIdAndType(request);

        int id = RequestUtil.getInt(request, "id", 0);
        int type = RequestUtil.getInt(request, "type", 0);
        Like like = Like.builder().author(JwtTokenUtil.getUserId(request)).belong(id).type(type).build();
        if (Objects.isNull(likeService.selectLike(like))) {
            return setResult(ResConstants.HTTP_RES_CODE_1217, ResConstants.HTTP_RES_CODE_1217_VALUE);
        }

        likeService.deleteLike(like);
        if (like.getType() == 1) {
            postService.deleteLike(like.getBelong());
        } else {
            videoService.deleteLike(like.getBelong());
        }

        return setResult(ResConstants.HTTP_RES_CODE_200, ResConstants.HTTP_RES_CODE_200_VALUE_1);
    }

    @DeleteMapping(value = "/isLike", params = {"type", "id"})
    @ApiOperation(value = "用户是否点过赞 type为帖子(1)或视频(2),id为帖子或视频的id (token yes)")
    public BaseResponse<Object> isLike(HttpServletRequest request, HttpServletResponse response) {
        commonService.checkIdAndType(request);

        int id = RequestUtil.getInt(request, "id", 0);
        int type = RequestUtil.getInt(request, "type", 0);
        Like like = Like.builder().author(JwtTokenUtil.getUserId(request)).belong(id).type(type).build();
        if (Objects.isNull(likeService.selectLike(like))) {
            return setResult(ResConstants.HTTP_RES_CODE_1218, ResConstants.HTTP_RES_CODE_1218_VALUE);
        } else {
            return setResult(ResConstants.HTTP_RES_CODE_1219, ResConstants.HTTP_RES_CODE_1219_VALUE);
        }
    }

    @GetMapping(value = "/likeList")
    @ApiOperation(value = "用户点赞列表 (token yes)")
    public BaseResponse<Object> likeList(HttpServletRequest request, HttpServletResponse response) {
        Long userId = JwtTokenUtil.getUserId(request);
        Map<String,Object> resMap = likeService.likeList(userId);

        return setResultSuccess(resMap);
    }
}
