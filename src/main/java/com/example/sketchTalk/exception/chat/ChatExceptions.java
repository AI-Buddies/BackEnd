package com.example.sketchTalk.exception.chat;

import com.example.sketchTalk._core.error.BaseErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ChatExceptions implements BaseErrorCode {
    SEND_CHAT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C1", "대화 요청 과정에 오류가 있습니다."),
    WRITE_DIARY_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C2", "일기 작성 요청 오류"),
    DRAW_IMAGE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C3", "그림 요청 오류");

    private final HttpStatus status;
    private final String code;
    private final String message;

    ChatExceptions(HttpStatus httpStatus, String code, String message) {
        this.status = httpStatus;
        this.code = code;
        this.message = message;
    }
}
