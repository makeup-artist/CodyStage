package com.cody.codystage.controller;

import com.cody.codystage.bean.dto.in.PostAddInDTO;
import com.cody.codystage.bean.dto.in.PostInfoListInDTO;
import com.cody.codystage.bean.dto.in.PostUpdateInDTO;
import com.cody.codystage.bean.po.Post;
import com.cody.codystage.common.base.BaseApiService;
import com.cody.codystage.common.base.BaseResponse;
import com.cody.codystage.common.constants.ResConstants;
import com.cody.codystage.service.CommonService;
import com.cody.codystage.service.PostService;
import com.cody.codystage.utils.CodyBeanUtils;
import com.cody.codystage.utils.JwtTokenUtil;
import com.cody.codystage.utils.RequestUtil;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Classname PostController
 * @Description TODO
 * @Date 2019/5/12 21:39
 * @Created by ZQ
 */
@RestController
@RequestMapping("/api/post")
@Api(tags = "帖子API")
public class PostController extends BaseApiService<Object> {

    @Resource
    private PostService postService;

    @Resource
    private CommonService commonService;

    @PostMapping("/add")
    @ApiOperation(value = "上传帖子 (token yes)")
    public BaseResponse<Object> addPost(@RequestBody @Valid PostAddInDTO postAddInDTO, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) {
        HashMap<Object, Object> resMap = Maps.newHashMap();
        commonService.checkDto(bindingResult);

        Post post = CodyBeanUtils.beanCopyPropertoes(postAddInDTO, Post.class);
        post.setAuthor(JwtTokenUtil.getUserId(request));
        post.setStar(0);
        Integer res = postService.addPost(post);
        if (res > 0) {
            resMap.put("postId", post.getId());
            return setResult(ResConstants.HTTP_RES_CODE_200, ResConstants.HTTP_RES_CODE_200_VALUE_1, resMap);
        } else {
            return setResult(ResConstants.HTTP_RES_CODE_1216, ResConstants.HTTP_RES_CODE_1216_VALUE);
        }

    }

    @PutMapping("/update")
    @ApiOperation(value = "修改帖子 (token yes)")
    public BaseResponse<Object> updatePost(@RequestBody @Valid PostUpdateInDTO postUpdateInDTO, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) {
        commonService.checkDto(bindingResult);

        Post post = CodyBeanUtils.beanCopyPropertoes(postUpdateInDTO, Post.class);
        post.setAuthor(JwtTokenUtil.getUserId(request));
        Integer res = postService.updatePost(post);
        if (res > 0) {
            return setResult(ResConstants.HTTP_RES_CODE_200, ResConstants.HTTP_RES_CODE_200_VALUE_1);
        } else {
            return setResult(ResConstants.HTTP_RES_CODE_1214, ResConstants.HTTP_RES_CODE_1214_VALUE);
        }
    }


    @GetMapping(value = "/select", params = "id")
    @ApiOperation(value = "根据id查询帖子")
    public BaseResponse<Object> selectPost(HttpServletRequest request, HttpServletResponse response) {
        commonService.checkId(request);

        int id = RequestUtil.getInt(request, "id", 0);
        Map<String, Object> resMap = postService.selectPost(id);

        return setResult(ResConstants.HTTP_RES_CODE_200, ResConstants.HTTP_RES_CODE_200_VALUE_1, resMap);
    }

    @DeleteMapping(value = "/delete", params = "id")
    @ApiOperation(value = "根据id删除帖子 (token yes)")
    public BaseResponse<Object> deletePost(HttpServletRequest request, HttpServletResponse response) {
        commonService.checkId(request);

        int id = RequestUtil.getInt(request, "id", 0);
        Long userId = JwtTokenUtil.getUserId(request);
        Integer res = postService.deletePost(id, userId);

        if (res > 0) {
            return setResult(ResConstants.HTTP_RES_CODE_200, ResConstants.HTTP_RES_CODE_200_VALUE_1);
        } else {
            return setResult(ResConstants.HTTP_RES_CODE_1214, ResConstants.HTTP_RES_CODE_1214_VALUE);
        }
    }

    @GetMapping(value = "/getPost", params = {"page", "limit"})
    @ApiOperation(value = "获取首页帖子")
    public BaseResponse<Object> getPost(HttpServletRequest request, HttpServletResponse response) {
        commonService.checkPageAndLimit(request);
        int page = RequestUtil.getInt(request, "page", 1);
        int limit = RequestUtil.getInt(request, "limit", 10);

        List<Map<String, Object>> res = postService.getPost((page - 1) * limit, limit);
        return setResult(ResConstants.HTTP_RES_CODE_200, ResConstants.HTTP_RES_CODE_200_VALUE_1, res);
    }

    @GetMapping(value = "/getPost/mine", params = {"page", "limit"})
    @ApiOperation(value = "获取用户发表的帖子(token yes)")
    public BaseResponse<Object> getPostByUserId(HttpServletRequest request, HttpServletResponse response) {
        commonService.checkPageAndLimit(request);

        int page = RequestUtil.getInt(request, "page", 1);
        int limit = RequestUtil.getInt(request, "limit", 10);
        Long userId = JwtTokenUtil.getUserId(request);

        List<Map<String, Object>> res = postService.getPostByUserId((page - 1) * limit, limit, userId);
        return setResult(ResConstants.HTTP_RES_CODE_200, ResConstants.HTTP_RES_CODE_200_VALUE_1, res);
    }

    @GetMapping(value = "/search", params = {"condition", "page", "limit"})
    @ApiOperation(value = "模糊搜索帖子，根据帖子标题")
    public BaseResponse<Object> searchPost(HttpServletRequest request, HttpServletResponse response) {
        commonService.checkCondition(request);

        String condition = request.getParameter("condition");
        int page = RequestUtil.getInt(request, "page", 1);
        int limit = RequestUtil.getInt(request, "limit", 10);

        List<Map<String, Object>> res = postService.searchPost(condition, page, limit);
        return setResult(ResConstants.HTTP_RES_CODE_200, ResConstants.HTTP_RES_CODE_200_VALUE_1, res);
    }

    @PostMapping(value = "/getByList")
    @ApiOperation(value = "通过帖子id的列表得到帖子的信息")
    public BaseResponse<Object> getPostByList(@RequestBody @Valid PostInfoListInDTO postInfoListInDTO, BindingResult bindingResult,HttpServletRequest request, HttpServletResponse response){
        commonService.checkDto(bindingResult);

        List<Post> posts = postService.selectPostByList(postInfoListInDTO.getPostList());
        return setResultSuccess(posts);

    }

}
