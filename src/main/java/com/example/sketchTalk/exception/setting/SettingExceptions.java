package com.example.sketchTalk.exception.setting;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum SettingExceptions {
    INVALID_VALUE(HttpStatus.BAD_REQUEST, "S1", "잘못된 값입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    SettingExceptions(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
