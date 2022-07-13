package com.mark.cheng.enums;

import org.springframework.http.HttpStatus;

/**
 * @author cheng
 * @since 2022/5/30 20:51
 **/
public enum ApiResultStatus {
    SUCCESS(HttpStatus.OK.value(), "Success"),
    ERROR(1, "Error: %s"),
    PARAM_ERROR(2, "參數錯誤"),
    USER_NOT_FOUND(3, "帳號密碼錯誤"),
    USER_EXISTS(4, "此帳號已存在"),
    UNSUPPORTED_TYPE(5, "不支援的類型"),

    TOKEN_VALIDATION_FAILED(HttpStatus.UNAUTHORIZED.value(), "TOKEN驗證失敗"),
    SYSTEM_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系統異常: %s"),
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
