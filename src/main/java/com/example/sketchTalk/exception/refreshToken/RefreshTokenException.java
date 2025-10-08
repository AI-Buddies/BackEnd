package com.example.sketchTalk.exception.refreshToken;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class RefreshTokenException extends RuntimeException {
    private final Boolean isSuccess = false;
    private final HttpStatus status = HttpStatus.UNAUTHORIZED;
    private final String errorMessage = "토큰이 유효하지 않거나, 만료되었습니다.";
    private final Object data = null;
}