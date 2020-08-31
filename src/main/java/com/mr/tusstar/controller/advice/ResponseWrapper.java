package com.mr.tusstar.controller.advice;

import com.mr.tusstar.common.entity.ResultModel;
import com.mr.tusstar.common.error.ServiceError;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author Qi
 * @create 2020-07-20 14:41
 */

@ControllerAdvice
public class ResponseWrapper implements ResponseBodyAdvice<Object> {

    /**
     * 是否支持advice功能
     * @param returnType
     * @param aClass
     * @return true-支持，false-不支持
     */
    @Override
    public boolean supports(MethodParameter returnType, Class aClass) {
        return !returnType.getGenericParameterType().equals(ResultModel.class);
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (o instanceof ServiceError) {
            ResultModel result = ResultModel.wrapErrorResult((ServiceError) o);
            // System.out.println(result);
            return result;
        }
        else {
            ResultModel result = ResultModel.wrapSuccessfulResult(o);
            // System.out.println(result);
            return result;
        }
    }
}
