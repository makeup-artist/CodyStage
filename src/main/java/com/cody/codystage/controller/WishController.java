package com.cody.codystage.controller;

import com.cody.codystage.common.base.BaseApiService;
import com.cody.codystage.common.base.BaseResponse;
import com.cody.codystage.common.constants.ResConstants;
import com.cody.codystage.service.CommonService;
import com.cody.codystage.service.WishService;
import com.cody.codystage.utils.JwtTokenUtil;
import com.cody.codystage.utils.RequestUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @Classname WishController
 * @Description TODO
 * @Date 2019/5/25 17:35
 * @Created by ZQ
 */
@RestController
@RequestMapping("/api/wish")
@Api(tags = "心愿单API")
public class WishController extends BaseApiService<Object> {

    @Resource
    private CommonService commonService;

    @Resource
    private WishService wishService;

    @GetMapping(value = "/add", params = {"belong"})
    @ApiOperation(value = "用户增加商品 需要商品id (token yes)")
    public BaseResponse<Object> addLike(HttpServletRequest request, HttpServletResponse response) {

        commonService.checkBelong(request);
        int belong = RequestUtil.getInt(request, "belong", 0);
        Long userId = JwtTokenUtil.getUserId(request);
        wishService.addWish(userId, belong);

        return setResultSuccess("点赞成功");
    }

    @DeleteMapping(value = "/delete", params = {"belong"})
    @ApiOperation(value = "用户删除商品 需要商品id (token yes)")
    public BaseResponse<Object> deleteLike(HttpServletRequest request, HttpServletResponse response) {

        commonService.checkBelong(request);
        int belong = RequestUtil.getInt(request, "belong", 0);
        Long userId = JwtTokenUtil.getUserId(request);
        wishService.deleteWish(userId, belong);

        return setResultSuccess("删除成功");
    }

    @GetMapping(value = "/isWish", params = {"belong"})
    @ApiOperation(value = "用户是否收藏 需要商品id (token yes)")
    public BaseResponse<Object> isWish(HttpServletRequest request, HttpServletResponse response) {

        commonService.checkBelong(request);
        int belong = RequestUtil.getInt(request, "belong", 0);
        Long userId = JwtTokenUtil.getUserId(request);
        Boolean wish = wishService.isWish(userId, belong);

        if (wish) {
            return setResult(ResConstants.HTTP_RES_CODE_1229, ResConstants.HTTP_RES_CODE_1229_VALUE);
        } else {
            return setResult(ResConstants.HTTP_RES_CODE_1230, ResConstants.HTTP_RES_CODE_1230_VALUE);
        }
    }

    @GetMapping(value = "/wishList", params = {"page", "limit"})
    @ApiOperation(value = "用户心愿单列表 (token yes)")
    public BaseResponse<Object> wishList(HttpServletRequest request, HttpServletResponse response) {

        int page = RequestUtil.getInt(request, "page", 1);
        int limit = RequestUtil.getInt(request, "limit", 10);
        Long userId = JwtTokenUtil.getUserId(request);

        List<Map<String, Object>> maps = wishService.wishList(userId, (page - 1) * limit, limit);

        return setResult(ResConstants.HTTP_RES_CODE_200, ResConstants.HTTP_RES_CODE_200_VALUE_1,maps);
    }

}
