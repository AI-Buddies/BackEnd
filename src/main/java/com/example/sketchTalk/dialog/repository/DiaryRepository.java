package com.example.sketchTalk.dialog.repository;

import com.example.sketchTalk.model.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
}
