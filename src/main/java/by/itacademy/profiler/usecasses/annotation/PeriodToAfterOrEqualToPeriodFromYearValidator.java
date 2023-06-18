package by.itacademy.profiler.usecasses.annotation;

import by.itacademy.profiler.usecasses.util.Periodic;

import java.time.Year;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PeriodToAfterOrEqualToPeriodFromYearValidator
        implements ConstraintValidator<PeriodToAfterOrEqualToPeriodFromValidation, Periodic<Year>> {

    @Override
    public boolean isValid(Periodic<Year> value, ConstraintValidatorContext context) {
        if (value == null) return true;

        if (value.periodTo() != null && value.periodFrom() != null) {
            return value.periodFrom().isBefore(value.periodTo()) || value.periodFrom().equals(value.periodTo());
        }
        return true;
    }

}
