package com.example.sketchTalk.controller;

import com.example.sketchTalk._core.common.ApiResponse;
import com.example.sketchTalk.dto.diary.in.ModifyDiaryReq;
import com.example.sketchTalk.dto.diary.in.SaveDiaryReq;
import com.example.sketchTalk.dto.diary.out.*;
import com.example.sketchTalk.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/diary")
@RequiredArgsConstructor
public class DiaryController {
    private final DiaryService diaryService;

    @PostMapping("/save")
    public ApiResponse<SaveDiaryRes> saveDiary(@AuthenticationPrincipal Long userId, @RequestBody SaveDiaryReq req) {
        SaveDiaryRes saveDiaryRes = diaryService.putDiary(userId, req);
        return ApiResponse.onSuccess(HttpStatus.CREATED, saveDiaryRes);
    }

    @PutMapping("/")
    public ApiResponse<ModifyDiaryRes> modifyDiary(@RequestBody ModifyDiaryReq req) {
        ModifyDiaryRes modifyDiaryRes = diaryService.changeDiary(req);
        return ApiResponse.onSuccess(HttpStatus.OK, modifyDiaryRes);
    }

    @GetMapping("/cal")
    public ApiResponse<List<GetCalanderDiraryRes>> getCalander(
            @RequestParam int year,
            @RequestParam int month,
            @AuthenticationPrincipal Long userId
    ) {
        List<GetCalanderDiraryRes> getCalanderDiraryRes = diaryService.getCalanderByMonth(year, month, userId);
        return ApiResponse.onSuccess(HttpStatus.OK, getCalanderDiraryRes);
    }

    @GetMapping("/list")
    public ApiResponse<List<GetDiaryPreviewRes>> getList(
            @RequestParam int year,
            @RequestParam int month,
            @AuthenticationPrincipal Long userId
    ) {
        List<GetDiaryPreviewRes> getDiaryListRes = diaryService.getDiaryListByMonth(year, month, userId);
        return ApiResponse.onSuccess(HttpStatus.OK, getDiaryListRes);
    }

    @GetMapping("/{id}/preview")
    public ApiResponse<GetDiaryPreviewRes> getDiaryPreview(@PathVariable Long id, @AuthenticationPrincipal Long userId) {
        GetDiaryPreviewRes getDiaryPreviewRes = diaryService.getDiaryPreviewById(id);
        return ApiResponse.onSuccess(HttpStatus.OK, getDiaryPreviewRes);
    }

    @GetMapping("/{id}")
    public ApiResponse<GetDiaryDetailRes> getDiaryDetail(@PathVariable Long id, @AuthenticationPrincipal Long userId) {
        GetDiaryDetailRes getDiaryDetailRes = diaryService.getDiaryDetailById(id);
        return ApiResponse.onSuccess(HttpStatus.OK, getDiaryDetailRes);
    }
}
