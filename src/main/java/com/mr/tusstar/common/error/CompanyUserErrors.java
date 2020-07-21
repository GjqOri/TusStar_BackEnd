package com.mr.tusstar.common.error;

/**
 * @author Qi
 * @create 2020-07-20 15:46
 */
public enum CompanyUserErrors implements ServiceError {

    NOAUTHC_ERROR(2001, "用户未登录"),
    NOUSER_ERROR(2002,"登录失败,用户名或密码错误"),
    NOAUTHZ_ERROR(2003, "企业用户无访问该资源的权限"),
    REPEATLOGIN_ERROR(2004, "重复登录"),
    ;

    private Integer code;
    private String message;

    CompanyUserErrors(Integer code, String message) {
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
