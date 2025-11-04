package com.example.sketchTalk.dto.diary.out;

import com.example.sketchTalk.model.Emotion;

import java.time.LocalDate;

public record GetDiaryPreviewRes(
        Long diaryId,
        LocalDate date,
        Emotion emotion,
        String title,
        String imageUrl
) {
}
