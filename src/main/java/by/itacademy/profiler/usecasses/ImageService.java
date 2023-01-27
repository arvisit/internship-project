package by.itacademy.profiler.usecasses;

import by.itacademy.profiler.api.exception.ImageStorageException;
import by.itacademy.profiler.usecasses.dto.ImageDto;

import java.io.InputStream;

public interface ImageService {

    ImageDto storageImage(InputStream imageInputStream, String username) throws ImageStorageException;

    ImageDto replaceImage(InputStream imageInputStream, String uuid) throws ImageStorageException;
}
