package by.itacademy.profiler.usecasses.annotation;

import by.itacademy.profiler.persistence.repository.PositionRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PositionValidator implements ConstraintValidator<PositionValidation, Long> {

    private final PositionRepository positionRepository;

    public boolean isValid(Long id, ConstraintValidatorContext cxt) {
        return positionRepository.findById(id).isPresent();
    }
}