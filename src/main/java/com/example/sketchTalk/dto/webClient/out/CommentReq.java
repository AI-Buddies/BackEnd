package com.example.sketchTalk.dto.webClient.out;

public record CommentReq(
        Long userId,
        String content
) {
}
