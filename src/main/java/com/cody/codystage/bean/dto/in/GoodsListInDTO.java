package com.cody.codystage.bean.dto.in;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Classname GoodsListInDTO
 * @Description TODO
 * @Date 2019/5/20 23:29
 * @Created by ZQ
 */
@Data
@ApiModel(value = "批量查询商品信息")
public class GoodsListInDTO {

    @ApiModelProperty(value = "商品id列表",required=true)
    @NotNull
    List<Integer> goodsList;
}
