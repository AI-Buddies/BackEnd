package com.example.sketchTalk.exception.diary;

import org.springframework.http.HttpStatus;

public class DiaryException extends RuntimeException {

    public final DiaryExceptions diaryExceptions;

    public DiaryException(DiaryExceptions diaryExceptions) {
        this.diaryExceptions = diaryExceptions;
    }

    public HttpStatus getHttpStatus() {
        return diaryExceptions.getStatus();
    }

    public String getMessage() {
        return diaryExceptions.getMessage();
    }
}
