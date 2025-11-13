package com.example.sketchTalk.exception.user;

import com.example.sketchTalk._core.error.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserExceptions implements BaseErrorCode {
    ID_NOT_FOUND(HttpStatus.UNAUTHORIZED, "U1", "ID를 찾을 수 없습니다."),
    PASSWORD_MISMATCH(HttpStatus.UNAUTHORIZED, "U2", "비밀번호가 일치하지 않습니다."),
    ID_ALREADY_EXISTS(HttpStatus.CONFLICT, "U3", "ID가 이미 존재합니다."),
    BIRTHDATE_INVALID(HttpStatus.BAD_REQUEST, "U4", "생년월일이 알맞지 않습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "U5", "해당 사용자가 존재하지 않습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}