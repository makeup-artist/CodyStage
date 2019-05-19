package com.cody.codystage.bean.po;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @Classname Video
 * @Description TODO
 * @Date 2019/5/19 15:00
 * @Created by ZQ
 */
@Data
public class Video {

    private Integer id;
    private Long author;
    private String url;
    private String title;
    private Integer star;
    private Timestamp createTime;
    private Timestamp updateTime;
}
