package com.example.sketchTalk.service;

import com.example.sketchTalk.dto.diary.in.SaveDiaryReq;
import com.example.sketchTalk.dto.diary.out.SaveDiaryRes;
import com.example.sketchTalk.model.entity.Diary;
import com.example.sketchTalk.repository.DiaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryRepository diaryRepository;

    public SaveDiaryRes putDiary(SaveDiaryReq saveDiaryReq) {
        Diary diary = new Diary(saveDiaryReq);
        Diary savedDiary = diaryRepository.save(diary);
        return new SaveDiaryRes(savedDiary.getDiaryId());
    }
}
