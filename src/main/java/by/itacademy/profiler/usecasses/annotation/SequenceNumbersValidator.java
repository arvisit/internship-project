package by.itacademy.profiler.usecasses.annotation;

import by.itacademy.profiler.usecasses.dto.ExperienceRequestDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class SequenceNumbersValidator implements ConstraintValidator<SequenceNumbersValidation, List<ExperienceRequestDto>> {
    @Override
    public boolean isValid(List<ExperienceRequestDto> value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true;
        }
        List<Integer> sequenceNumbers = value.stream()
                .map(ExperienceRequestDto::sequenceNumber)
                .distinct()
                .toList();
        return value.size() == sequenceNumbers.size();
    }
}
