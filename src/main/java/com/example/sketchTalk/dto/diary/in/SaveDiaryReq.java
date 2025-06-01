package com.example.sketchTalk.dto.diary.in;

import com.example.sketchTalk.model.Emotion;
import com.example.sketchTalk.model.entity.User;

import java.util.Date;

public record SaveDiaryReq(
        User user,
        String title,
        String content,
        Date date,
        Emotion emotion
) {
}
