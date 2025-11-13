package com.example.sketchTalk.dto.chat.in;

import com.example.sketchTalk.model.Style;

public record PrevDrawReq(
        String content,
        Style style,
        String prevImageUrl
) {
}
