package com.example.sketchTalk.controller.setting;

import com.example.sketchTalk._core.common.ApiResponse;
import com.example.sketchTalk._core.common.ApiResponseUtils;
import com.example.sketchTalk.dto.setting.in.GetSettingReq;
import com.example.sketchTalk.dto.setting.in.SetAudioSettingReq;
import com.example.sketchTalk.dto.setting.out.GetAudioSettingRes;
import com.example.sketchTalk.dto.setting.out.SetAudioSettingRes;
import com.example.sketchTalk.service.setting.AudioSettingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AudioSettingController {
    private final AudioSettingService audioSettingService;

    public AudioSettingController(AudioSettingService audioSettingService) {
        this.audioSettingService = audioSettingService;
    }

    @GetMapping("/setting/tts")
    public ResponseEntity<ApiResponse<GetAudioSettingRes>> getAudioSetting(@RequestBody GetSettingReq req) {
        GetAudioSettingRes result = audioSettingService.getAudioSetting(req);

        return ApiResponseUtils.ok(result);
    }

    @PatchMapping("/setting/tts")
    public ResponseEntity<ApiResponse<SetAudioSettingRes>> setAudioSetting(@RequestBody SetAudioSettingReq req) {
        SetAudioSettingRes result = audioSettingService.setAudioSetting(req);

        return ApiResponseUtils.ok(result);
    }
}
