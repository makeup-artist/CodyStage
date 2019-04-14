package com.cody.codystage.dto.output;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Classname UserOutDTO
 * @Description TODO
 * @Date 2019/4/14 19:07
 * @Created by ZQ
 */
@Data
@ApiModel(value = "用户返回参数")
public class UserOutDTO {
    @ApiModelProperty(value = "用户ID")
    private Integer id;
    @ApiModelProperty(value = "用户名")
    private String username;
    @ApiModelProperty(value = "性别", notes = "0为男，1为女")
    private Integer gender;
    @ApiModelProperty(value = "手机号码")
    private Integer mobile;
    @ApiModelProperty(value = "年龄")
    private Integer age;
    @ApiModelProperty(value = "昵称")
    private String nickname;
    @ApiModelProperty(value = "头像地址")
    private String picture;
    @ApiModelProperty(value = "个人描述")
    private String description;
    @ApiModelProperty(value = "用户自己的标签")
    private String tag;
    @ApiModelProperty(value = "邮箱")
    private String email;
    @ApiModelProperty(value = "账户最近修改时间")
    private Date updateTime;
    @ApiModelProperty(value = "账户创建时间")
    private Date createTime;
    @ApiModelProperty(value = "账户是否可用", notes = "0可用，1不可用")
    private Integer isAvailable;
}
