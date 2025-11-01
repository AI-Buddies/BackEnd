package com.example.sketchTalk.controller.setting.notification;

import com.example.sketchTalk._core.common.ApiResponse;
import com.example.sketchTalk.dto.setting.in.SetWritingNotificationSettingReq;
import com.example.sketchTalk.dto.setting.out.GetWritingNotificationSettingRes;
import com.example.sketchTalk.dto.setting.out.SetWritingNotificationSettingRes;
import com.example.sketchTalk.service.setting.notification.WritingNotificationSettingService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
public class WritingNotificationSettingController {
    private final WritingNotificationSettingService writingNotificationSettingService;

    public WritingNotificationSettingController(WritingNotificationSettingService writingNotificationSettingService) {
        this.writingNotificationSettingService = writingNotificationSettingService;
    }

    @GetMapping("/setting/notification/post")
    public ApiResponse<GetWritingNotificationSettingRes> getWritingNotificationSetting(@AuthenticationPrincipal Long userId) {
        GetWritingNotificationSettingRes result = writingNotificationSettingService.getWritingNotificationSetting(userId);

        return ApiResponse.onSuccess(HttpStatus.OK, result);
    }

    @PatchMapping("/setting/notification/post")
    public ApiResponse<SetWritingNotificationSettingRes> setWritingNotificationSetting(
            @AuthenticationPrincipal Long userId,
            @RequestBody SetWritingNotificationSettingReq req
    ) {
        SetWritingNotificationSettingRes result = writingNotificationSettingService.setWritingNotificationSetting(userId, req);

        return ApiResponse.onSuccess(HttpStatus.OK, result);
    }
}