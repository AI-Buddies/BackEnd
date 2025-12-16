package com.example.sketchTalk.repository;

import com.example.sketchTalk.dto.diary.out.GetCalanderDiraryRes;
import com.example.sketchTalk.model.entity.Diary;
import com.example.sketchTalk.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
    List<GetCalanderDiraryRes> findAllByUserAndDateBetween(User user, LocalDate dateAfter, LocalDate dateBefore);
    List<Diary> findAllByUserAndDateBetweenOrderByDateAsc(User user, LocalDate start, LocalDate end);
    List<Diary> findAllByUser_UserIdAndDateIs(Long userId, LocalDate date);

    Optional<Diary> findByDiaryId(Long diaryId);

    void deleteAllByUser_UserId(Long userId);
}
