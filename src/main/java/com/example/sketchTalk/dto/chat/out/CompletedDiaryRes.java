package com.example.sketchTalk.dto.chat.out;

import com.example.sketchTalk.dto.category.out.AchievedResultRes;
import com.example.sketchTalk.model.Emotion;

import java.time.LocalDate;
import java.util.List;

public record CompletedDiaryRes(
        Long diaryId,
        LocalDate date,
        Emotion emotion,
        String title,
        String content,
        String imageURL,
        String comment,
        Boolean achieved,
        AchievedResultRes achievedResult,
        String voice
) {
}
