package com.example.sketchTalk.dialog.dto.in;

import com.example.sketchTalk.Emotion;
import com.example.sketchTalk.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@AllArgsConstructor
public class DiaryInDTO {
    private User user;

    private String title;
    private String content;
    private Date date;
    private Enum<Emotion> emotion;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}