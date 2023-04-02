package by.itacademy.profiler.usecasses.impl;

import by.itacademy.profiler.api.exception.BadRequestException;
import by.itacademy.profiler.persistence.model.Image;
import by.itacademy.profiler.persistence.model.User;
import by.itacademy.profiler.persistence.repository.ImageRepository;
import by.itacademy.profiler.persistence.repository.UserRepository;
import by.itacademy.profiler.usecasses.ImageValidationService;
import by.itacademy.profiler.usecasses.util.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageValidationServiceImpl implements ImageValidationService {

    private final UserRepository userRepository;

    private final ImageRepository imageRepository;

    private final AuthService authService;

    @Override
    public boolean validateImageForCv(String imageUuid) {
        return !imageRepository.isImageBelongUserProfile(imageUuid);
    }

    @Override
    public boolean validateImageForProfile(String imageUuid) {
        return !imageRepository.isImageBelongCurriculumVitae(imageUuid);
    }

    @Override
    public boolean isImageBelongsToUser(String imageUuid) {
        Image image = imageRepository.findByUuid(imageUuid)
                .orElseThrow(() -> new BadRequestException(String.format("No such image %s!", imageUuid)));
        User user = userRepository.findByEmail(authService.getUsername());
        return image.getUser().getId().equals(user.getId());
    }
}
