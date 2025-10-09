package com.example.sketchTalk._core.common;

import com.example.sketchTalk._core.error.BaseErrorCode;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiResponse<T> {
    @JsonIgnore
    private final HttpStatus httpStatus;

    @JsonProperty("isSuccess")
    private final Boolean isSuccess;

    private final String statusCode;
    private final String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    // 성공
    private ApiResponse(HttpStatus httpStatus, String message, T data) {
        this.httpStatus = httpStatus;
        this.isSuccess = true;
        this.statusCode = String.valueOf(httpStatus.value());
        this.message = message;
        this.data = data;
    }

    // 실패
    private ApiResponse(BaseErrorCode errorCode, T data) {
        this.httpStatus = errorCode.getStatus();
        this.isSuccess = false;
        this.statusCode = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.data = data;
    }

    // 성공(데이터 유)
    public static <T> ApiResponse<T> onSuccess(HttpStatus httpStatus, T data) {
        return new ApiResponse<>(httpStatus, "요청 성공", data);
    }

    // 성공(데이터 무)
    public static ApiResponse<?> onSuccess(HttpStatus httpStatus) {
        return new ApiResponse<>(httpStatus, "요청 성공", null);
    }

    // 실패(데이터 유)
    public static <T> ApiResponse<T> onFailure(BaseErrorCode errorCode, T data) {
        return new ApiResponse<>(errorCode, data);
    }

    // 실패(데이터 무)
    public static ApiResponse<?> onFailure(BaseErrorCode errorCode) {
        return new ApiResponse<>(errorCode, null);
    }
}
