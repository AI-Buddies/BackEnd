package com.example.sketchTalk.exception.diary;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum DiaryExceptions {
    DIARY_NOT_FOUND(HttpStatus.NOT_FOUND, "D1", "해당하는 일기가 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    DiaryExceptions(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
