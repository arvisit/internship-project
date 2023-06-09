package by.itacademy.profiler.usecasses.dto;

import java.util.List;

import lombok.Builder;

@Builder(setterPrefix = "with")
public record EducationResponseDto(
        List<MainEducationResponseDto> mainEducations,
        List<CourseResponseDto> courses
) {
}
