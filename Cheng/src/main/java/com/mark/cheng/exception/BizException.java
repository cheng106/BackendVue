package com.mark.cheng.exception;

import com.mark.cheng.enums.ApiResultStatus;

/**
 * @author cheng
 * @since 2022/5/30 20:50
 **/
public class BizException extends RuntimeException {
    private final int code;

    private BizException(int code, String msg) {
        super(msg);
        this.code = code;
    }

    private BizException(Throwable throwable, int code, String msg) {
        super(msg, throwable);
        this.code = code;
    }

    public static BizException error(String msg) {
        return new BizException(ApiResultStatus.ERROR.getCode(), String.format(ApiResultStatus.ERROR.getMsg(), msg));
    }

    public static BizException error(Throwable throwable, String msg) {
        return new BizException(throwable, ApiResultStatus.ERROR.getCode(), String.format(ApiResultStatus.ERROR.getMsg(), msg));
    }

    public static BizException error(Throwable throwable, ApiResultStatus status) {
        return new BizException(throwable, status.getCode(), status.getMsg());
    }

    public static BizException create(ApiResultStatus status, Object... args) {
        String msg;
        if (args.length == 0) {
            msg = status.getMsg();
        } else {
            msg = String.format(status.getMsg(), args);
        }

        return new BizException(status.getCode(), msg);
    }

    public int getCode() {
        return code;
    }
}
