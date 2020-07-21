package com.mr.tusstar.common.error;

/**
 * @author Qi
 * @create 2020-07-20 14:11
 */
public enum UserErrors implements ServiceError {

    NOAUTHC_ERROR(1001, "用户未登录"),
    NOUSER_ERROR(1002,"登录失败,用户名或密码错误"),
    NOAUTHZ_ERROR(1003, "用户无访问该资源的权限"),
    REPEATLOGIN_ERROR(1004, "重复登录"),
    USEREXIST_ERROR(1005, "用户已存在"),
    REGISTER_ERROR(1006, "注册失败,请稍后再试"),
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
