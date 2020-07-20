package com.mr.tusstar.common.entity;


import com.mr.tusstar.common.error.ServiceError;

/**
 * @author Qi
 * @create 2020-07-20 14:26
 */
public class ResultModel {

    // 接口调用是否成功
    private boolean success;
    // 成功返回0,失败时返回具体错误码
    private Integer code;
    // 成功返回null,失败时返回具体错误信息
    private String message;
    // 成功返回具体数据,失败时返回null
    private Object data;

    // 返回值模板类一定要有get方法,否则无法转换为JSON字符串
    public boolean isSuccess() {
        return success;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(Object data) {
        this.data = data;
    }

    /**
     * 包装接口调用成功时返回的信息
     * @param data 返回的信息
     * @return
     */
    public static ResultModel wrapSuccessfulResult(Object data) {
        ResultModel result = new ResultModel();
        result.setSuccess(true);
        result.setCode(0);
        result.setMessage(null);
        result.setData(data);
        return result;
    }

    /**
     * 包装接口调用出错时返回的信息
     * @param errors 发生的具体错误
     * @return
     */
    public static ResultModel wrapErrorResult(ServiceError errors) {
        ResultModel result = new ResultModel();
        result.setSuccess(false);
        result.setCode(errors.getCode());
        result.setMessage(errors.getMessage());
        result.setData(null);
        return result;
    }

    @Override
    public String toString() {
        return "ResultModel{" +
                "success=" + success +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
