package com.cody.codystage.mapper;

import com.cody.codystage.bean.po.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @Classname OrderMapper
 * @Description TODO
 * @Date 2019/5/20 22:46
 * @Created by ZQ
 */
@Mapper
public interface OrderMapper {


    @Insert("insert into `order`(order_id,client,goods,money) values(#{order_id},#{client},#{goods},#{money})")
    Integer addOrder(Order order);

    @Select("select * from `order` where order_id=#{order_id}")
    Map<String,Object> getOrder(@Param("order_id") Long order_id);
}
