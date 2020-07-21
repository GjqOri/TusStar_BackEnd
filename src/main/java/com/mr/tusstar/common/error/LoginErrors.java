package com.mr.tusstar.common.error;

/**
 * @author Qi
 * @create 2020-07-21 17:25
 */
public enum LoginErrors implements ServiceError  {

    NOAUTHC_ERROR(2001, "请先登录"),
    NOUSER_ERROR(2002,"登录失败,用户名或密码错误"),
    ;

    private Integer code;
    private String message;

    LoginErrors(Integer code, String message) {
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
