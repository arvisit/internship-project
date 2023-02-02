package by.itacademy.profiler.usecasses.impl;

import by.itacademy.profiler.api.exception.BadRequestException;
import by.itacademy.profiler.api.exception.ImageStorageException;
import by.itacademy.profiler.persistence.model.Image;
import by.itacademy.profiler.persistence.model.User;
import by.itacademy.profiler.persistence.repository.ImageRepository;
import by.itacademy.profiler.persistence.repository.UserRepository;
import by.itacademy.profiler.storage.ImageStorageService;
import by.itacademy.profiler.usecasses.ImageService;
import by.itacademy.profiler.usecasses.dto.ImageDto;
import by.itacademy.profiler.usecasses.mapper.ImageMapper;
import by.itacademy.profiler.usecasses.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
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
        imageStorageService.save(imageInputStream, imageName);
        Image savedImage = imageRepository.save(image);
        return imageMapper.imageToImageDto(savedImage);
    }

    @Override
    @Transactional
    public ImageDto replaceImage(InputStream imageInputStream, String uuid) throws ImageStorageException {
        String username = AuthUtil.getUsername();
        Image image = imageRepository.findByUuidAndUsername(uuid, username);
        if (null != image) {
            imageStorageService.delete(uuid);
            imageStorageService.save(imageInputStream, uuid);
        } else {
            throw new ImageStorageException(String.format("Image with UUID %s could not be replaced", uuid));
        }
        return imageMapper.imageToImageDto(image);
    }

    @Override
    public byte[] getImage(String imageName) throws IOException {
        String username = AuthUtil.getUsername();
        Image uuid = imageRepository.findByUuidAndUsername(imageName, username);
        if (uuid == null) {
            log.error("User {} doesn't have image with uuid {}", username, imageName);
            throw new BadRequestException(String.format("Wrong image uuid %s", imageName));
        }
        File image = imageStorageService.getImage(imageName);
        try (FileInputStream inputStream = new FileInputStream(image)) {
            return StreamUtils.copyToByteArray(inputStream);
        }
    }

    private Image createImage(String imageName, String username) {
        User user = userRepository.findByEmail(username);
        return new Image(user, imageName);
    }
}
