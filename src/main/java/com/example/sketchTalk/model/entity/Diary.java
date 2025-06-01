package com.example.sketchTalk.model.entity;

import com.example.sketchTalk.model.Emotion;
import com.example.sketchTalk.dto.in.DiaryInDTO;
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


    public Diary(DiaryInDTO diaryInDTO) {
        this.user = diaryInDTO.getUser();
        this.title = diaryInDTO.getTitle();
        this.content = diaryInDTO.getContent();
        this.date = diaryInDTO.getDate();
        this.emotion = diaryInDTO.getEmotion();
    }


}
