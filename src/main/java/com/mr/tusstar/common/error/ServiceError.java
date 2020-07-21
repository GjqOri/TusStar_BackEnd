package com.mr.tusstar.common.error;

/**
 * @author Qi
 * @create 2020-07-20 14:06
 */
public interface ServiceError {

    /**
     * 获取错误码
     * @return Integer
     */
    Integer getCode();

    /**
     * 获取错误信息
     * @return String
     */
    String getMessage();

}
