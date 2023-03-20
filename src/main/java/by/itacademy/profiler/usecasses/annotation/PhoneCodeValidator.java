package by.itacademy.profiler.usecasses.annotation;

import by.itacademy.profiler.usecasses.PhoneCodeService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PhoneCodeValidator implements ConstraintValidator<PhoneCodeValidation, Long> {

    private final PhoneCodeService phoneCodeService;

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext context) {
        if (id == null) {
            return true;
        }
        return phoneCodeService.isPhoneCodeExist(id);
    }
}
