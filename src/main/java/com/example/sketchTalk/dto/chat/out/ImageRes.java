package com.example.sketchTalk.dto.chat.out;

import com.example.sketchTalk.dto.webClient.in.DiaryDataBody;
import com.example.sketchTalk.dto.webClient.in.ImageDataBody;

public record ImageRes(
        int statusCode,
        String message,
        ImageDataBody data,
        boolean isSuccess
) {
}
