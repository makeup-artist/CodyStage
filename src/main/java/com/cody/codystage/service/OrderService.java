package com.cody.codystage.service;

import com.cody.codystage.bean.po.Order;
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
    private RedisService redisService;

    @Resource
    private OrderMapper orderMapper;

    public List<Object> getOrderList(Long userId, Integer page, Integer limit) {
        String key= "order:" + userId;
        return redisService.lGet(key, page, limit);
    }

    public void addOrder(Long userId, Map<Integer, Integer> orderMap) {
        List<Integer> orderList = Lists.newArrayList();

        Long orderId = SnowflakeIdUtil.nextId();

        orderMap.forEach((k, v) -> orderList.add(k));

        double sum = 0.0;
        List<Map<String, Object>> goodsDetailList = goodsService.getGoodsDetailByList(orderList);
        for (Map<String, Object> map : goodsDetailList) {
            Long id = (Long) map.get("id");
            Integer num = orderMap.get(id.intValue());
            sum += num * (Double) map.get("price");
            map.put("num", num);
        }

        Order order = Order.builder().client(userId).order_id(orderId).money(sum).goods(goodsDetailList.toString()).build();
        orderMapper.addOrder(order);
        Map<String, Object> order1 = orderMapper.getOrder(orderId);
        String key = "order:" + userId;
        redisService.lSet(key, order1);
    }
}
