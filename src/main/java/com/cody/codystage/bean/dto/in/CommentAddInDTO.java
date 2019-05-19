package com.cody.codystage.bean.dto.in;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Classname CommentAddInDTO
 * @Description TODO
 * @Date 2019/5/19 19:48
 * @Created by ZQ
 */

@Data
@ApiModel(value = "增加评论参数")
public class CommentAddInDTO {

    @ApiModelProperty(value = "视频或帖子的id", required = true)
    @NotNull
    private Integer belong;

    @ApiModelProperty(value = "评论的类型，1为帖子，2为视频", required = true)
    @NotNull
    private Integer type;

    @ApiModelProperty(value = "评论内容", required = true)
    @NotNull
    private String content;

    @ApiModelProperty(value = "评论的层级，1位对帖子或视频的评论，2位对其他评论的评论", required = true)
    @NotNull
    private Integer grade;

    @ApiModelProperty(value = "若评级为2，则为评论的id,否则为0", required = true)
    @NotNull
    private Integer to_comment_id;
}
