package com.example.sketchTalk.controller.setting;

import com.example.sketchTalk._core.common.ApiResponse;
import com.example.sketchTalk.dto.setting.in.GetSettingReq;
import com.example.sketchTalk.dto.setting.in.SetDefaultSettingReq;
import com.example.sketchTalk.dto.setting.out.GetProfileRes;
import com.example.sketchTalk.dto.setting.out.SetDefaultSettingRes;
import com.example.sketchTalk.service.setting.DefaultSettingService;
import org.springframework.http.HttpStatus;
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
    public ApiResponse<GetProfileRes> getUserInformation(@RequestBody GetSettingReq req) {
        GetProfileRes result = defaultSettingService.getUserInformation(req);

        return ApiResponse.onSuccess(HttpStatus.OK, result);
    }

    @PatchMapping("/setting")
    public ApiResponse<SetDefaultSettingRes> SetDefaultAlarm(@RequestBody SetDefaultSettingReq req) {
        SetDefaultSettingRes result = defaultSettingService.SetDefaultAlarm(req);

        return ApiResponse.onSuccess(HttpStatus.OK, result);
    }
}