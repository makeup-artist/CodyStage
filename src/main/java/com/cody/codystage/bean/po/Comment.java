package com.cody.codystage.bean.po;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @Classname Comment
 * @Description TODO
 * @Date 2019/5/19 19:50
 * @Created by ZQ
 */
@Data
public class Comment {

    private Integer id;
    private Long author;
    private Integer belong;
    private Timestamp createTime;
    private Timestamp updateTime;
    private Integer  type;
    private String content;
    private Integer grade;
    private Integer to_comment_id;
}
