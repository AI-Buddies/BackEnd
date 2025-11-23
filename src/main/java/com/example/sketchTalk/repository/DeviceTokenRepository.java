package com.example.sketchTalk.repository;

import com.example.sketchTalk.model.entity.DeviceToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeviceTokenRepository extends JpaRepository<DeviceToken, Long> {
    Optional<DeviceToken> findByUserIdAndDeviceIdentifier(Long userId, String deviceIdentifier);
    List<DeviceToken> findAllByUserIdAndRevokedFalse(Long userId);

    void deleteAllByUserId(Long userId);
}
