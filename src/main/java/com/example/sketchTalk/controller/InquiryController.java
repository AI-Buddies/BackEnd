package com.example.sketchTalk.controller;

import com.example.sketchTalk._core.common.ApiResponse;
import com.example.sketchTalk.dto.setting.in.SendInquiryReq;
import com.example.sketchTalk.dto.setting.out.SendInquiryRes;
import com.example.sketchTalk.service.InquiryService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InquiryController {
    private final InquiryService inquiryService;

    public InquiryController(InquiryService inquiryService) {
        this.inquiryService = inquiryService;
    }

    @PostMapping("/setting/inquiry")
    public ApiResponse<SendInquiryRes> sendInquiry(
            @AuthenticationPrincipal Long userId,
            @RequestBody SendInquiryReq req
    ) {
        SendInquiryRes result = inquiryService.sendInquiry(userId, req);

        return ApiResponse.onSuccess(HttpStatus.OK, result);
    }
}