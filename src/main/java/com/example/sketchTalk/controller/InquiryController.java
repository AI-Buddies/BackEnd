package com.example.sketchTalk.controller;

import com.example.sketchTalk.dto.setting.in.SendInquiryReq;
import com.example.sketchTalk.dto.setting.out.SendInquiryRes;
import com.example.sketchTalk.service.InquiryService;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<SendInquiryRes> sendInquiry(@RequestBody SendInquiryReq req) {
        SendInquiryRes sendInquiryRes = inquiryService.sendInquiry(req);

        return ResponseEntity.ok(sendInquiryRes);
    }
}
