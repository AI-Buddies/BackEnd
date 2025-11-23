package com.example.sketchTalk.dto.chat.out;

import com.example.sketchTalk.model.Style;

public record DrawImageRes(
        Long diaryId,
        Style style,
        String imageUrl
) {
}
