package com.example.sketchTalk.dto.category.out;

import java.util.List;

public record CategoryDetailRes(
        long categoryId,
        String categoryName,
        int completed,
        int total,
        boolean isCompleted,
        List<SubCategoryRes> subCategories
) {}