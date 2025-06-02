package com.example.sketchTalk.dto.diary.in;

import com.example.sketchTalk.model.Emotion;

public record ModifyDiaryReq(
        Long diaryId,
        String title,
        String content,
        Emotion emotion
) {
}
