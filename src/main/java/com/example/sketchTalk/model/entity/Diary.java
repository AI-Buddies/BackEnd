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

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

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
        this.user = saveDiaryReq.user();
        this.title = saveDiaryReq.title();
        this.content = saveDiaryReq.content();
        this.date = saveDiaryReq.date();
        this.emotion = saveDiaryReq.emotion();
    }


}
