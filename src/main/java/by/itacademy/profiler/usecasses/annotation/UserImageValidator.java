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

@RequiredArgsConstructor
public class UserImageValidator implements ConstraintValidator<UserImageValidation, String> {

    private final UserRepository userRepository;

    private final ImageRepository imageRepository;

    @Override
    public boolean isValid(String imageUuid, ConstraintValidatorContext context) {
        Image image = imageRepository.findByUuid(imageUuid)
                .orElseThrow(() -> new BadRequestException(String.format("No such image %s!", imageUuid)));
        User user = userRepository.findByEmail(AuthUtil.getUsername());
        if (image.getUser().getId().equals(user.getId())) {
            return true;
        } else {
            throw new BadRequestException("Not a users image!");
        }
    }
}
