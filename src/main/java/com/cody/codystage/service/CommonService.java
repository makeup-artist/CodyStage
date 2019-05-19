package com.cody.codystage.service;

import com.cody.codystage.common.constants.ResConstants;
import com.cody.codystage.common.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;

/**
 * @Classname CommentService
 * @Description TODO
 * @Date 2019/5/12 21:42
 * @Created by ZQ
 */
@Service
@Slf4j
public class CommonService {

    public void  checkDto(BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new ServiceException(ResConstants.HTTP_RES_CODE_1206, bindingResult.toString());
        }
    }

    public void checkPageAndLimit(HttpServletRequest request){
        if(request.getParameter("page").isEmpty()|| request.getParameter("limit").isEmpty()){
            throw new ServiceException(ResConstants.HTTP_RES_CODE_401, ResConstants.HTTP_RES_CODE_401_VALUE);
        }
    }

    public void checkCondition(HttpServletRequest request){
        if(request.getParameter("condition").isEmpty()|| request.getParameter("page").isEmpty()|| request.getParameter("limit").isEmpty()){
            throw new ServiceException(ResConstants.HTTP_RES_CODE_401, ResConstants.HTTP_RES_CODE_401_VALUE);
        }
    }

    public void checkId(HttpServletRequest request){
        if(request.getParameter("id").isEmpty()){
            throw new ServiceException(ResConstants.HTTP_RES_CODE_401, ResConstants.HTTP_RES_CODE_401_VALUE);
        }
    }

    public void checkBelongAndType(HttpServletRequest request){
        if(request.getParameter("belong").isEmpty() || request.getParameter("type").isEmpty()){
            throw new ServiceException(ResConstants.HTTP_RES_CODE_401, ResConstants.HTTP_RES_CODE_401_VALUE);
        }
    }
}
