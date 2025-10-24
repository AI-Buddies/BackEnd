package com.example.sketchTalk.repository.achievement;

import com.example.sketchTalk.model.entity.achievement.UserSub;
import com.example.sketchTalk.model.entity.achievement.UserSubKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSubRepository extends JpaRepository<UserSub, UserSubKey> {
}
