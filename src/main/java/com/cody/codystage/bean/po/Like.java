package com.cody.codystage.bean.po;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @Classname Like
 * @Description TODO
 * @Date 2019/5/19 22:41
 * @Created by ZQ
 */
@Data
@Builder
public class Like {

    private Integer id;
    private Long author;
    private Timestamp createTime;
    private Timestamp updateTime;
    private Integer type;
    private Integer belong;

}
