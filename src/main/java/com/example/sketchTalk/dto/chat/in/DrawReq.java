package com.example.sketchTalk.dto.chat.in;

import com.example.sketchTalk.model.Style;

public record DrawReq(
        String content,
        Style style
) {
}
