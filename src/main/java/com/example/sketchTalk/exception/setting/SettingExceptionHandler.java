package com.example.sketchTalk.exception.setting;

import com.example.sketchTalk.exception.ExceptionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class SettingExceptionHandler {
    @ExceptionHandler()
    public ResponseEntity<ExceptionResponse> handle(SettingException e) {
        ExceptionResponse response = ExceptionResponse.builder()
                .status(e.getHttpStatus())
                .message(e.getMessage())
                .data(null)
                .build();

        return ResponseEntity.status(e.getHttpStatus()).body(response);
    }
}