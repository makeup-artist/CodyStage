package com.cody.codystage.bean.dto.in;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @Classname UserRegisterCodeInDTO
 * @Description TODO
 * @Date 2019/5/25 10:21
 * @Created by ZQ
 */
@Data
@ApiModel(value = "用户手机注册参数")
public class UserRegisterCodeInDTO {

    @ApiModelProperty(value = "用户名(长度1-20)",required=true )
    @NotNull(message = "用户名不能为空")
    @Size(min = 1,max = 20,message = "用户名长度不符合要求" )
    private String username;

    @ApiModelProperty(value = "密码(长度>6)",required=true)
    @NotNull(message = "密码不能为空")
    @Size(min = 6,message = "密码长度不符合要求" )
    private String password;

    @ApiModelProperty(value = "手机号码")
    @Pattern(regexp = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$"
            , message = "手机号格式错误")
    private String mobile;

    @ApiModelProperty(value = "验证码")
    @NotNull(message = "验证码不能为空")
    @Size(min =4, max = 4, message = "验证码长度不符合要求")
    private String code;
}
