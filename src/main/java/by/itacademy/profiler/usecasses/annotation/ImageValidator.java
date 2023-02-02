package by.itacademy.profiler.usecasses.annotation;

import by.itacademy.profiler.api.exception.BadRequestException;
import by.itacademy.profiler.persistence.repository.ImageRepository;
import by.itacademy.profiler.usecasses.util.AuthUtil;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import static java.util.Objects.nonNull;

@RequiredArgsConstructor
public class ImageValidator implements ConstraintValidator<ImageValidation, String> {

    private final ImageRepository imageRepository;

    public boolean isValid(String uuid, ConstraintValidatorContext cxt) {
        if (null == uuid) {
            return true;
        }
        if (imageRepository.isImageBelongCurriculumVitae(uuid) || imageRepository.isImageBelongUserProfile(uuid)) {
            throw new BadRequestException(String.format("image %s already in use!", uuid));
        }
        String username = AuthUtil.getUsername();
        return nonNull(imageRepository.findByUuidAndUsername(uuid, username));
    }
}