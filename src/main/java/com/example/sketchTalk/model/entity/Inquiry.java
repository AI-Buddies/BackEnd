package com.example.sketchTalk.model.entity;


import com.example.sketchTalk.dto.setting.in.SendInquiryReq;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "inquiry")
public class Inquiry {
    @Id
    @Column(name = "inquiry_id", nullable = false)
    private Long inquiryId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "is_answered", nullable = false)
    private Boolean isAnswered = false;

    public Inquiry(SendInquiryReq sendInquiryReq) {
        this.userId = sendInquiryReq.userId();
        this.title = sendInquiryReq.title();
        this.content = sendInquiryReq.content();
    }
}
