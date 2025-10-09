package com.example.sketchTalk._core.error;

import com.example.sketchTalk._core.common.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // CustomException 처리
    @ExceptionHandler(CustomException.class)
    public ApiResponse<Object> handleCustomException(CustomException e) {
        BaseErrorCode errorCode = e.getErrorCode();
        log.error("CustomException: code={}, message={}", errorCode.getCode(), errorCode.getMessage());

        return ApiResponse.onFailure(errorCode, e.getData());
    }

    // @Valid 유효성 검사 실패
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BaseErrorCode errorCode = CommonExceptions.INVALID_ARGUMENT;
        log.error("MethodArgumentNotValidException: {}", e.getMessage());

        return ApiResponse.onFailure(errorCode);
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<Object> handleException(Exception e) {
        BaseErrorCode errorCode = CommonExceptions.INTERNAL_SERVER_ERROR;
        log.error("Unhandled Exception: {}", e.getMessage());
        e.printStackTrace();

        return ApiResponse.onFailure(errorCode, e.getMessage());
    }
}
