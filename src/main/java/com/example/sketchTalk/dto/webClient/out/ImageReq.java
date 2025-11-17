package com.example.sketchTalk.dto.webClient.out;

import com.example.sketchTalk.model.Style;

public record ImageReq(
        Long userId,
        String content,
        Style style
) {
}
