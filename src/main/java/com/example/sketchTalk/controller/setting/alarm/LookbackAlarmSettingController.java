package com.example.sketchTalk.controller.setting.alarm;

import com.example.sketchTalk.dto.setting.in.GetSettingReq;
import com.example.sketchTalk.dto.setting.in.SetLookbackAlarmSettingReq;
import com.example.sketchTalk.dto.setting.out.GetLookbackAlarmSettingRes;
import com.example.sketchTalk.dto.setting.out.SetLookbackAlarmSettingRes;
import com.example.sketchTalk.service.setting.alarm.LookbackAlarmSettingService;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<GetLookbackAlarmSettingRes> getLookbackAlarmSetting(@RequestBody GetSettingReq req) {
        GetLookbackAlarmSettingRes getLookbackAlarmSettingRes = lookbackAlarmSettingService.getLookbackAlarmSetting(req);

        return ResponseEntity.ok(getLookbackAlarmSettingRes);
    }

    @PatchMapping("/setting/notify/lookback")
    public ResponseEntity<SetLookbackAlarmSettingRes> setLookbackAlarmSetting(@RequestBody SetLookbackAlarmSettingReq req) {
        SetLookbackAlarmSettingRes setLookbackAlarmSettingRes = lookbackAlarmSettingService.setLookbackAlarmSetting(req);

        return ResponseEntity.ok(setLookbackAlarmSettingRes);
    }
}
