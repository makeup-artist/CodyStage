package com.cody.codystage.controller;

import com.cody.codystage.bean.dto.in.PostAddInDTO;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
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

        Map<String, Object> resMap = orderService.getOrderList(userId, page, limit);
        return setResult(ResConstants.HTTP_RES_CODE_200, ResConstants.HTTP_RES_CODE_200_VALUE_1, resMap);
    }

    @PostMapping("/add")
    @ApiOperation(value = "增加订单 (token yes)")
    public BaseResponse<Object> addOrder(@Valid PostAddInDTO postAddInDTO, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) {
        commonService.checkDto(bindingResult);

        Long userId = JwtTokenUtil.getUserId(request);
        Integer res = orderService.addOrder(userId, postAddInDTO);
        if (res > 0) {
            return setResult(ResConstants.HTTP_RES_CODE_200, ResConstants.HTTP_RES_CODE_200_VALUE_1);
        } else {
            return setResult(ResConstants.HTTP_RES_CODE_1216, ResConstants.HTTP_RES_CODE_1216_VALUE);
        }
    }
}
