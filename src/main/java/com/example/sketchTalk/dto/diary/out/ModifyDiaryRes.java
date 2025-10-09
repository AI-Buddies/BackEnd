package com.example.sketchTalk.dto.diary.out;

import com.example.sketchTalk.model.Emotion;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record ModifyDiaryRes(
        Long diaryId,
        Long userId,//추후에 User로 변경
        String title,
        String content,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate date,
        Emotion emotion
) {
}
