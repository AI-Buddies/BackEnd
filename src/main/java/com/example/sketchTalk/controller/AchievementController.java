package com.example.sketchTalk.controller;

import com.example.sketchTalk._core.common.ApiResponse;
import com.example.sketchTalk.dto.category.out.CategoryDetailRes;
import com.example.sketchTalk.dto.category.out.CategoryListRes;
import com.example.sketchTalk.dto.chat.in.ChatReq;
import com.example.sketchTalk.dto.webClient.in.ChatDataBody;
import com.example.sketchTalk.service.CategoryService;
import com.example.sketchTalk.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/achievement")
@RequiredArgsConstructor
public class AchievementController {
    private final CategoryService categoryService;

    @GetMapping("")
    public ApiResponse<List<CategoryListRes>> getAcheivementList(
            @AuthenticationPrincipal Long userId,
            @RequestParam(defaultValue = "all") String status
    ) {
        List<CategoryListRes> res = categoryService.getAcheivementList(userId, status);
        return ApiResponse.onSuccess(HttpStatus.CREATED, res);
    }

    @GetMapping("/{categoryId}")
    public ApiResponse<CategoryDetailRes> getAchievementDetail(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long categoryId,
            @RequestParam(defaultValue = "all") String status
    ) {
        CategoryDetailRes res = categoryService.getAchievementDetail(userId, categoryId, status);
        return ApiResponse.onSuccess(HttpStatus.OK, res);
    }
}
