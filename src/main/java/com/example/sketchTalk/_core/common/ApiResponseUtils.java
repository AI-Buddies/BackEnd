package com.example.sketchTalk._core.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ApiResponseUtils {
    public static <T> ResponseEntity<ApiResponse<T>> ok(T data) {
        ApiResponse<T> body = ApiResponse.onSuccess(HttpStatus.OK, data);

        return ResponseEntity
                .status(body.getHttpStatus())
                .body(body);
    }
}