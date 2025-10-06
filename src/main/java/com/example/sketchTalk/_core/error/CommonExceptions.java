package com.example.sketchTalk._core.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonExceptions implements BaseErrorCode {
    INVALID_ARGUMENT(HttpStatus.BAD_REQUEST, "C001", "잘못된 인자값입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C002", "서버 내부에 오류가 발생했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}