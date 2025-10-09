package com.example.sketchTalk.exception.user;

import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public enum UserExceptions {
    ID_NOT_FOUND(HttpStatus.UNAUTHORIZED, "U1", "ID_NOT_FOUND"),
    PASSWORD_MISMATCH(HttpStatus.UNAUTHORIZED, "U2", "PASSWORD_MISMATCH"),
    ID_ALREADY_EXISTS(HttpStatus.CONFLICT, "U3", "ID_ALREADY_EXISTS"),
    BIRTHDATE_INVALID(HttpStatus.BAD_REQUEST, "U4", "BIRTH_DATE_INVALID");

    private final HttpStatus status;
    private final String code;
    private final String message;

    UserExceptions(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
