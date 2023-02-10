package by.itacademy.profiler.usecasses;

import by.itacademy.profiler.api.exception.ImageStorageException;
import by.itacademy.profiler.persistence.model.Image;
import by.itacademy.profiler.usecasses.dto.ImageDto;

import java.io.IOException;
import java.io.InputStream;

public interface ImageService {

    ImageDto storageImage(InputStream imageInputStream, String username) throws ImageStorageException;

    ImageDto replaceImage(InputStream imageInputStream, String uuid) throws ImageStorageException;

    byte[] getImage(String imageName) throws IOException;

    void deleteStoredImageFile(Image image);

    boolean isImageChanging(String incomingImageUuid, Image storedImage);

    String replaceImage(String incomingImageUuid, Image storedImage);
}
