package by.itacademy.profiler.usecasses.annotation;

import by.itacademy.profiler.persistence.repository.PhoneCodeRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PhoneCodeValidator implements ConstraintValidator<PhoneCodeValidation, Long> {

    private final PhoneCodeRepository phoneCodeRepository;

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext context) {
        if (id == null) {
            return true;
        }
        return phoneCodeRepository.findById(id).isPresent();
    }
}
