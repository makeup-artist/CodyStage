package com.cody.codystage.bean.dto.in;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Classname VideoInfoListInDTO
 * @Description TODO
 * @Date 2019/5/20 0:16
 * @Created by ZQ
 */
@Data
@ApiModel(value = "批量查询视频信息")
public class VideoInfoListInDTO {

    @ApiModelProperty(value = "视频id列表",required=true)
    @NotNull
    List<Integer> videoList;
}
