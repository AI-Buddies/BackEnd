package com.example.sketchTalk.dto.setting.out;

import java.time.LocalDate;

public record GetAppInfoRes (
    String platform,
    String currentVersion,
    LocalDate date
) {
}