package com.example.sketchTalk.dto.setting.out;

import lombok.Builder;

@Builder
public record SendInquiryRes(
        String title,
        String content
) {
}