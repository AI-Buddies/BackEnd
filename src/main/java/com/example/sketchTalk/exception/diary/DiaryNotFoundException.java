package com.example.sketchTalk.exception.diary;

import lombok.Getter;

@Getter
public class DiaryNotFoundException extends DiaryException {
    private final Long diaryId;

    public DiaryNotFoundException(Long diaryId) {
        super(DiaryExceptions.DIARY_NOT_FOUND);
        this.diaryId = diaryId;
    }
}
