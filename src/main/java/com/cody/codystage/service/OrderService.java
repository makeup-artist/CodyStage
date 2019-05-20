package com.cody.codystage.service;

import com.cody.codystage.mapper.OrderMapper;
import com.cody.codystage.utils.twitter.SnowflakeIdUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Classname OrderService
 * @Description TODO
 * @Date 2019/5/20 23:10
 * @Created by ZQ
 */
@Service
@Transactional
@Slf4j
public class OrderService {

    @Resource
    private GoodsService goodsService;

    @Resource
    private OrderMapper orderMapper;

    public Map<String, Object> getOrderList(Long userId, Integer page, Integer limit) {
        return null;
    }

    public Integer addOrder(Long userId, Map<Integer, Integer> orderMap) {
        List<Map<String, Object>> orderList = Lists.newArrayList();

        Long orderId = SnowflakeIdUtil.nextId();
        orderMap.forEach((k,v) -> {
            Map<String,Object> order= Maps.newHashMap();
            order.put("order_id",orderId);
            order.put("client",userId);
            order.put("number",v);
            order.put("goods_id",k);
            orderList.add(order);
        });
        return orderMapper.addOrder(orderList);
    }
}
