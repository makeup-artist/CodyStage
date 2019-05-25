package com.cody.codystage.controller;

import com.cody.codystage.bean.dto.in.OrderAddInDTO;
import com.cody.codystage.common.base.BaseApiService;
import com.cody.codystage.common.base.BaseResponse;
import com.cody.codystage.common.constants.ResConstants;
import com.cody.codystage.service.CommonService;
import com.cody.codystage.service.OrderService;
import com.cody.codystage.utils.JwtTokenUtil;
import com.cody.codystage.utils.RequestUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * @Classname OrderController
 * @Description TODO
 * @Date 2019/5/20 23:08
 * @Created by ZQ
 */

@RestController
@RequestMapping("/api/order")
@Api(tags = "订单API")
public class OrderController extends BaseApiService<Object> {

    @Resource
    private CommonService commonService;

    @Resource
    private OrderService orderService;

    @GetMapping(value = "/list", params = {"page", "limit"})
    @ApiOperation(value = "获取订单列表 (token yes)")
    public BaseResponse<Object> getOrderList(HttpServletRequest request, HttpServletResponse response) {
        commonService.checkPageAndLimit(request);

        Long userId = JwtTokenUtil.getUserId(request);
        int page = RequestUtil.getInt(request, "page", 1);
        int limit = RequestUtil.getInt(request, "limit", 10);

        List<Object> orderList = orderService.getOrderList(userId, (page - 1) * limit, limit-1);
        return setResult(ResConstants.HTTP_RES_CODE_200, ResConstants.HTTP_RES_CODE_200_VALUE_1, orderList);
    }

    @PostMapping("/add")
    @ApiOperation(value = "增加订单 (token yes)")
    public BaseResponse<Object> addOrder(@RequestBody Map<Integer, Integer> orderMap, HttpServletRequest request, HttpServletResponse response) {

        Long userId = JwtTokenUtil.getUserId(request);
        orderService.addOrder(userId, orderMap);

        return setResult(ResConstants.HTTP_RES_CODE_200, ResConstants.HTTP_RES_CODE_200_VALUE_1);
    }
}
