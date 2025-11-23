package com.example.sketchTalk.dto.chat.in;

import com.example.sketchTalk.model.Style;

public record SelectedImageReq(
        Long diaryId,
        Style style,
        String imageUrl
) {
}
