package by.itacademy.profiler.usecasses.dto;

import by.itacademy.profiler.persistence.model.LanguageProficiencyEnum;
import lombok.Builder;

@Builder(setterPrefix = "with")
public record CvLanguageResponseDto(
        Long id,
        String name,
        LanguageProficiencyEnum languageProficiency,
        String certificateUrl
) {
}
