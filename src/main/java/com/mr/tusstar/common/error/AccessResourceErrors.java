package com.mr.tusstar.common.error;

/**
 * @author Qi
 * @create 2020-07-21 17:30
 */
public enum AccessResourceErrors implements ServiceError  {

    NOAUTHZ_ERROR(3001, "您无访问该资源的权限"),
    ;

    private Integer code;
    private String message;

    AccessResourceErrors(Integer code, String message) {
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
