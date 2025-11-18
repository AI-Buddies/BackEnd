package com.example.sketchTalk.dto.chat.out;

import com.example.sketchTalk.model.Emotion;

public record WriteDiaryRes(
        String title,
        String content,
        Emotion emotion
) {
}
