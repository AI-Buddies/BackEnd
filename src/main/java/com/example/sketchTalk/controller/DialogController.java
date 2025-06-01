package com.example.sketchTalk.controller;

import com.example.sketchTalk.dto.out.DiaryOutDTO;
import com.example.sketchTalk.service.DialogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DialogController {
    private final DialogService dialogService;

    @PostMapping("/chat")
    public ResponseEntity<DiaryOutDTO> chat(@RequestBody DiaryInDTO diaryInDTO) {
        DiaryOutDTO diaryOutDTO = dialogService.saveDiary(diaryInDTO);
        return ResponseEntity.ok(diaryOutDTO);
    }
}