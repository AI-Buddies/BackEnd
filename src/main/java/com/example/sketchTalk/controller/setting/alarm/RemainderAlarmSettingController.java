package com.example.sketchTalk.controller.setting.alarm;

import com.example.sketchTalk.service.setting.alarm.RemainderAlarmSettingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RemainderAlarmSettingController {
    private final RemainderAlarmSettingService remainderAlarmSettingService;

    public RemainderAlarmSettingController(RemainderAlarmSettingService remainderAlarmSettingService) {
        this.remainderAlarmSettingService = remainderAlarmSettingService;
    }

    // TODO: remainder, Lookback 둘을 한 번에 나타낼지 상의해보기!
}
