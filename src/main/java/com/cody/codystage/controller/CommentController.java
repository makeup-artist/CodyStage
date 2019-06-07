package com.cody.codystage.controller;

import com.cody.codystage.bean.dto.in.CommentAddInDTO;
import com.cody.codystage.bean.po.Comment;
import com.cody.codystage.common.base.BaseApiService;
import com.cody.codystage.common.base.BaseResponse;
import com.cody.codystage.common.constants.ResConstants;
import com.cody.codystage.service.CommentService;
import com.cody.codystage.service.CommonService;
import com.cody.codystage.utils.CodyBeanUtils;
import com.cody.codystage.utils.JwtTokenUtil;
import com.cody.codystage.utils.RequestUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

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
    private CommonService commonService;

    @Resource
    private CommentService commentService;

    @PostMapping("/add")
    @ApiOperation(value = "添加评论 (token yes)")
    public BaseResponse<Object> addComment(@RequestBody @Valid CommentAddInDTO commentAddInDTO, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) {
        commonService.checkDto(bindingResult);

        Comment comment = CodyBeanUtils.beanCopyPropertoes(commentAddInDTO, Comment.class);
        comment.setAuthor(JwtTokenUtil.getUserId(request));
        Integer res = commentService.addComment(comment);

        if (res > 0) {
            return setResult(ResConstants.HTTP_RES_CODE_200, ResConstants.HTTP_RES_CODE_200_VALUE_1);
        } else {
            return setResult(ResConstants.HTTP_RES_CODE_1216, ResConstants.HTTP_RES_CODE_1216_VALUE);
        }
    }

    @DeleteMapping(value = "/delete", params = {"id"})
    @ApiOperation(value = "删除评论 需要评论id (token yes)")
    public BaseResponse<Object> deleteComment(HttpServletRequest request, HttpServletResponse response) {
        commonService.checkId(request);

        int id = RequestUtil.getInt(request, "id", 0);
        Long userId = JwtTokenUtil.getUserId(request);
        Integer res = commentService.deleteComment(id, userId);

        if (res > 0) {
            return setResult(ResConstants.HTTP_RES_CODE_200, ResConstants.HTTP_RES_CODE_200_VALUE_1);
        } else {
            return setResult(ResConstants.HTTP_RES_CODE_1214, ResConstants.HTTP_RES_CODE_1214_VALUE);
        }
    }

    @GetMapping(value = "/get", params = {"belong","type"})
    @ApiOperation(value = "获取评论 需要评论的属祖的类型和id (token yes)")
    public Object getComment(HttpServletRequest request, HttpServletResponse response) {
        commonService.checkBelongAndType(request);

        int belong = RequestUtil.getInt(request, "belong", 0);
        int type = RequestUtil.getInt(request, "type", 0);

        return commentService.getComment(belong, type);
    }
}
