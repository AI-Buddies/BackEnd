package com.example.sketchTalk.exception.diary;

import com.example.sketchTalk.controller.DiaryController;
import com.example.sketchTalk.exception.ExceptionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = DiaryController.class)
public class DiaryExceptionHandler {

    @ExceptionHandler(DiaryNotFoundException.class)
    private ResponseEntity<ExceptionResponse> handler(DiaryNotFoundException e) {
        ExceptionResponse response = ExceptionResponse.builder()
                .status(e.getHttpStatus())
                .message(e.getMessage())
                .data("diaryID : " + e.getDiaryId())
                .build();
        return ResponseEntity.status(e.getHttpStatus()).body(response);
    }
}
