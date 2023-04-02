package by.itacademy.profiler.usecasses.annotation;

import by.itacademy.profiler.usecasses.ImageValidationService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import static by.itacademy.profiler.usecasses.annotation.UserImageValidation.ValidatedDto.CV_DTO;
import static by.itacademy.profiler.usecasses.annotation.UserImageValidation.ValidatedDto.PROFILE_DTO;
import static java.util.Objects.isNull;

@RequiredArgsConstructor
public class UserImageValidator implements ConstraintValidator<UserImageValidation, String> {

    private final ImageValidationService imageValidationService;

    private String dtoToValidate;

    @Override
    public void initialize(UserImageValidation constraintAnnotation) {
        this.dtoToValidate = constraintAnnotation.toValidate().name();
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String imageUuid, ConstraintValidatorContext context) {
        if (isNull(imageUuid)) {
            return true;
        }
        if (imageUuid.equals("")) {
            return false;
        }
        if (!imageValidationService.isImageBelongsToUser(imageUuid)) {
            return false;
        }
        if (dtoToValidate.equals(CV_DTO.name())) {
            return imageValidationService.validateImageForCv(imageUuid);
        } else if (dtoToValidate.equals(PROFILE_DTO.name())) {
            return imageValidationService.validateImageForProfile(imageUuid);
        }
        return false;
    }
}

