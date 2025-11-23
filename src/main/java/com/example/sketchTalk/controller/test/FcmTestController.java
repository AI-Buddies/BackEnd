package com.example.sketchTalk.controller.test;

import com.example.sketchTalk._core.common.ApiResponse;
import com.example.sketchTalk.dto.test.FcmTestReq;
import com.example.sketchTalk.service.FcmService;
import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class FcmTestController {
    private final FcmService fcmService;

    @PostMapping("/fcm")
    public ApiResponse<Void> test(@RequestBody FcmTestReq req) throws FirebaseMessagingException {
        fcmService.sendTestMessage(req.fcmToken());

        return ApiResponse.onSuccess(HttpStatus.OK, null);
    }

}
