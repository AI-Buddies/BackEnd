package com.example.sketchTalk.dto.setting.out;

import com.example.sketchTalk.model.entity.setting.FrequentlyAskedQuestion;

import java.util.List;

public record GetFAQListRes(
    List<FrequentlyAskedQuestion> list
) {
}