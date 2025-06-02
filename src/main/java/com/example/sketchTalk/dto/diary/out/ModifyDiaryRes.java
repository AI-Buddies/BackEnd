package com.example.sketchTalk.dto.diary.out;

import com.example.sketchTalk.model.Emotion;
import lombok.Builder;

import java.util.Date;

@Builder
public record ModifyDiaryRes(
        Long diaryId,
        Long userId,//추후에 User로 변경
        String title,
        String content,
        Date date,
        Emotion emotion
) {
}
