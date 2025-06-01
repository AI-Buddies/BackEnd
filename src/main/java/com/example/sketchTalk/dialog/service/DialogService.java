package com.example.sketchTalk.dialog.service;

import com.example.sketchTalk.dialog.dto.in.DiaryInDTO;
import com.example.sketchTalk.dialog.dto.out.DiaryOutDTO;
import com.example.sketchTalk.dialog.repository.DiaryRepository;
import com.example.sketchTalk.model.Diary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DialogService {

    private final DiaryRepository diaryRepository;

    public DiaryOutDTO saveDiary(DiaryInDTO diaryInDTO) {
        diaryRepository.save(new Diary(diaryInDTO));
        return new DiaryOutDTO("message");
    }
}
