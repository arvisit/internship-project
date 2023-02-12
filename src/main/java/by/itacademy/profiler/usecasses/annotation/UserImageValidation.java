package by.itacademy.profiler.usecasses.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.hibernate.validator.constraints.UUID;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@UUID(allowNil = false, message = UserImageValidation.IMAGE_UUID_IS_NOT_VALID)
@Documented
@Constraint(validatedBy = UserImageValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserImageValidation {

    String IMAGE_UUID_IS_NOT_VALID = "Image UUID is not valid!";

    String message() default IMAGE_UUID_IS_NOT_VALID;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    ValidatedDto toValidate();

    enum ValidatedDto {
        PROFILE_DTO,
        CV_DTO
    }
}
