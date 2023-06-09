package by.itacademy.profiler.usecasses.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ FIELD, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = { PeriodToPeriodFromYearValidator.class,
        PeriodToPeriodFromYearMonthValidator.class })
public @interface PeriodToPeriodFromValidation {

    String message() default "Field `periodTo` should be later than `periodFrom`";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
