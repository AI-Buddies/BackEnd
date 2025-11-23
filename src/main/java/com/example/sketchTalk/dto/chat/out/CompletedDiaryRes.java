package com.example.sketchTalk.dto.chat.out;

import com.example.sketchTalk.model.Emotion;

import java.time.Instant;
import java.util.List;

public record CompletedDiaryRes(
        Long diaryId,
        Instant date,
        Emotion emotion,
        String title,
        String content,
        String imageURL,
        String comment,
        Boolean achieved,
        List<String> achievedList,
        String voice
) {
}
