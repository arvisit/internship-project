package by.itacademy.profiler.usecasses.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.hibernate.validator.constraints.UUID;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@UUID(allowNil = false)
@Documented
@Constraint(validatedBy = UserImageValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserImageValidation {
    String message() default "Image UUID is not valid!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    ValidatedDto toValidate();

    enum ValidatedDto {
        PROFILE_DTO,
        CV_DTO
    }
}
