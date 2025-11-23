package com.example.sketchTalk.dto.chat.out;

import com.example.sketchTalk.dto.webClient.in.CommentDataBody;
import com.example.sketchTalk.dto.webClient.in.DiaryDataBody;

public record CommentRes(
        int statusCode,
        String message,
        CommentDataBody data,
        boolean isSuccess
) {
}
