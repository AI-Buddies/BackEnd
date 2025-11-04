package com.example.sketchTalk.controller.setting.alarm;

import com.example.sketchTalk._core.common.ApiResponse;
import com.example.sketchTalk.dto.setting.in.GetSettingReq;
import com.example.sketchTalk.dto.setting.in.SetRemainderAlarmSettingReq;
import com.example.sketchTalk.dto.setting.out.GetRemainderAlarmSettingRes;
import com.example.sketchTalk.dto.setting.out.SetRemainderAlarmSettingRes;
import com.example.sketchTalk.service.setting.alarm.RemainderAlarmSettingService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class RemainderAlarmSettingController {
    private final RemainderAlarmSettingService remainderAlarmSettingService;

    public RemainderAlarmSettingController(RemainderAlarmSettingService remainderAlarmSettingService) {
        this.remainderAlarmSettingService = remainderAlarmSettingService;
    }

    @GetMapping("/setting/notify/remainder")
    public ApiResponse<GetRemainderAlarmSettingRes> getRemainderAlarmSetting(@RequestBody GetSettingReq req) {
        GetRemainderAlarmSettingRes result = remainderAlarmSettingService.getRemainderAlarmSetting(req);

        return ApiResponse.onSuccess(HttpStatus.OK, result);
    }

    @PatchMapping("/setting/notify/remainder")
    public ApiResponse<SetRemainderAlarmSettingRes> setRemainderAlarmSetting(@RequestBody SetRemainderAlarmSettingReq req) {
        SetRemainderAlarmSettingRes result = remainderAlarmSettingService.setRemainderAlarmSetting(req);

        return ApiResponse.onSuccess(HttpStatus.OK, result);
    }
}