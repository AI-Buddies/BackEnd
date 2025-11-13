package com.example.sketchTalk.repository.achievement;

import com.example.sketchTalk.model.entity.achievement.UserSub;
import com.example.sketchTalk.model.entity.achievement.UserSubKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserSubRepository extends JpaRepository<UserSub, UserSubKey> {
    boolean existsByUserSubKey_UserIdAndUserSubKey_SubIdAndIsClearTrue(Long userId, Long subId);
    int countByUserSubKey_UserIdAndUserSubKey_SubIdInAndIsClearTrue(Long userId, List<Long> subIds);
}
