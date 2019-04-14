package com.cody.codystage.utils;

import org.springframework.beans.BeanUtils;

public class CodyBeanUtils<T1, T2> {

    public static <T2> T2 beanCopyPropertoes(Object tEntity, Class<T2> tClass) {

        if (tEntity == null || tClass == null) {
            return null;
        }

        try {
            T2 newInstance = tClass.newInstance();
            BeanUtils.copyProperties(tEntity, newInstance);
            return newInstance;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

}
