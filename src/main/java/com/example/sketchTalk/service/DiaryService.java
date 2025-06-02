package com.example.sketchTalk.service;

import com.example.sketchTalk.dto.diary.in.ModifyDiaryReq;
import com.example.sketchTalk.dto.diary.in.SaveDiaryReq;
import com.example.sketchTalk.dto.diary.out.ModifyDiaryRes;
import com.example.sketchTalk.dto.diary.out.SaveDiaryRes;
import com.example.sketchTalk.exception.diary.DiaryException;
import com.example.sketchTalk.model.Emotion;
import com.example.sketchTalk.model.entity.Diary;
import com.example.sketchTalk.repository.DiaryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryRepository diaryRepository;

    public SaveDiaryRes putDiary(SaveDiaryReq saveDiaryReq) {
        Diary diary = new Diary(saveDiaryReq);
        Diary savedDiary = diaryRepository.save(diary);
        return new SaveDiaryRes(savedDiary.getDiaryId());
    }

    @Transactional
    public ModifyDiaryRes changeDiary(ModifyDiaryReq modifyDiaryReq) {
        //다이어리 찾기
        Diary diary = diaryRepository.findById(modifyDiaryReq.diaryId()).orElseThrow(()->new DiaryException(modifyDiaryReq.diaryId()));
        diary.rewriteDiary(modifyDiaryReq.title(), modifyDiaryReq.content(), modifyDiaryReq.emotion());
        diaryRepository.save(diary);

        ModifyDiaryRes modifyDiaryRes = ModifyDiaryRes.builder()
                .diaryId(diary.getDiaryId())
                .userId(diary.getUserId())
                .title(diary.getTitle())
                .content(diary.getContent())
                .date(diary.getDate())
                .emotion(diary.getEmotion())
                .build();
        return modifyDiaryRes;
    }
}
