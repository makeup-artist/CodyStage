package com.cody.codystage.controller;

import com.cody.codystage.bean.dto.in.GoodsListInDTO;
import com.cody.codystage.common.base.BaseApiService;
import com.cody.codystage.common.base.BaseResponse;
import com.cody.codystage.common.constants.ResConstants;
import com.cody.codystage.service.CommonService;
import com.cody.codystage.service.GoodsService;
import com.cody.codystage.utils.RequestUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * @Classname GoodsController
 * @Description TODO
 * @Date 2019/5/20 22:00
 * @Created by ZQ
 */

@RestController
@RequestMapping("/api/goods")
@Api(tags = "商品API")
public class GoodsController extends BaseApiService<Object> {

    @Resource
    private CommonService commonService;

    @Resource
    private GoodsService goodsService;


    @GetMapping(value = "/list", params = {"type", "page", "limit"})
    @ApiOperation(value = "获取商品列表 type(1口红 2底妆 3眉毛 4美瞳 5发色)")
    public BaseResponse<Object> getGoodsList(HttpServletRequest request, HttpServletResponse response) {
        commonService.checkPageAndLimit(request);

        int type = RequestUtil.getInt(request, "type", 0);
        int page = RequestUtil.getInt(request, "page", 1);
        int limit = RequestUtil.getInt(request, "limit", 10);

        List<Map<String, Object>> goodsList = goodsService.getGoodsList(type, (page - 1) * limit, limit);

        return setResult(ResConstants.HTTP_RES_CODE_200, ResConstants.HTTP_RES_CODE_200_VALUE_1, goodsList);

    }

    @GetMapping(value = "/detail", params = {"id"})
    @ApiOperation(value = "获取商品详情，包括商品详细信息和图片列表")
    public BaseResponse<Object> getGoodsDetail(HttpServletRequest request, HttpServletResponse response) {
        commonService.checkId(request);

        int id = RequestUtil.getInt(request, "id", 0);
        Map<String, Object> goodsDetail = goodsService.getGoodsDetail(id);

        return setResult(ResConstants.HTTP_RES_CODE_200, ResConstants.HTTP_RES_CODE_200_VALUE_1, goodsDetail);
    }

    @PostMapping(value = "/detail/list")
    @ApiOperation(value = "获取商品详情，包括商品详细信息和图片列表")
    public BaseResponse<Object> getGoodsDetailByList(@Valid GoodsListInDTO goodsListInDTO, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) {
        commonService.checkDto(bindingResult);

        List<Integer> goodsIdList = goodsListInDTO.getGoodsList();
        List<Map<String, Object>> goodsDetailList = goodsService.getGoodsDetailByList(goodsIdList);

        return setResult(ResConstants.HTTP_RES_CODE_200, ResConstants.HTTP_RES_CODE_200_VALUE_1, goodsDetailList);
    }
}
