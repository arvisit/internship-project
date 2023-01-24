package by.itacademy.profiler.usecasses.annotation;

import by.itacademy.profiler.persistence.repository.ImageRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ImageValidator implements ConstraintValidator<ImageValidation, String> {

    private final ImageRepository imageRepository;

    public boolean isValid(String uuid, ConstraintValidatorContext cxt) {
        if (null == uuid) {
            return true;
        }
        return imageRepository.findByUuid(uuid).isPresent();
    }
}