package com.cody.codystage.controller;

import com.cody.codystage.common.base.BaseApiService;
import com.cody.codystage.service.PostService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
}
