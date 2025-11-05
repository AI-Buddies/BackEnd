package com.example.sketchTalk.dto.webClient.in;

import com.example.sketchTalk.model.Emotion;

public record DiaryDataBody(
        String title,
        String content,
        Emotion emotion
) {
}
