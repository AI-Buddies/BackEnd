package com.example.sketchTalk.model.entity.setting.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.stream.Stream;

public enum VoiceType {
    KO_KR_SEOHYEON_NEURAL("ko-KR-SeoHyeonNeural"),
    KO_KR_GOOKMIN_NEURAL("ko-KR-GookMinNeural"),
    KO_KR_SUNHI_NEURAL("ko-KR-SunHiNeural");

    private final String value;

    VoiceType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @JsonCreator
    public static VoiceType fromValue(String value) {
        if (value == null) {
            return null;
        }
        // 들어온 문자열(value)과 ENUM 상수의 'value' 필드가 일치하는 상수를 찾습니다.
        return Stream.of(VoiceType.values())
                .filter(v -> v.getValue().equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid VoiceType value: " + value));
    }
}
