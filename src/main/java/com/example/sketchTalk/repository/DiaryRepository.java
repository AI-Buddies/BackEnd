package com.example.sketchTalk.repository;

import com.example.sketchTalk.dto.diary.out.GetCalanderDiraryRes;
import com.example.sketchTalk.model.entity.Diary;
import com.example.sketchTalk.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
    List<GetCalanderDiraryRes> findAllByUserIdAndDateBetween(User user, LocalDate dateAfter, LocalDate dateBefore);
    List<Diary> findAllByUserIdAndDateBetweenOrderByDateAsc(User user, LocalDate start, LocalDate end);

    Optional<Diary> findByUserIdAndDiaryId(User user, Long diaryId);
}
