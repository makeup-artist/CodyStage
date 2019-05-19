package com.cody.codystage.bean.dto.in;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @Classname PostUpdateInDTO
 * @Description TODO
 * @Date 2019/5/19 9:05
 * @Created by ZQ
 */
@Data
@ApiModel(value = "更新帖子参数")
public class PostUpdateInDTO {

    @ApiModelProperty(value = "帖子id",required=true)
    private int id;

    @ApiModelProperty(value = "标题 长度<60",required=true)
    @Size(max = 60,message = "用标题长度不符合要求" )
    @NotNull(message = "标题不能为空")
    private String title;

    @ApiModelProperty(value = "文章内容",required=true)
    @NotNull(message = "文章内容不能为空")
    private String content;
}
