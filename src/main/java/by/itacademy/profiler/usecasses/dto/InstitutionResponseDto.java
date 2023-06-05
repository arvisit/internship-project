package by.itacademy.profiler.usecasses.dto;

import lombok.Builder;

@Builder(setterPrefix = "with")
public record InstitutionResponseDto(
        Long id,
        String name
) {
}
