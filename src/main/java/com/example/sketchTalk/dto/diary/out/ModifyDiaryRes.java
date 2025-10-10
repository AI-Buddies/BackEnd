package com.example.sketchTalk.dto.diary.out;

import com.example.sketchTalk.model.Emotion;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record ModifyDiaryRes(
        Long diaryId,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate date,
        String title,
        Emotion emotion,
        String content
) {
}
