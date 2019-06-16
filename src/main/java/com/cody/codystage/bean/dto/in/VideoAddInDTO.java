package com.cody.codystage.bean.dto.in;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @Classname VideoAddInDTO
 * @Description TODO
 * @Date 2019/5/19 15:00
 * @Created by ZQ
 */
@Data
@ApiModel(value = "上传视频参数")
public class VideoAddInDTO {

    @ApiModelProperty(value = "视频url",required=true)
    @NotNull(message = "视频地址不能为空")
    private String url;


    @ApiModelProperty(value = "标题 长度<60",required=true)
    @Size(max = 60,message = "用标题长度不符合要求" )
    @NotNull(message = "标题不能为空")
    private String title;


    @ApiModelProperty(value = "封面url",required=true)
    @NotNull(message = "封面地址不能为空")
    private String cover;
}
