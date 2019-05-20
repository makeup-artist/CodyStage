package com.cody.codystage.bean.dto.in;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Map;

/**
 * @Classname OrderAddInDTO
 * @Description TODO
 * @Date 2019/5/20 23:15
 * @Created by ZQ
 */
@Data
@ApiModel(value = "增加参数")
public class OrderAddInDTO {


    @ApiModelProperty(value = "Map里面key为商品id(int)，value为商品数量(int)",required=true)
    @NotNull(message = "不能为空")
    private List<Map<Integer, Integer>> orderList;
}
