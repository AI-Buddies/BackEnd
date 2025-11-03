package com.example.sketchTalk.dto.diary.out;

import com.example.sketchTalk.model.Emotion;

import java.time.LocalDate;

public record GetDiaryDetailRes(
        Long diaryId,
        LocalDate date,
        Emotion emotion,
        String title,
        String content,
        String imageUrl,
        String comment
) {
}
