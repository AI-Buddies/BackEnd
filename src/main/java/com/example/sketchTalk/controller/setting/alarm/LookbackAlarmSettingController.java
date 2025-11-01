package com.example.sketchTalk.controller.setting.alarm;

import com.example.sketchTalk._core.common.ApiResponse;
import com.example.sketchTalk.dto.setting.in.SetLookbackAlarmSettingReq;
import com.example.sketchTalk.dto.setting.out.GetLookbackAlarmSettingRes;
import com.example.sketchTalk.dto.setting.out.SetLookbackAlarmSettingRes;
import com.example.sketchTalk.service.setting.alarm.LookbackAlarmSettingService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LookbackAlarmSettingController {
    private final LookbackAlarmSettingService lookbackAlarmSettingService;

    public LookbackAlarmSettingController(LookbackAlarmSettingService lookbackAlarmSettingService) {
        this.lookbackAlarmSettingService = lookbackAlarmSettingService;
    }

    @GetMapping("/setting/notify/lookback")
    public ApiResponse<GetLookbackAlarmSettingRes> getLookbackAlarmSetting(@AuthenticationPrincipal Long userId) {
        GetLookbackAlarmSettingRes result = lookbackAlarmSettingService.getLookbackAlarmSetting(userId);

        return ApiResponse.onSuccess(HttpStatus.OK, result);
    }

    @PatchMapping("/setting/notify/lookback")
    public ApiResponse<SetLookbackAlarmSettingRes> setLookbackAlarmSetting(
            @AuthenticationPrincipal Long userId,
            @RequestBody SetLookbackAlarmSettingReq req
    ) {
        SetLookbackAlarmSettingRes result = lookbackAlarmSettingService.setLookbackAlarmSetting(userId, req);

        return ApiResponse.onSuccess(HttpStatus.OK, result);
    }
}