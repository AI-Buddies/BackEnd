package com.example.sketchTalk.dto.diary.in;

import com.example.sketchTalk.model.Emotion;
import com.example.sketchTalk.model.entity.User;

import java.util.Date;

public record saveDiaryReq(
        User user,
        String title,
        String comment,
        Date date,
        Emotion emotion
) {
}
