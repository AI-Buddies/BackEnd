package com.example.sketchTalk.dto.chat.out;

public record ChatReplyRes(
        String reply,
        Boolean isSufficient,
        String voice
) {
}
