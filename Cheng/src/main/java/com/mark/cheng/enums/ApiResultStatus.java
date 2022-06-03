package com.mark.cheng.enums;

/**
 * @author cheng
 * @since 2022/5/30 20:51
 **/
public enum ApiResultStatus {
    SUCCESS(200, "success"),
    ERROR(1, "error"),
    PARAM_ERROR(2, "參數錯誤"),
    USER_NOT_FOUND(3, "帳號密碼錯誤"),
    USER_EXISTS(4, "此帳號已存在"),

    SYSTEM_ERROR(999, "系統異常: %s"),
    ;

    private final int code;
    private final String msg;

    ApiResultStatus(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }
}
