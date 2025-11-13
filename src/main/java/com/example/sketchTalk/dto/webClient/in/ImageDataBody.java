package com.example.sketchTalk.dto.webClient.in;

import com.example.sketchTalk.model.Style;

public record ImageDataBody(
        Long diaryId,
        Style style,
        String imageURL
) {
}
