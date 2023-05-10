package by.itacademy.profiler.usecasses.dto;

import lombok.Builder;

@Builder(setterPrefix = "with")
public record LanguageResponseDto(
        Long id,
        String name,
        Boolean isTop
) {
}
