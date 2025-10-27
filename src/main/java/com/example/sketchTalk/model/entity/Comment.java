package com.example.sketchTalk.model.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(name="diary_id")
    private Long diaryId;

    @Column(name="content")
    private String content;

    public Comment(Long diaryId, String content) {
        this.diaryId = diaryId;
        this.content = content;
    }
}
