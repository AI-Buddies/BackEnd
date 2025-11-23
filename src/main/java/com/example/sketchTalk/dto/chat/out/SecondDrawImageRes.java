package com.example.sketchTalk.dto.chat.out;

import com.example.sketchTalk.model.Style;

public record SecondDrawImageRes(
        Long diaryId,
        Style style,
        String imageURL,
        String prevImageUrl
) {
}
