package com.example.sketchTalk._core.error;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private final BaseErrorCode errorCode;
    private final Object data;

    public CustomException(BaseErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.data = null;
    }

    public CustomException(BaseErrorCode errorCode, Object data) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.data = data;
    }
}