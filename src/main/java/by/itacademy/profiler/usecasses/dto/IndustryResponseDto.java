package by.itacademy.profiler.usecasses.dto;

import lombok.Builder;

@Builder(setterPrefix = "with")
public record IndustryResponseDto(Long id, String name) {
}
