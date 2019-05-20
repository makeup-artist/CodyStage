package com.cody.codystage.mapper;

import com.cody.codystage.bean.dto.in.GoodsListInDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @Classname GoodsMapper
 * @Description TODO
 * @Date 2019/5/20 22:07
 * @Created by ZQ
 */
@Mapper
public interface GoodsMapper {


    @Select("select id,name,picture,description,type,price,createTime,updateTime from `goods` where type=#{type} limit #{page},#{limit}")
    List<Map<String, Object>> getGoodsList(@Param("type") Integer type, @Param("page") Integer page, @Param("limit") Integer limit);


    @Select("select * from `goods` where id=#{id}")
    Map<String, Object> getGoodsDetail(@Param("id") Integer id);

    List<Map<String,Object>>  getGoodsDetailByList(List<Integer> list);

    List<Map<String,Object>>  getGoodsPartByList(List<Integer> list);
}
