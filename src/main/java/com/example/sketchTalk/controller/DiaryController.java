package com.example.sketchTalk.controller;

import com.example.sketchTalk.dto.diary.in.ModifyDiaryReq;
import com.example.sketchTalk.dto.diary.in.SaveDiaryReq;
import com.example.sketchTalk.dto.diary.out.ModifyDiaryRes;
import com.example.sketchTalk.dto.diary.out.SaveDiaryRes;
import com.example.sketchTalk.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/")
    public ResponseEntity<ModifyDiaryRes> modifyDiary(@RequestBody ModifyDiaryReq req) {
        ModifyDiaryRes modifyDiaryRes = diaryService.changeDiary(req);
        return ResponseEntity.ok(modifyDiaryRes);
    }
}
