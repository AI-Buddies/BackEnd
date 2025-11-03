package com.example.sketchTalk.model.entity;

import com.example.sketchTalk.model.Style;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="image_id")
    private Long imageId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id", nullable = false)
    private Diary diary;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Style style;

    @Column(nullable = false)
    private String url;
}
