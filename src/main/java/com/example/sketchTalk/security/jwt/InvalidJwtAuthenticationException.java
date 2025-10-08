package com.example.sketchTalk.security.jwt;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;

public class InvalidJwtAuthenticationException extends AuthenticationException {
    private final Boolean isSuccess = false;
    private final HttpStatus status = HttpStatus.UNAUTHORIZED;
    private String errorMessage;
    private final Object data = null;

    public InvalidJwtAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}