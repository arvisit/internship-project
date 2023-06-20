package by.itacademy.profiler.usecasses.dto;

import by.itacademy.profiler.usecasses.annotation.DateBottomLimitValidation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;

import java.time.YearMonth;

import static by.itacademy.profiler.usecasses.util.ValidationConstants.REGEXP_VALIDATE_AWARD_DESCRIPTION;
import static by.itacademy.profiler.usecasses.util.ValidationConstants.REGEXP_VALIDATE_AWARD_ISSUER;
import static by.itacademy.profiler.usecasses.util.ValidationConstants.REGEXP_VALIDATE_AWARD_TITLE;

@Builder(setterPrefix = "with")
public record AwardDto(
        @NotNull(message = "Title must not be null")
        @Pattern(regexp = REGEXP_VALIDATE_AWARD_TITLE, message = "Invalid title")
        @Length(max = 30, message = "Title is too long, the max number of symbols is 30")
        @Schema(defaultValue = "Title example", description = "Title")
        String title,

        @DateBottomLimitValidation(value = "1970-01")
        @PastOrPresent(message = "Date is in the future")
        @Schema(defaultValue = "2020-02", description = "Working period from")
        YearMonth date,

        @Pattern(regexp = REGEXP_VALIDATE_AWARD_ISSUER, message = "Invalid issuer")
        @Length(max = 25, message = "Issuer is too long, the max number of symbols is 25")
        @Schema(defaultValue = "Issuer example", description = "Issuer")
        String issuer,

        @Pattern(regexp = REGEXP_VALIDATE_AWARD_DESCRIPTION, message = "Invalid Description")
        @Length(max = 70, message = "Description is too long, the max number of symbols is 70")
        @Schema(defaultValue = "Description example", description = "Description")
        String description,


        @Length(max = 255, message = "Link is too long, the max number of symbols is 255")
        @Schema(defaultValue = "https://example.com/rand", description = "Link")
        String link) {
}
