package com.cody.codystage.controller;


import com.cody.codystage.bean.dto.in.PostInfoListInDTO;
import com.cody.codystage.bean.dto.in.VideoAddInDTO;
import com.cody.codystage.bean.dto.in.VideoInfoListInDTO;
import com.cody.codystage.bean.dto.in.VideoUpdateInDTO;
import com.cody.codystage.bean.po.Post;
import com.cody.codystage.bean.po.Video;
import com.cody.codystage.common.base.BaseApiService;
import com.cody.codystage.common.base.BaseResponse;
import com.cody.codystage.common.constants.ResConstants;
import com.cody.codystage.service.CommonService;
import com.cody.codystage.service.VideoService;
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
 * @Classname VideoController
 * @Description TODO
 * @Date 2019/5/12 21:40
 * @Created by ZQ
 */
@RestController
@RequestMapping("/api/video")
@Api(tags = "视频API")
public class VideoController extends BaseApiService<Object> {

    @Resource
    private VideoService videoService;

    @Resource
    private CommonService commonService;


    @PostMapping("/add")
    @ApiOperation(value = "上传视频 (token yes)")
    public BaseResponse<Object> addPost(@Valid VideoAddInDTO videoAddInDTO, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response){
        HashMap<Object, Object> resMap = Maps.newHashMap();

        commonService.checkDto(bindingResult);

        Video video = CodyBeanUtils.beanCopyPropertoes(videoAddInDTO, Video.class);
        video.setAuthor(JwtTokenUtil.getUserId(request));
        video.setStar(0);

        Integer res = videoService.addVideo(video);
        if(res>0){
            resMap.put("videoId",video.getId());
            return setResult(ResConstants.HTTP_RES_CODE_200, ResConstants.HTTP_RES_CODE_200_VALUE_1, resMap);
        }else {
            return setResult(ResConstants.HTTP_RES_CODE_1216, ResConstants.HTTP_RES_CODE_1216_VALUE);
        }
    }

    @PutMapping("/update")
    @ApiOperation(value = "修改视频 (token yes)")
    public BaseResponse<Object> updatePost(@Valid VideoUpdateInDTO videoUpdateInDTO, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response){
        commonService.checkDto(bindingResult);

        Video video = CodyBeanUtils.beanCopyPropertoes(videoUpdateInDTO, Video.class);
        video.setAuthor(JwtTokenUtil.getUserId(request));
        Integer res=videoService.updateVideo(video);
        if(res>0){
            return setResult(ResConstants.HTTP_RES_CODE_200, ResConstants.HTTP_RES_CODE_200_VALUE_1);
        }else {
            return setResult(ResConstants.HTTP_RES_CODE_1214, ResConstants.HTTP_RES_CODE_1214_VALUE);
        }
    }


    @GetMapping(value ="/select" , params = "id")
    @ApiOperation(value = "根据id查询视频")
    public BaseResponse<Object> selectPost (HttpServletRequest request, HttpServletResponse response){
        commonService.checkId(request);

        int id = RequestUtil.getInt(request, "id",0);
        Map<String,Object> resMap=videoService.selectVideo(id);

        return setResult(ResConstants.HTTP_RES_CODE_200, ResConstants.HTTP_RES_CODE_200_VALUE_1, resMap);
    }

    @DeleteMapping(value ="/delete",params = "id")
    @ApiOperation(value = "根据id删除视频 (token yes)")
    public BaseResponse<Object> deletePost(HttpServletRequest request, HttpServletResponse response){
        commonService.checkId(request);

        int id = RequestUtil.getInt(request, "id",0);
        Long userId = JwtTokenUtil.getUserId(request);
        Integer res=videoService.deleteVideo(id,userId);

        if(res>0){
            return setResult(ResConstants.HTTP_RES_CODE_200, ResConstants.HTTP_RES_CODE_200_VALUE_1);
        }else {
            return setResult(ResConstants.HTTP_RES_CODE_1214, ResConstants.HTTP_RES_CODE_1214_VALUE);
        }
    }

    @GetMapping(value = "/getPost",params = {"page","limit"})
    @ApiOperation(value = "获取视频推荐")
    public BaseResponse<Object> getPost(HttpServletRequest request, HttpServletResponse response){
        commonService.checkPageAndLimit(request);

        int page=RequestUtil.getInt(request,"page",1);
        int limit=RequestUtil.getInt(request,"limit",10);

        List<Map<String,Object>> res=videoService.getVideo((page-1)*limit,limit);
        return setResult(ResConstants.HTTP_RES_CODE_200, ResConstants.HTTP_RES_CODE_200_VALUE_1,res);
    }

    @GetMapping(value = "/getPost/mine",params = {"page","limit"})
    @ApiOperation(value = "获取用户发的视频 (token yes)")
    public BaseResponse<Object> getPostByUserId(HttpServletRequest request, HttpServletResponse response){
        commonService.checkPageAndLimit(request);

        int page=RequestUtil.getInt(request,"page",1);
        int limit=RequestUtil.getInt(request,"limit",10);
        Long userId = JwtTokenUtil.getUserId(request);

        List<Map<String,Object>> res=videoService.getVideoByUserId((page-1)*limit,limit,userId);
        return setResult(ResConstants.HTTP_RES_CODE_200, ResConstants.HTTP_RES_CODE_200_VALUE_1,res);
    }

    @GetMapping(value = "/search",params = {"condition","page","limit"})
    @ApiOperation(value = "模糊搜索视频，根据视频标题")
    public BaseResponse<Object> searchPost(HttpServletRequest request, HttpServletResponse response){
        commonService.checkCondition(request);

        String condition = request.getParameter("condition");
        int page=RequestUtil.getInt(request,"page",1);
        int limit=RequestUtil.getInt(request,"limit",10);

        List<Map<String,Object>> res=videoService.searchVideo(condition,page,limit);
        return setResult(ResConstants.HTTP_RES_CODE_200, ResConstants.HTTP_RES_CODE_200_VALUE_1,res);
    }

    @PostMapping(value = "/getByList")
    @ApiOperation(value = "通过视频id的列表得到视频的信息")
    public BaseResponse<Object> getVideoByList(@Valid VideoInfoListInDTO videoInfoListInDTO, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response){
        commonService.checkDto(bindingResult);

        List<Video> videos = videoService.selectVideoByList(videoInfoListInDTO.getVideoList());
        return setResultSuccess(videos);

    }

}
