package com.example.sketchTalk.dto.chat.out;

import com.example.sketchTalk.dto.webClient.in.ChatDataBody;

public record ReplyRes(
        int statusCode,
        String message,
        ChatDataBody data,
        boolean isSuccess
) {
}
