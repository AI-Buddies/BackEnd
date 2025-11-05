package com.example.sketchTalk.service;

import com.example.sketchTalk._core.error.CustomException;

import com.example.sketchTalk.dto.category.out.AchievedResultRes;
import com.example.sketchTalk.dto.category.out.CategoryCompletionRes;
import com.example.sketchTalk.dto.comment.in.SaveCommentReq;
import com.example.sketchTalk.dto.comment.out.ReqContentRes;
import com.example.sketchTalk.dto.comment.out.SaveCommentRes;
import com.example.sketchTalk.dto.diary.in.ModifyDiaryReq;
import com.example.sketchTalk.dto.diary.in.SaveDiaryReq;
import com.example.sketchTalk.dto.diary.out.*;
import com.example.sketchTalk.dto.diary.out.SaveDiaryRes;
import com.example.sketchTalk.dto.subcategory.out.SubCategoryNamesDTO;
import com.example.sketchTalk.exception.diary.DiaryExceptions;
import com.example.sketchTalk.model.entity.Diary;
import com.example.sketchTalk.repository.DiaryRepository;
import com.example.sketchTalk.repository.achievement.SubCategoryRepository;
import com.example.sketchTalk.security.jwt.JwtUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final SubCategoryService subCategoryService;
    private final CategoryService categoryService;
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
    private AchievedResultRes checkAchievement(long userId, String diaryContent) {
        List<SubCategoryNamesDTO> subCategories = subCategoryService.findAllSubCategories();//모든 서브카테고리 조회
        List<SubCategoryNamesDTO> achievedSubs = findSubs(diaryContent, subCategories);//일기 내 검색
        List<CategoryCompletionRes> achievedCategories = List.of();
        if(!achievedSubs.isEmpty()) {//달성한 도전과제가 있을 경우
            List<Long> categoryIds = subCategoryService.updateSubCategory(achievedSubs);//각각의 USER_SUB를 저장 및 확인할 카테고리 Id 저장
            achievedCategories = categoryService.updateCategory(userId, categoryIds);//카테고리 전체의 달성 여부를 확인 및 처리
        }
        return new AchievedResultRes(achievedSubs, achievedCategories);
    }

    private List<SubCategoryNamesDTO> findSubs(String diaryContent, List<SubCategoryNamesDTO> subs) {
        //문자열 내 검색
        return subs.stream()
                .filter(sub->diaryContent.contains(sub.name()))
                .map(sub -> new SubCategoryNamesDTO(sub.subId(), sub.category(), sub.name()))
                .collect(Collectors.toList());
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
