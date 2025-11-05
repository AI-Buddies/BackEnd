package com.example.sketchTalk.repository.achievement;

import com.example.sketchTalk.model.entity.achievement.UserCategory;
import com.example.sketchTalk.model.entity.achievement.UserCategoryKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCategoryRepository extends JpaRepository<UserCategory, UserCategoryKey> {
}
