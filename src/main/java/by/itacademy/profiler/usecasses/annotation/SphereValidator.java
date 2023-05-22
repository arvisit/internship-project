package by.itacademy.profiler.usecasses.annotation;

import by.itacademy.profiler.usecasses.SphereService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SphereValidator implements ConstraintValidator<SphereValidation, Long> {

    private final SphereService sphereService;

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext context) {
        if (id == null) {
            return true;
        }
        return sphereService.isSphereExist(id);
    }
}
