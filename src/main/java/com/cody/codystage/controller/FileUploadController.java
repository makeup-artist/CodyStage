package com.cody.codystage.controller;

import com.cody.codystage.common.base.BaseApiService;
import com.cody.codystage.common.base.BaseResponse;
import com.cody.codystage.common.constants.CodyConstants;
import com.cody.codystage.service.FileUploadService;
import com.cody.codystage.utils.RequestUtil;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Classname FileUploadContorller
 * @Description TODO
 * @Date 2019/5/11 16:22
 * @Created by ZQ
 */
@RestController
@RequestMapping("/api/upload/")
@Api(tags = "上传文件")
public class FileUploadController extends BaseApiService<Object> {

    @Resource
    FileUploadService fileUploadService;


    @PostMapping(value = "/image",consumes = "multipart/*",headers = "Content-Type=multipart/form-data")
    @ApiOperation(value = "图片")
    public BaseResponse<Object> uploadImage(@ApiParam(value = "上传的文件",required = true) MultipartFile multipartFile, HttpServletRequest request){
        Map<String,Object> resMap= Maps.newHashMap();
        String url = fileUploadService.uploadFile(CodyConstants.IMAGE_BUCKETNAME, multipartFile);
        resMap.put("url",url);
        return setResultSuccess(resMap);
    }

    @PostMapping(value = "/video",consumes = "multipart/*",headers = "Content-Type=multipart/form-data")
    @ApiOperation(value = "视频" )
    public BaseResponse<Object> uploadVideo(@ApiParam(value = "上传的文件",required = true) MultipartFile multipartFile, HttpServletRequest request){
        Map<String,Object> resMap= Maps.newHashMap();
        String url = fileUploadService.uploadFile(CodyConstants.VIDEO_BUCKETNAME, multipartFile);
        resMap.put("url",url);
        return setResultSuccess(resMap);
    }
}
