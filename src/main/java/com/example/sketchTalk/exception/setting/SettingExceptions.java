package com.example.sketchTalk.exception.setting;

import com.example.sketchTalk._core.error.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SettingExceptions implements BaseErrorCode {
    INVALID_VALUE(HttpStatus.BAD_REQUEST, "S1", "잘못된 값입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}