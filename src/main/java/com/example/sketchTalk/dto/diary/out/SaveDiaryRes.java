package com.example.sketchTalk.dto.diary.out;

import com.example.sketchTalk.model.Emotion;

public record SaveDiaryRes(
        Long diaryId,
        String title,
        String content,
        Emotion emotion
) {
}
