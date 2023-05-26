package by.itacademy.profiler.usecasses.annotation;

import by.itacademy.profiler.usecasses.IndustryService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IndustryValidator implements ConstraintValidator<IndustryValidation, Long> {

    private final IndustryService industryService;

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext context) {
        if (id == null) {
            return true;
        }
        return industryService.isIndustryExist(id);
    }
}
