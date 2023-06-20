package by.itacademy.profiler.usecasses.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;

import java.util.List;

import static by.itacademy.profiler.usecasses.util.ValidationConstants.REGEXP_VALIDATE_ADDITIONAL_INFORMATION;
import static by.itacademy.profiler.usecasses.util.ValidationConstants.REGEXP_VALIDATE_HOBBY;

@Builder(setterPrefix = "with")
public record AdditionalInformationRequestDto(

        @NotNull(message = "Additional information must not be null")
        @Pattern(regexp = REGEXP_VALIDATE_ADDITIONAL_INFORMATION, message = "Invalid Additional information")
        @Length(max = 150, message = "Additional information is too long, the max number of symbols is 150")
        @Schema(defaultValue = "Additional information example", description = "Additional information")
        String additionalInfo,

        @Pattern(regexp = REGEXP_VALIDATE_HOBBY, message = "Invalid hobby")
        @Length(max = 100, message = "Hobby is too long, the max number of symbols is 100")
        @Schema(defaultValue = "Photography ", description = "Hobby")
        String hobby,

        @Valid
        @Size(max = 3, message = "Amount of awards should not be more than 3")
        List<AwardDto> awards) {
}
