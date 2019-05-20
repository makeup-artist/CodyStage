package com.cody.codystage.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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


    Integer addOrder(List<Map<String, Object>> list);
}
