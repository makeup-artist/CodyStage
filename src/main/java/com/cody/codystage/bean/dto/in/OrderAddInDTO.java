package com.cody.codystage.bean.dto.in;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
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
    private Map<Object, Object> orderMap;
}
