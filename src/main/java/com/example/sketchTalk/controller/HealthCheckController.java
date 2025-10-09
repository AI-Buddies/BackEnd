package com.example.sketchTalk.controller;

import com.example.sketchTalk._core.common.ApiResponse;
import com.example.sketchTalk.dto.test.HealthStatusDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/health-check")
@RequiredArgsConstructor
public class HealthCheckController {

    @GetMapping("/success")
    public ApiResponse<?> successCheck() {
        return ApiResponse.onSuccess(HttpStatus.OK);
    }

    @GetMapping("/success-data")
    public ApiResponse<HealthStatusDto> successDataCheck() {
        HealthStatusDto healthStatus = new HealthStatusDto("OK", LocalDateTime.now());
        return ApiResponse.onSuccess(HttpStatus.OK, healthStatus);
    }
}