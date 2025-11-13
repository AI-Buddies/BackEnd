package com.example.sketchTalk.dto.webClient.in;

public record SecondImageDataBody(
        Long diaryId,
        String imageURL,
        String prevImageUrl
) {
}
