package com.example.sketchTalk.dto.setting.out;

import lombok.Builder;

@Builder
public record SendInquiryRes(
        Long userId,
        String title,
        String content
) {
}