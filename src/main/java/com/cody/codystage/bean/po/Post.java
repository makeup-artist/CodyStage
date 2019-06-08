package com.cody.codystage.bean.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @Classname Post
 * @Description TODO
 * @Date 2019/5/13 23:00
 * @Created by ZQ
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    private int id;
    private long author;
    private Timestamp createTime;
    private Timestamp updateTime;
    private String title;
    private String content;
    private int star;
}
