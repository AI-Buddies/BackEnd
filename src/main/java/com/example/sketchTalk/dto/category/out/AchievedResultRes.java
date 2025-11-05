package com.example.sketchTalk.dto.category.out;

import com.example.sketchTalk.dto.subcategory.out.SubCategoryNamesDTO;

import java.util.List;

public record AchievedResultRes(
        List<SubCategoryNamesDTO> achievedSubs,
        List<CategoryCompletionRes> achievedCategory
) {
}
