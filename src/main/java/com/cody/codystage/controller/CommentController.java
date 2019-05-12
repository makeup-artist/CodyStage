package com.cody.codystage.controller;

import com.cody.codystage.common.base.BaseApiService;
import com.cody.codystage.service.CommentService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Classname CommentController
 * @Description TODO
 * @Date 2019/5/12 21:41
 * @Created by ZQ
 */
@RestController
@RequestMapping("/api/comment")
@Api(tags = "评论API")
public class CommentController extends BaseApiService<Object> {

    @Resource
    private CommentService commentService;
}
