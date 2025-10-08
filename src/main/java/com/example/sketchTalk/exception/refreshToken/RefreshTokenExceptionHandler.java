package com.example.sketchTalk.exception.refreshToken;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class RefreshTokenExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<?> handle(RefreshTokenException e) {
        Map<String, Object> body = new HashMap<>();
        body.put("isSuccess", e.getIsSuccess());
        body.put("statusCode", e.getStatus().value());
        body.put("message", e.getErrorMessage());
        body.put("data", e.getData());

        return ResponseEntity.status(e.getStatus()).body(body);
    }
}