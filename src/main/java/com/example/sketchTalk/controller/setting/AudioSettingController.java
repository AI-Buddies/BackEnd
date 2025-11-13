package com.example.sketchTalk.controller.setting;

import com.example.sketchTalk._core.common.ApiResponse;
import com.example.sketchTalk.dto.setting.in.SetAudioSettingReq;
import com.example.sketchTalk.dto.setting.out.GetAudioSettingRes;
import com.example.sketchTalk.dto.setting.out.SetAudioSettingRes;
import com.example.sketchTalk.service.setting.AudioSettingService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
public class AudioSettingController {
    private final AudioSettingService audioSettingService;

    public AudioSettingController(AudioSettingService audioSettingService) {
        this.audioSettingService = audioSettingService;
    }

    @GetMapping("/setting/tts")
    public ApiResponse<GetAudioSettingRes> getAudioSetting(@AuthenticationPrincipal Long userId) {
        GetAudioSettingRes result = audioSettingService.getAudioSetting(userId);

        return ApiResponse.onSuccess(HttpStatus.OK, result);
    }

    @PutMapping("/setting/tts")
    public ApiResponse<SetAudioSettingRes> setAudioSetting(
            @AuthenticationPrincipal Long userId,
            @RequestBody SetAudioSettingReq req
    ) {
        SetAudioSettingRes result = audioSettingService.setAudioSetting(userId, req);

        return ApiResponse.onSuccess(HttpStatus.OK, result);
    }
}