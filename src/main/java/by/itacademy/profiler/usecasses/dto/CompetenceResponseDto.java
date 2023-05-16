package by.itacademy.profiler.usecasses.dto;

import lombok.Builder;

import java.util.List;

@Builder(setterPrefix = "with")
public record CompetenceResponseDto(
        List<CvLanguageResponseDto> languages,
        List<SkillResponseDto> skills
) {
}
