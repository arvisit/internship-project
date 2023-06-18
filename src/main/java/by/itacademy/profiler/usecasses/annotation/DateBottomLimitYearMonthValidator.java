package by.itacademy.profiler.usecasses.annotation;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DateBottomLimitYearMonthValidator implements ConstraintValidator<DateBottomLimitValidation, YearMonth> {

    private String min;

    @Override
    public void initialize(DateBottomLimitValidation constraintAnnotation) {
        this.min = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(YearMonth value, ConstraintValidatorContext context) {
        if (value == null || "".equals(min)) {
            return true;
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM");
        YearMonth minYearMonth = YearMonth.parse(min, dateTimeFormatter);
        return value.isAfter(minYearMonth);
    }

}
