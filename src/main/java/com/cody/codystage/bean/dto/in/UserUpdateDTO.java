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
 * @Classname UserUpdateDTO
 * @Description TODO
 * @Date 2019/5/2 10:21
 * @Created by ZQ
 */
@Data
@ApiModel(value = "用户修改信息DTO", description = "")
public class UserUpdateDTO {
    @ApiModelProperty(value = "用户名(长度1-20)" )
    @NotNull(message = "用户名不能为空")
    @Size(min = 1,max = 20,message = "用户名长度不符合要求" )
    private String username;

    @Range(min = 0, max = 1, message = "性别为0或1")
    @ApiModelProperty(value = "性别", notes = "0为男，1为女")
    private Integer gender;

    @Range(min = 0, max = 120, message = "年龄非法")
    @ApiModelProperty(value = "年龄")
    private Integer age;

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
