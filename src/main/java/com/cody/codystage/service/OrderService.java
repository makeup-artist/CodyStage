package com.cody.codystage.service;

import com.cody.codystage.bean.dto.in.PostAddInDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Map<String, Object> getOrderList(Long userId, Integer page, Integer limit) {
        return null;
    }

    public Integer addOrder(Long userId, PostAddInDTO postAddInDTO) {
        return null;
    }
}
