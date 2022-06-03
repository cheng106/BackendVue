package com.mark.cheng.model;

import com.mark.cheng.enums.ApiResultStatus;
import com.mark.cheng.exception.BizException;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author cheng
 * @since 2022/5/24 23:26
 **/
@Data
@Accessors(chain = true)
public class R implements Serializable {
    private static final long serialVersionUID = 1;

    private int code;
    private String msg;
    private Object data;

    public static R success() {
        return success(null);
    }

    public static R success(Object data) {
        return new R()
                .setCode(ApiResultStatus.SUCCESS.getCode())
                .setMsg(ApiResultStatus.SUCCESS.getMsg())
                .setData(data);
    }

    public static R error() {
        return failed(ApiResultStatus.ERROR);
    }

    public static R error(String msg, Object... args) {
        return create(ApiResultStatus.ERROR.getCode(), msg, args);
    }

    public static R failed(ApiResultStatus status, Object... args) {
        return create(status.getCode(), status.getMsg(), args);
    }

    public static R failed(BizException e) {
        return create(e.getCode(), e.getMessage());
    }

    private static R create(int code, String msg, Object... args) {
        if (args.length > 0) {
            return new R()
                    .setCode(code)
                    .setMsg(String.format(msg, args));
        } else {
            return new R()
                    .setCode(code)
                    .setMsg(msg);
        }
    }
}
