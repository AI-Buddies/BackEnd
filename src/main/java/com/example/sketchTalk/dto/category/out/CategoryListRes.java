package com.example.sketchTalk.dto.category.out;

public record CategoryListRes(
        long categoryId,
        String categoryName,
        int completed,
        int total,
        boolean isCompleted
) {}
