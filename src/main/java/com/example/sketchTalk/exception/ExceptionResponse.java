package com.example.sketchTalk.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ExceptionResponse {
    public final HttpStatus status;
    public final String message;
    public final Object data;

    @Builder
    public ExceptionResponse(HttpStatus status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

}
