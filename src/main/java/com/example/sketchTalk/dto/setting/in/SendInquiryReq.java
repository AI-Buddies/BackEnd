package com.example.sketchTalk.dto.setting.in;

import java.time.LocalDateTime;

public record SendInquiryReq(
        // TODO: 추후 User로 바꾸기!
        Long userId,
        String title,
        String content
) {
}
