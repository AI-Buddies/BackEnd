package com.example.sketchTalk.model.entity;

import com.example.sketchTalk.dto.diary.in.SaveDiaryReq;
import com.example.sketchTalk.model.Emotion;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "diary")
public class Diary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="diary_id")
    private Long diaryId;

    //@ManyToOne <- 추후에 User로 변경
    @JoinColumn(name="user_id")
    private Long userId;

    @Column(nullable=false)
    private String title;

    @Column(nullable=false)
    private String content;

    @Column(nullable=false)
    private Date date;

    @Column(nullable=false)
    private Enum<Emotion> emotion;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public Diary(SaveDiaryReq saveDiaryReq) {
        this.userId = saveDiaryReq.userId();//추후에 User로 변경
        this.title = saveDiaryReq.title();
        this.content = saveDiaryReq.content();
        this.date = saveDiaryReq.date();
        this.emotion = saveDiaryReq.emotion();
    }

    public void rewriteDiary(String title, String content, Emotion emotion) {
        this.title = title;
        this.content = content;
        this.emotion = emotion;
    }


}
