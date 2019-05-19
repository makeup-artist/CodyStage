package com.cody.codystage.bean.dto.in;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @Classname UserInputDTO
 * @Description TODO
 * @Date 2019/4/14 18:09
 * @Created by ZQ
 */
@Data
@ApiModel(value = "用户信息实体类")
public class UserInputDTO {

    @ApiModelProperty(value = "用户名(长度1-20)",required=true )
    @NotNull(message = "用户名不能为空")
    @Size(min = 1,max = 20,message = "用户名长度不符合要求" )
    private String username;

    @ApiModelProperty(value = "密码(长度>6)",required=true)
    @NotNull(message = "密码不能为空")
    @Size(min = 6,message = "密码长度不符合要求" )
    private String password;

    @Range(min = 0, max = 1, message = "性别为0或1")
    @ApiModelProperty(value = "性别", notes = "0为男，1为女")
    private Integer gender;

    @Range(min = 0, max = 120, message = "年龄非法")
    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "手机号码")
    @Pattern(regexp = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$"
            , message = "手机号格式错误")
    private String mobile;

    @ApiModelProperty(value = "昵称")
    @Size(max = 20,message = "昵称过长" )
    private String nickname;

    @ApiModelProperty(value = "头像地址")
    private String picture;

    @ApiModelProperty(value = "个人描述")
    @Size(max = 255,message = "个人描述过长" )
    private String description;

    @ApiModelProperty(value = "用户自己的标签")
    @Size(max = 20,message = "标签过长" )
    private String tag;

    @ApiModelProperty(value = "邮箱")
    @Email(message = "错误的邮箱格式")
    private String email;

}
