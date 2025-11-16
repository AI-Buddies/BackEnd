package com.example.sketchTalk.repository;

import com.example.sketchTalk.dto.diary.out.GetCalanderDiraryRes;
import com.example.sketchTalk.model.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
    List<GetCalanderDiraryRes> findAllByUserIdAndDateBetween(Long userId, LocalDate dateAfter, LocalDate dateBefore);
    List<Diary> findAllByUserIdAndDateBetweenOrderByDateAsc(Long userId, LocalDate start, LocalDate end);
    List<Diary> findAllByUserIdAndDateIs(Long userId, LocalDate date);
    Optional<Diary> findByUserIdAndDiaryId(Long userId, Long diaryId);
}
