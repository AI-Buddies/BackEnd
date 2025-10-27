package com.example.sketchTalk.controller;

import com.example.sketchTalk._core.common.ApiResponse;
import com.example.sketchTalk.dto.diary.in.ModifyDiaryReq;
import com.example.sketchTalk.dto.diary.in.SaveDiaryReq;
import com.example.sketchTalk.dto.diary.out.ModifyDiaryRes;
import com.example.sketchTalk.dto.diary.out.SaveDiaryRes;
import com.example.sketchTalk.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/diary")
@RequiredArgsConstructor
public class DiaryController {
    private final DiaryService diaryService;

    @PostMapping("/save")
    public ApiResponse<SaveDiaryRes> saveDiary(@RequestBody SaveDiaryReq req) {
        SaveDiaryRes saveDiaryRes = diaryService.putDiary(req);
        return ApiResponse.onSuccess(HttpStatus.CREATED, saveDiaryRes);
    }

    @PutMapping("/")
    public ApiResponse<ModifyDiaryRes> modifyDiary(@RequestBody ModifyDiaryReq req) {
        ModifyDiaryRes modifyDiaryRes = diaryService.changeDiary(req);
        return ApiResponse.onSuccess(HttpStatus.OK, modifyDiaryRes);
    }
}
