package com.example.sketchTalk.controller.setting.alarm;

import com.example.sketchTalk.dto.setting.in.GetSettingReq;
import com.example.sketchTalk.dto.setting.in.SetRemainderAlarmSettingReq;
import com.example.sketchTalk.dto.setting.out.GetRemainderAlarmSettingRes;
import com.example.sketchTalk.dto.setting.out.SettingRes;
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
    public ResponseEntity<GetRemainderAlarmSettingRes> getRemainderAlarmSetting(@RequestBody GetSettingReq req) {
        GetRemainderAlarmSettingRes getRemainderAlarmSettingRes = remainderAlarmSettingService.getRemainderAlarmSetting(req);

        return ResponseEntity.ok(getRemainderAlarmSettingRes);
    }

    @PatchMapping("/setting/notify/remainder")
    public ResponseEntity<SettingRes> setRemainderAlarmSetting(@RequestBody SetRemainderAlarmSettingReq req) {
        SettingRes settingRes = remainderAlarmSettingService.setRemainderAlarmSetting(req);

        return ResponseEntity.ok(settingRes);
    }
}
