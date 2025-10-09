package com.example.sketchTalk.dto.test;

import java.time.LocalDateTime;

public record HealthStatusDto(
        String status,
        LocalDateTime serverTime
) {
}
