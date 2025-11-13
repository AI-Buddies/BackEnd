package com.example.sketchTalk.model.entity;

import com.example.sketchTalk.dto.diary.in.SaveDiaryReq;
import com.example.sketchTalk.model.Emotion;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "diary")
public class Diary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="diary_id")
    private Long diaryId;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @Column(nullable=false)
    private String title;

    @Column(nullable=false)
    private String content;

    @Column(nullable=false)
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private Emotion emotion;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToOne(mappedBy = "diary", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = true)
    private Image image;

    @OneToOne(mappedBy = "diary", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = true)
    private Comment comment;

    public Diary(User user, SaveDiaryReq saveDiaryReq) {
        this.user = user;
        this.title = saveDiaryReq.title();
        this.content = saveDiaryReq.content();
        this.date = saveDiaryReq.date();
        this.emotion = saveDiaryReq.emotion();
        this.createdAt = LocalDateTime.now();
    }

    public void rewriteDiary(String title, String content, Emotion emotion) {
        this.title = title;
        this.content = content;
        this.emotion = emotion;
        this.updatedAt = LocalDateTime.now();
    }


}
