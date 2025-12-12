package com.example.sketchTalk.model.entity.setting.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class VoiceTypeConverter implements AttributeConverter<VoiceType, String> {

    // 1. 엔티티 (VoiceType) -> DB 컬럼 (String)으로 변환
    @Override
    public String convertToDatabaseColumn(VoiceType voiceType) {
        if (voiceType == null) {
            return null;
        }
        return voiceType.getValue();
    }

    // 2. DB 컬럼 (String) -> 엔티티 (VoiceType)으로 변환
    @Override
    public VoiceType convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }

        // DB 문자열 값과 일치하는 ENUM 상수를 찾습니다.
        return Stream.of(VoiceType.values())
                .filter(v -> v.getValue().equals(dbData))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown voice type value: " + dbData));
    }
}
