package com.cody.codystage.bean.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname Order
 * @Description TODO
 * @Date 2019/5/25 14:31
 * @Created by ZQ
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    private Long order_id;
    private Long client;
    private String goods;
    private Double money;

}
