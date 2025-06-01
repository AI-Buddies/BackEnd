package com.example.sketchTalk.model;

import com.example.sketchTalk.Emotion;
import com.example.sketchTalk.dialog.dto.in.DiaryInDTO;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
public class Diary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="diary_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    private String title;
    private String content;
    private Date date;
    private Enum<Emotion> emotion;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Diary(DiaryInDTO diaryInDTO) {
    }

    public Diary() {

    }
}
