package com.example.sketchTalk.exception.user;

import org.springframework.http.HttpStatus;

public class UserException extends RuntimeException {
    public final UserExceptions userExceptions;

    public UserException(UserExceptions userExceptions) {
        this.userExceptions = userExceptions;
    }

    public HttpStatus getHttpStatus() {
        return userExceptions.getStatus();
    }

    public String getMessage() {
        return userExceptions.getMessage();
    }
}
