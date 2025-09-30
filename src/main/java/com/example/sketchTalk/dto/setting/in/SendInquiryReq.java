package com.example.sketchTalk.dto.setting.in;

public record SendInquiryReq(
        Long userId,
        String title,
        String content
) {
}
