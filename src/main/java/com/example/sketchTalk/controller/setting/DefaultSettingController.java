package com.example.sketchTalk.controller.setting;

import com.example.sketchTalk.dto.setting.out.DefaultSettingRes;
import com.example.sketchTalk.service.setting.DefaultSettingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultSettingController {
    private final DefaultSettingService defaultSettingService;

    public DefaultSettingController(DefaultSettingService defaultSettingService) {
        this.defaultSettingService = defaultSettingService;
    }

    @GetMapping("/setting")
    public ResponseEntity<DefaultSettingRes> getUserInformation() {
        DefaultSettingRes defaultSettingRes = defaultSettingService.getUserInformation();

        return ResponseEntity.ok(defaultSettingRes);
    }
}