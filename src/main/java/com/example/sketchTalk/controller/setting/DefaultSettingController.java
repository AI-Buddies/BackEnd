package com.example.sketchTalk.controller.setting;

import com.example.sketchTalk._core.common.ApiResponse;
import com.example.sketchTalk._core.common.ApiResponseUtils;
import com.example.sketchTalk.dto.setting.in.GetSettingReq;
import com.example.sketchTalk.dto.setting.in.SetDefaultSettingReq;
import com.example.sketchTalk.dto.setting.out.GetProfileRes;
import com.example.sketchTalk.dto.setting.out.SetDefaultSettingRes;
import com.example.sketchTalk.service.setting.DefaultSettingService;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ApiResponse<GetProfileRes>> getUserInformation(@RequestBody GetSettingReq req) {
        GetProfileRes result = defaultSettingService.getUserInformation(req);

        return ApiResponseUtils.ok(result);
    }

    @PatchMapping("/setting")
    public ResponseEntity<ApiResponse<SetDefaultSettingRes>> SetDefaultAlarm(@RequestBody SetDefaultSettingReq req) {
        SetDefaultSettingRes result = defaultSettingService.SetDefaultAlarm(req);

        return ApiResponseUtils.ok(result);
    }
}