package com.example.sketchTalk.service;

import com.example.sketchTalk.dto.setting.in.SendInquiryReq;
import com.example.sketchTalk.dto.setting.out.SendInquiryRes;
import com.example.sketchTalk.model.entity.Inquiry;
import com.example.sketchTalk.repository.InquiryRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class InquiryService {
    private final InquiryRepository inquiryRepository;

    public InquiryService(InquiryRepository inquiryRepository) {
        this.inquiryRepository = inquiryRepository;
    }

    @Transactional
    public SendInquiryRes sendInquiry(SendInquiryReq req) {
        Inquiry inquiry = new Inquiry(req);
        inquiryRepository.save(inquiry);

        SendInquiryRes sendInquiryRes = SendInquiryRes.builder()
                .userId(inquiry.getUserId())
                .title(inquiry.getTitle())
                .content(inquiry.getContent())
                .build();

        return sendInquiryRes;
    }
}