package com.example.sketchTalk.service;

import com.example.sketchTalk.dto.in.DiaryInDTO;
import com.example.sketchTalk.dto.out.DiaryOutDTO;
import com.example.sketchTalk.repository.DiaryRepository;
import com.example.sketchTalk.model.entity.Diary;
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
