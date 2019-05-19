package com.cody.codystage.bean.dto.in;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @Classname UserAlterDTO
 * @Description TODO
 * @Date 2019/5/2 15:20
 * @Created by ZQ
 */
@Data
@ApiModel(value = "用户修改密码参数")
public class UserAlterDTO {
    @ApiModelProperty(value = "用户名 长度1-20",required=true)
    @Size(min = 1,max = 20,message = "用户名长度不符合要求" )
    @NotNull(message = "用户名不能为空")
    private String username;


    @ApiModelProperty(value = "旧密码 长度>6",required=true)
    @NotNull(message = "密码不能为空")
    @Size(min = 6,message = "密码长度不符合要求" )
    private String oldPassword;

    @ApiModelProperty(value = "新密码 长度>6",required=true)
    @NotNull(message = "密码不能为空")
    @Size(min = 6,message = "密码长度不符合要求" )
    private String newPassword;
}
