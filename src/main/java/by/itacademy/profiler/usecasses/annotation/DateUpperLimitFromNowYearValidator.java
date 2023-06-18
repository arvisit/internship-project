package by.itacademy.profiler.usecasses.annotation;

import java.time.Year;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DateUpperLimitFromNowYearValidator implements ConstraintValidator<DateUpperLimitFromNowValidation, Year> {

    private long increment;

    @Override
    public void initialize(DateUpperLimitFromNowValidation constraintAnnotation) {
        this.increment = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(Year value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        Year maxYear = Year.now().plusYears(increment);
        return value.isBefore(maxYear);
    }

}
