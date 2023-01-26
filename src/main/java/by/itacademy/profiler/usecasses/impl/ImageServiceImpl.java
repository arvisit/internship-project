package by.itacademy.profiler.usecasses.impl;

import by.itacademy.profiler.api.exception.ImageStorageException;
import by.itacademy.profiler.persistence.model.Image;
import by.itacademy.profiler.persistence.model.User;
import by.itacademy.profiler.persistence.repository.ImageRepository;
import by.itacademy.profiler.persistence.repository.UserRepository;
import by.itacademy.profiler.storage.ImageStorageService;
import by.itacademy.profiler.usecasses.ImageService;
import by.itacademy.profiler.usecasses.dto.ImageDto;
import by.itacademy.profiler.usecasses.mapper.ImageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageMapper imageMapper;

    private final ImageStorageService imageStorageService;

    private final UserRepository userRepository;

    private final ImageRepository imageRepository;

    @Override
    @Transactional
    public ImageDto storageImage(InputStream imageInputStream, String username) throws ImageStorageException {
        String imageName = UUID.randomUUID().toString();
        Image image = createImage(imageName, username);
        imageStorageService.saveImage(imageInputStream, imageName);
        Image savedImage = imageRepository.save(image);
        return imageMapper.imageToImageDto(savedImage);
    }

    private Image createImage(String imageName, String username) {
        User user = userRepository.findByEmail(username);
        return new Image(user, imageName);
    }
}
