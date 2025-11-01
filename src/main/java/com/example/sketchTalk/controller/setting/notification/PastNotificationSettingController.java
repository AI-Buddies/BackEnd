package com.example.sketchTalk.controller.setting.notification;

import com.example.sketchTalk._core.common.ApiResponse;
import com.example.sketchTalk.dto.setting.in.SetPastNotificationSettingReq;
import com.example.sketchTalk.dto.setting.out.GetPastNotificationSettingRes;
import com.example.sketchTalk.dto.setting.out.SetPastNotificationSettingRes;
import com.example.sketchTalk.service.setting.notification.PastNotificationSettingService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
public class PastNotificationSettingController {
    private final PastNotificationSettingService pastNotificationSettingService;

    public PastNotificationSettingController(PastNotificationSettingService pastNotificationSettingService) {
        this.pastNotificationSettingService = pastNotificationSettingService;
    }

    @GetMapping("/setting/notification/past")
    public ApiResponse<GetPastNotificationSettingRes> getPastNotificationSetting(@AuthenticationPrincipal Long userId) {
        GetPastNotificationSettingRes result = pastNotificationSettingService.getPastNotificationSetting(userId);

        return ApiResponse.onSuccess(HttpStatus.OK, result);
    }

    @PutMapping("/setting/notification/past")
    public ApiResponse<SetPastNotificationSettingRes> setPastNotificationAlarmSetting(
            @AuthenticationPrincipal Long userId,
            @RequestBody SetPastNotificationSettingReq req
    ) {
        SetPastNotificationSettingRes result = pastNotificationSettingService.setPastNotificationSetting(userId, req);

        return ApiResponse.onSuccess(HttpStatus.OK, result);
    }
}