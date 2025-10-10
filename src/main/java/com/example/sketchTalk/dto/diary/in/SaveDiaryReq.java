package com.example.sketchTalk.dto.diary.in;

import com.example.sketchTalk.model.Emotion;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record SaveDiaryReq(
        Long userId,//추후에 User로 변경
        String title,
        String content,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate date,
        Emotion emotion
) {
}
