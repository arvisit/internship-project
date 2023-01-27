package by.itacademy.profiler.usecasses.annotation;

import by.itacademy.profiler.api.exception.BadRequestException;
import by.itacademy.profiler.persistence.model.Image;
import by.itacademy.profiler.persistence.model.User;
import by.itacademy.profiler.persistence.repository.ImageRepository;
import by.itacademy.profiler.persistence.repository.UserRepository;
import by.itacademy.profiler.usecasses.util.AuthUtil;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import static java.util.Objects.isNull;

@RequiredArgsConstructor
public class UserImageValidator implements ConstraintValidator<UserImageValidation, String> {

    private final UserRepository userRepository;

    private final ImageRepository imageRepository;

    @Override
    public boolean isValid(String imageUuid, ConstraintValidatorContext context) {
        if (isNull(imageUuid)) {
            return true;
        }
        Image image = imageRepository.findByUuid(imageUuid)
                .orElseThrow(() -> new BadRequestException(String.format("No such image %s!", imageUuid)));
        User user = userRepository.findByEmail(AuthUtil.getUsername());
        return image.getUser().getId().equals(user.getId());
    }
}
