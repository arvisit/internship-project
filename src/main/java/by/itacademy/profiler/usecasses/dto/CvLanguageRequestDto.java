package by.itacademy.profiler.usecasses.dto;

import by.itacademy.profiler.persistence.model.LanguageProficiencyEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;


@Builder(setterPrefix = "with")
public record CvLanguageRequestDto(
        @NotNull(message = "Field must not be null")
        @Schema(defaultValue = "1", description = "Language id")
        Long id,
        @NotNull(message = "Language proficiency level must not be null")
        @Schema(defaultValue = "A2", description = "language proficiency level")
        LanguageProficiencyEnum languageProficiency,
        @Schema(defaultValue = "http://example.com/rndm-url", description = "Certificate url")
        @Length(max = 255, message = "The certificate url is too long, the max number of symbols is 255")
        String certificateUrl
) {
}
