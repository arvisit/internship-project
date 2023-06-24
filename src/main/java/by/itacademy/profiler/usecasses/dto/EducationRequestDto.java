package by.itacademy.profiler.usecasses.dto;

import by.itacademy.profiler.usecasses.annotation.PeriodToAfterOrEqualToPeriodFromValidation;
import by.itacademy.profiler.usecasses.annotation.PresentTimePeriodToValidation;
import by.itacademy.profiler.usecasses.annotation.SequenceNumbersValidation;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder(setterPrefix = "with")
public record EducationRequestDto(
        @SequenceNumbersValidation
        @Size(max = 2, message = "Amount of main educations should not be more than 2")
        @Valid
        List<
            @NotNull(message = "Main education must not be null")
            @PeriodToAfterOrEqualToPeriodFromValidation
            @PresentTimePeriodToValidation
            MainEducationRequestDto> mainEducations,
        @SequenceNumbersValidation
        @NotEmpty(message = "List of courses must have at least 1 course")
        @Size(max = 5, message = "Amount of courses should not be more than 5")
        @Valid
        List<
            @NotNull(message = "Course must not be null")
            @PeriodToAfterOrEqualToPeriodFromValidation
            @PresentTimePeriodToValidation CourseRequestDto> courses
) {
}
