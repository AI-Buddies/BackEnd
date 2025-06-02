package com.example.sketchTalk.dto.diary.in;

import com.example.sketchTalk.model.Emotion;

import java.util.Date;

public record ModifyDiaryReq(
        Long diaryId,
        Long userId,//추후에 User로 변경
        String title,
        String content,
        Date date,
        Emotion emotion
) {
}
