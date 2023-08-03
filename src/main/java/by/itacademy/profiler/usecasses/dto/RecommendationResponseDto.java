package by.itacademy.profiler.usecasses.dto;

import lombok.Builder;

import java.io.Serializable;

@Builder(setterPrefix = "with")
public record RecommendationResponseDto(
        Long id, String fullName,
        String company, String position,
        Long phoneCodeId, Integer phoneCode,
        String phoneNumber, String email,
        String linkedIn, String telegram,
        String viber, String whatsApp,
        String recommendations
) implements Serializable {
}
