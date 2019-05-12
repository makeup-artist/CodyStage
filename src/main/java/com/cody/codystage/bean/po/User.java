package com.cody.codystage.bean.po;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @Classname User
 * @Description TODO
 * @Date 2019/4/14 22:45
 * @Created by ZQ
 */
@Data
public class User {
    private Long id;
    private String username;
    private String password;
    private Integer gender;
    private Integer age;
    private String mobile;
    private String nickname;
    private String picture;
    private String description;
    private String tag;
    private String email;
    private Timestamp updateTime;
    private Timestamp createTime;
    private Integer isAvailable;
    private String role;
}
