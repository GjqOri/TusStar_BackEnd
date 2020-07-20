package com.mr.tusstar.common.error;

/**
 * @author Qi
 * @create 2020-07-20 14:11
 */
public enum UserErrors implements ServiceError {

    NOAUTHC_ERROR(1001, "用户未认证"),
    NOUSER_ERROR(1002,"登录失败,用户名或密码错误"),
    NOPERM_ERROR(1003, "用户无访问该资源的权限"),
    ;

    private Integer code;
    private String message;

    UserErrors(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
