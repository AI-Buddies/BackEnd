package com.example.sketchTalk.controller.setting.alarm;

import com.example.sketchTalk._core.common.ApiResponse;
import com.example.sketchTalk.dto.setting.in.GetSettingReq;
import com.example.sketchTalk.dto.setting.in.SetLookbackAlarmSettingReq;
import com.example.sketchTalk.dto.setting.out.GetLookbackAlarmSettingRes;
import com.example.sketchTalk.dto.setting.out.SetLookbackAlarmSettingRes;
import com.example.sketchTalk.service.setting.alarm.LookbackAlarmSettingService;
import org.springframework.http.HttpStatus;
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
    public ApiResponse<GetLookbackAlarmSettingRes> getLookbackAlarmSetting(@RequestBody GetSettingReq req) {
        GetLookbackAlarmSettingRes result = lookbackAlarmSettingService.getLookbackAlarmSetting(req);

        return ApiResponse.onSuccess(HttpStatus.OK, result);
    }

    @PatchMapping("/setting/notify/lookback")
    public ApiResponse<SetLookbackAlarmSettingRes> setLookbackAlarmSetting(@RequestBody SetLookbackAlarmSettingReq req) {
        SetLookbackAlarmSettingRes result = lookbackAlarmSettingService.setLookbackAlarmSetting(req);

        return ApiResponse.onSuccess(HttpStatus.OK, result);
    }
}