package com.cody.codystage.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @Classname PictureMappper
 * @Description TODO
 * @Date 2019/5/20 22:08
 * @Created by ZQ
 */

@Mapper
public interface PictureMapper {


    @Select("select * from picture where goods_id=#{id}")
    List<Map<String,Object>> getPictureList(@Param("id") Integer id);

    List<Map<String,Object>> getPictureListByList(List<Integer> list);
}
