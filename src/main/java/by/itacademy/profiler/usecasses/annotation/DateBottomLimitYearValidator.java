package by.itacademy.profiler.usecasses.annotation;

import java.time.Year;
import java.time.format.DateTimeFormatter;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DateBottomLimitYearValidator implements ConstraintValidator<DateBottomLimitValidation, Year> {

    private String min;

    @Override
    public void initialize(DateBottomLimitValidation constraintAnnotation) {
        this.min = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(Year value, ConstraintValidatorContext context) {
        if (value == null || "".equals(min)) {
            return true;
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy");
        Year minYear = Year.parse(min, dateTimeFormatter);
        return value.isAfter(minYear);
    }

}
