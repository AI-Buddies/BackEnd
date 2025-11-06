package com.example.sketchTalk.dto.subcategory.out;

import com.example.sketchTalk.model.entity.achievement.Category;

public record SubCategoryNamesDTO(
        Long subId,
        Category category,
        String name
) {
}
