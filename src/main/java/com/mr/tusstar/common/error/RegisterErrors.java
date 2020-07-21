package com.mr.tusstar.common.error;

/**
 * @author Qi
 * @create 2020-07-21 17:33
 */
public enum RegisterErrors implements ServiceError {

    USEREXIST_ERROR(1001, "用户已存在"),
    OTHER_ERROR(1002, "注册失败,请稍后再试"),
    ;

    private Integer code;
    private String message;

    RegisterErrors(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return this.code;
    };

    @Override
    public String getMessage() {
        return this.message;
    };

}
