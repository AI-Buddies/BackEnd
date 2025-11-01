package com.example.sketchTalk.exception.chat;

import com.example.sketchTalk._core.error.BaseErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ChatExceptions implements BaseErrorCode {
    SEND_CHAT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C1", "대화 요청 과정에 오류가 있습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    ChatExceptions(HttpStatus httpStatus, String code, String message) {
        this.status = httpStatus;
        this.code = code;
        this.message = message;
    }
}
