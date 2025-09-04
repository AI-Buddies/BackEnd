package com.example.sketchTalk.service;

import com.example.sketchTalk.dto.setting.in.SendInquiryReq;
import com.example.sketchTalk.dto.setting.out.SendInquiryRes;
import com.example.sketchTalk.model.entity.Inquiry;
import com.example.sketchTalk.model.entity.User;
import com.example.sketchTalk.repository.InquiryRepository;
import com.example.sketchTalk.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class InquiryService {
    private final InquiryRepository inquiryRepository;

    public InquiryService(InquiryRepository inquiryRepository) {
        this.inquiryRepository = inquiryRepository;
    }

    public SendInquiryRes sendInquiry(SendInquiryReq req) {
        Inquiry inquiry = new Inquiry(req);
        inquiryRepository.save(inquiry);

        return new SendInquiryRes(true, "SEND_SUCCESS");
    }
}
