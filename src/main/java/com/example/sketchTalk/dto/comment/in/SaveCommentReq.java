package com.example.sketchTalk.dto.comment.in;

public record SaveCommentReq(
        Long diaryId,
        String content
) {
}
