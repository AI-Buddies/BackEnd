package com.example.sketchTalk.controller.setting.alarm;

import com.example.sketchTalk._core.common.ApiResponse;
import com.example.sketchTalk._core.common.ApiResponseUtils;
import com.example.sketchTalk.dto.setting.in.GetSettingReq;
import com.example.sketchTalk.dto.setting.in.SetRemainderAlarmSettingReq;
import com.example.sketchTalk.dto.setting.out.GetRemainderAlarmSettingRes;
import com.example.sketchTalk.dto.setting.out.SetRemainderAlarmSettingRes;
import com.example.sketchTalk.service.setting.alarm.RemainderAlarmSettingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RemainderAlarmSettingController {
    private final RemainderAlarmSettingService remainderAlarmSettingService;

    public RemainderAlarmSettingController(RemainderAlarmSettingService remainderAlarmSettingService) {
        this.remainderAlarmSettingService = remainderAlarmSettingService;
    }

    @GetMapping("/setting/notify/remainder")
    public ResponseEntity<ApiResponse<GetRemainderAlarmSettingRes>> getRemainderAlarmSetting(@RequestBody GetSettingReq req) {
        GetRemainderAlarmSettingRes result = remainderAlarmSettingService.getRemainderAlarmSetting(req);

        return ApiResponseUtils.ok(result);
    }

    @PatchMapping("/setting/notify/remainder")
    public ResponseEntity<ApiResponse<SetRemainderAlarmSettingRes>> setRemainderAlarmSetting(@RequestBody SetRemainderAlarmSettingReq req) {
        SetRemainderAlarmSettingRes result = remainderAlarmSettingService.setRemainderAlarmSetting(req);

        return ApiResponseUtils.ok(result);
    }
}
