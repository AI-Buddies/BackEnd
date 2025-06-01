package com.example.sketchTalk.controller;

import com.example.sketchTalk.dto.diary.in.SaveDiaryReq;
import com.example.sketchTalk.dto.diary.out.SaveDiaryRes;
import com.example.sketchTalk.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/diary")
@RequiredArgsConstructor
public class DiaryController {
    private final DiaryService diaryService;

    @PostMapping("/")
    public ResponseEntity<SaveDiaryRes> saveDiary(@RequestBody SaveDiaryReq req) {
        SaveDiaryRes saveDiaryRes = diaryService.putDiary(req);
        return ResponseEntity.ok(saveDiaryRes);
    }
}
