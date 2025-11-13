package com.example.sketchTalk.dto.chat.in;

public record PrevDrawReq(
        String content,
        String style,
        String prevImageUrl
) {
}
