package by.itacademy.profiler.usecasses.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ FIELD, PARAMETER })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = { DateUpperLimitFromNowYearValidator.class, DateUpperLimitFromNowYearMonthValidator.class })
public @interface DateUpperLimitFromNowValidation {

    long value() default 0L;

    String message() default "Date should not be later than current year + {value}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
