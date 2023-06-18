package by.itacademy.profiler.usecasses.dto;

import by.itacademy.profiler.usecasses.annotation.DateBottomLimitValidation;
import by.itacademy.profiler.usecasses.annotation.DateUpperLimitFromNowValidation;
import by.itacademy.profiler.usecasses.util.Periodic;
import by.itacademy.profiler.usecasses.util.Sequencable;

import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.time.Year;

import static by.itacademy.profiler.usecasses.util.ValidationConstants.REGEXP_VALIDATE_INSTITUTION;
import static by.itacademy.profiler.usecasses.util.ValidationConstants.REGEXP_VALIDATE_SIXTH_PAGE_TEXT_FIELDS;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder(setterPrefix = "with")
public record MainEducationRequestDto(
        @NotNull(message = "Sequence number must not be null")
        @Min(value = 1, message = "Sequence number must not be less than 1")
        @Max(value = 2, message = "Sequence number must not be more than 2")
        @Schema(defaultValue = "1", description = "Sequence number")
        Integer sequenceNumber,
        @PastOrPresent(message = "Date is in the future")
        @DateBottomLimitValidation(value = "1970")
        @NotNull(message = "Main education period start must not be null")
        @Schema(defaultValue = "2015", description = "Main education period from")
        Year periodFrom,
        @DateUpperLimitFromNowValidation(value = 6L)
        @Schema(defaultValue = "2020", description = "Main education period to")
        Year periodTo,
        @NotNull(message = "Must be specified")
        @Schema(defaultValue = "false", description = "Is main education up to now")
        Boolean presentTime,
        @NotNull(message = "Institution name must not be null")
        @Pattern(regexp = REGEXP_VALIDATE_INSTITUTION, message = "Invalid institution name")
        @Length(max = 100, message = "Institution name is too long, the max number of symbols is 100")
        @Schema(defaultValue = "Institution Name", description = "Institution Name")
        String institution,
        @Pattern(regexp = REGEXP_VALIDATE_SIXTH_PAGE_TEXT_FIELDS, message = "Invalid faculty name")
        @Length(max = 40, message = "Faculty name is too long, the max number of symbols is 40")
        @Schema(defaultValue = "Faculty Name", description = "Faculty Name")
        String faculty,
        @NotNull(message = "Specialty name must not be null")
        @Pattern(regexp = REGEXP_VALIDATE_SIXTH_PAGE_TEXT_FIELDS, message = "Invalid specialty name")
        @Length(max = 100, message = "Specialty name is too long, the max number of symbols is 100")
        @Schema(defaultValue = "Specialty Name", description = "Specialty Name")
        String specialty
) implements Serializable, Sequencable, Periodic<Year> {
}
