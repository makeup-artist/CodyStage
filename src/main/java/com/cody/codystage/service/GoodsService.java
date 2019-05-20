package com.cody.codystage.service;

import com.cody.codystage.bean.dto.in.GoodsListInDTO;
import com.cody.codystage.mapper.GoodsMapper;
import com.cody.codystage.mapper.PictureMapper;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Classname GoodsService
 * @Description TODO
 * @Date 2019/5/20 22:01
 * @Created by ZQ
 */
@Service
@Slf4j
@Transactional
public class GoodsService {

    @Resource
    private GoodsMapper goodsMapper;

    @Resource
    private PictureMapper pictureMapper;

    public List<Map<String, Object>> getGoodsList(Integer type, Integer page, Integer limit) {
        return goodsMapper.getGoodsList(type, page, limit);
    }

    public Map<String, Object> getGoodsDetail(Integer id) {

        Map<String, Object> resMap = goodsMapper.getGoodsDetail(id);
        List<Map<String, Object>> pictureList = pictureMapper.getPictureList(id);
        resMap.put("pictureList", pictureList);

        return resMap;
    }

    public List<Map<String, Object>> getGoodsDetailByList(List<Integer> goodsIdList) {

        List<Map<String, Object>> goodsDetailByList = goodsMapper.getGoodsDetailByList(goodsIdList);
        List<Map<String, Object>> pictureListByList = pictureMapper.getPictureListByList(goodsIdList);

        Multimap<Object, Object> myMultimap = ArrayListMultimap.create();
        pictureListByList.forEach(e -> {
            Long goods_id = (Long) e.get("goods_id");
            myMultimap.put(goods_id, e);
        });

        for (Map<String, Object> goods : goodsDetailByList) {
            Long id = (Long) goods.get("id");
            Collection<Object> pictures = myMultimap.get(id);
            goods.put("pictureList", pictures);
        }
        return goodsDetailByList;
    }

    public List<Map<String, Object>> getGoodsPartByList(List<Integer> goodsIdList) {
        List<Map<String, Object>> res = null;
        if (!goodsIdList.isEmpty()) {
            res = goodsMapper.getGoodsPartByList(goodsIdList);
        }

        return res;
    }
}
