package com.mark.cheng.exception;

import com.mark.cheng.enums.ApiResultStatus;
import com.mark.cheng.model.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * @author cheng
 * @since 2022/5/30 20:22
 **/
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({MissingServletRequestParameterException.class, HttpMessageNotReadableException.class,
            UnsatisfiedServletRequestParameterException.class, MethodArgumentTypeMismatchException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R badRequestException(Exception e) {
        log.error("###-ERR:{}", e.getMessage(), e);
        return R.failed(ApiResultStatus.PARAM_ERROR, e.getMessage());
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R exception(Throwable t) {
        log.error("系統異常:{}", t.getMessage(), t);
        return R.failed(ApiResultStatus.SYSTEM_ERROR, t.getMessage());

    }
}
