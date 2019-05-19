package com.cody.codystage.bean.dto.in;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @Classname PostAddInDTO
 * @Description TODO
 * @Date 2019/5/13 22:55
 * @Created by ZQ
 */
@Data
@ApiModel(value = "上传帖子参数")
public class PostAddInDTO {
    @ApiModelProperty(value = "标题 长度<60",required=true)
    @Size(max = 60,message = "用标题长度不符合要求" )
    @NotNull(message = "标题不能为空")
    private String title;

    @ApiModelProperty(value = "文章内容",required=true)
    @NotNull(message = "文章内容不能为空")
    private String content;
}
