package com.cody.codystage.controller;

import com.cody.codystage.common.base.BaseApiService;
import com.cody.codystage.service.VideoService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
}
