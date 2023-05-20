package by.itacademy.profiler.usecasses.dto;

import lombok.Builder;

@Builder(setterPrefix = "with")
public record SphereResponseDto(Long id, String name) {
}
