package by.itacademy.profiler.storage.impl;

import by.itacademy.profiler.api.exception.ImageStorageException;
import by.itacademy.profiler.storage.ImageStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@RequiredArgsConstructor
@Component
public class ImageStorageServiceImpl implements ImageStorageService {

    @Value("${image.storage-dir}")
    private String imageStorageLocation;

    @Override
    public void save(InputStream content, String imageName) throws ImageStorageException {
        try {
            Path location = Paths.get(imageStorageLocation);
            if (!Files.exists(location)) {
                Files.createDirectories(location);
            }
            Path imageLocation = Paths.get(imageStorageLocation, imageName);
            Files.copy(content, imageLocation);
            log.debug("Image was saved successfully {}", imageName);
        } catch (IOException e) {
            throw new ImageStorageException(e.getMessage());
        }
    }

    @Override
    public void delete(String imageName) throws ImageStorageException {
        try {
            Path imageLocation = Paths.get(imageStorageLocation, imageName);
            Files.delete(imageLocation);
            log.debug("Image was deleted successfully {}", imageName);
        } catch (IOException e) {
            throw new ImageStorageException(e.getMessage());
        }
    }
}
