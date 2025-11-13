package com.example.sketchTalk.dto.webClient.out;

public record ImageReq(
        Long userId,
        String content,
        String style
) {
}
