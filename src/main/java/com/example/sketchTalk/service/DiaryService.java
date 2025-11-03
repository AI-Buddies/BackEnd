package com.example.sketchTalk.service;

import com.example.sketchTalk._core.error.CustomException;
import com.example.sketchTalk.dto.diary.in.ModifyDiaryReq;
import com.example.sketchTalk.dto.diary.in.SaveDiaryReq;
import com.example.sketchTalk.dto.diary.out.*;
import com.example.sketchTalk.exception.diary.DiaryExceptions;
import com.example.sketchTalk.model.entity.Diary;
import com.example.sketchTalk.repository.DiaryRepository;
import com.example.sketchTalk.security.jwt.JwtUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final JwtUtils jwtUtils;
//    private final CommentService commentService;

    public SaveDiaryRes putDiary(SaveDiaryReq saveDiaryReq) {
        Diary diary = new Diary(saveDiaryReq);
        Diary savedDiary = diaryRepository.save(diary);

//        ReqContentRes writtenComment = commentService.reqComment(diary.getContent());
//        SaveCommentReq saveCommentReq = new SaveCommentReq(savedDiary.getDiaryId(), writtenComment.content());
//        SaveCommentRes saveCommentRes = commentService.putComment(saveCommentReq);

        return new SaveDiaryRes(savedDiary.getDiaryId(), savedDiary.getTitle(), savedDiary.getContent(), savedDiary.getEmotion());
    }

    @Transactional
    public ModifyDiaryRes changeDiary(ModifyDiaryReq modifyDiaryReq) {
        //다이어리 찾기
        Diary diary = diaryRepository.findById(modifyDiaryReq.diaryId()).orElseThrow(()->new CustomException(DiaryExceptions.DIARY_NOT_FOUND, modifyDiaryReq.diaryId()));
        diary.rewriteDiary(modifyDiaryReq.title(), modifyDiaryReq.content(), modifyDiaryReq.emotion());
        diaryRepository.save(diary);

        ModifyDiaryRes modifyDiaryRes = ModifyDiaryRes.builder()
                .diaryId(diary.getDiaryId())
                .date(diary.getDate())
                .title(diary.getTitle())
                .emotion(diary.getEmotion())
                .content(diary.getContent())
                .build();
        return modifyDiaryRes;
    }

    /*
    단어 목록을 가져와서 HastSet을 만들고
    문자열 내 검색, 존재하는 단어 탐색하여 객체 배열로 반환
    UserSub에 추가를 하고
    전체 카테고리를 달성헀는지 확인
     */
    private void checkAchievement(String diaryContent) {
        //여기서 각 함수를 호출해서 실행
    }

    private List<String> getCategoryNames() {//객체 배열로 반환
        //단어 목록 가져오기
        return null;
    }

    private List<Long> findSubs(String diary, List<String> subs) {
        //문자열 내 검색
        return null;
    }

    private void updateCategory(List<Long> subs) {

    }

    private void updateAchievement() {

    }

    public List<GetCalanderDiraryRes> getCalanderByMonth(int year, int month, Long userId) {
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

        return diaryRepository.findAllByUserIdAndDateBetween(userId, start, end);
    }

    public List<GetDiaryPreviewRes> getDiaryListByMonth(int year, int month, Long userId) {
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

        return diaryRepository.findAllByUserIdAndDateBetweenOrderByDateAsc(userId, start, end)
                .stream()
                .map(d -> {
                    String imageUrl = d.getImage() != null ? d.getImage().getUrl() : null;
                    return new GetDiaryPreviewRes(
                            d.getDiaryId(),
                            d.getDate(),
                            d.getEmotion(),
                            d.getTitle(),
                            imageUrl
                    );
                })
                .toList();
    }

    public GetDiaryPreviewRes getDiaryPreviewById(Long id, Long userId) {
        Diary diary = diaryRepository.findByUserIdAndDiaryId(userId, id).orElseThrow(() -> new CustomException(DiaryExceptions.DIARY_NOT_FOUND, id));

        String imageUrl = diary.getImage() != null ? diary.getImage().getUrl() : null;
        return new GetDiaryPreviewRes(
                diary.getDiaryId(),
                diary.getDate(),
                diary.getEmotion(),
                diary.getTitle(),
                imageUrl
        );
    }

    public GetDiaryDetailRes getDiaryDetailById(Long id, Long userId) {
        Diary diary = diaryRepository.findByUserIdAndDiaryId(userId, id).orElseThrow(() -> new CustomException(DiaryExceptions.DIARY_NOT_FOUND, id));

        String imageUrl = diary.getImage() != null ? diary.getImage().getUrl() : null;
        String comment = diary.getComment() != null ? diary.getComment().getContent() : null;
        return new GetDiaryDetailRes(
                diary.getDiaryId(),
                diary.getDate(),
                diary.getEmotion(),
                diary.getTitle(),
                diary.getContent(),
                imageUrl,
                comment
        );
    }
}
