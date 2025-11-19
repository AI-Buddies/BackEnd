package com.example.sketchTalk.dto.chat.in;

import com.example.sketchTalk.model.Style;

public record PrevDrawReq(
        Long diaryId,
        String content,
        Style style,
        String prevImageUrl
) {
}
