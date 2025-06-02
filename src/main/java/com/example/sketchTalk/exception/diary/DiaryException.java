package com.example.sketchTalk.exception.diary;

public class DiaryException extends RuntimeException {

    public DiaryException(int status, String message) {
        super(message);
    }

    public DiaryException(Long id) {
        super("Diary Not Found: " + id);
    }
}
