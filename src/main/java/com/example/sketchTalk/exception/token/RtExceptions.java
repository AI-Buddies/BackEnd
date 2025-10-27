package com.example.sketchTalk.exception.token;

import com.example.sketchTalk._core.error.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum RtExceptions implements BaseErrorCode {
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "RT1", "토큰이 유효하지 않거나, 만료되었습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
