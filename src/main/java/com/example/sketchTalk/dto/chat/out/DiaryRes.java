package com.example.sketchTalk.dto.chat.out;

import com.example.sketchTalk.dto.webClient.in.DiaryDataBody;

public record DiaryRes(
        int statusCode,
        String message,
        DiaryDataBody data,
        boolean isSuccess
) {
}
