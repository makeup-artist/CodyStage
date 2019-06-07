package com.cody.codystage.bean.dto.in;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Classname UserIdListInDto
 * @Description TODO
 * @Date 2019/6/2 21:31
 * @Created by ZQ
 */
@Data
@ApiModel(value = "批量查询用户信息")
public class UserIdListInDto {

    @ApiModelProperty(value = "用户id列表", required = true)
    @NotNull
    List<Long> idList;
}
