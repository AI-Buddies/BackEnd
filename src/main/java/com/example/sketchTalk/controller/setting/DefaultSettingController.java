package com.example.sketchTalk.controller.setting;

import com.example.sketchTalk._core.common.ApiResponse;
import com.example.sketchTalk.dto.setting.in.SetDefaultSettingReq;
import com.example.sketchTalk.dto.setting.out.GetAppInfoRes;
import com.example.sketchTalk.dto.setting.out.GetFAQListRes;
import com.example.sketchTalk.dto.setting.out.GetProfileRes;
import com.example.sketchTalk.dto.setting.out.SetDefaultSettingRes;
import com.example.sketchTalk.service.setting.DefaultSettingService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultSettingController {
    private final DefaultSettingService defaultSettingService;

    public DefaultSettingController(DefaultSettingService defaultSettingService) {
        this.defaultSettingService = defaultSettingService;
    }

    @GetMapping("/setting")
    public ApiResponse<GetProfileRes> getUserInformation(@AuthenticationPrincipal Long userId) {
        GetProfileRes result = defaultSettingService.getUserInformation(userId);

        return ApiResponse.onSuccess(HttpStatus.OK, result);
    }

    @PatchMapping("/setting")
    public ApiResponse<SetDefaultSettingRes> SetDefaultAlarm(
            @AuthenticationPrincipal Long userId,
            @RequestBody SetDefaultSettingReq req
    ) {
        SetDefaultSettingRes result = defaultSettingService.SetDefaultAlarm(userId, req);

        return ApiResponse.onSuccess(HttpStatus.OK, result);
    }

    @GetMapping("/setting/question")
    public ApiResponse<GetFAQListRes> getFAQList() {
        GetFAQListRes result = defaultSettingService.getFAQList();

        return ApiResponse.onSuccess(HttpStatus.OK, result);
    }

    @GetMapping("/setting/appinfo")
    public ApiResponse<GetAppInfoRes> getAppInformation() {
        GetAppInfoRes result = defaultSettingService.getAppInformation();

        return ApiResponse.onSuccess(HttpStatus.OK, result);
    }
}