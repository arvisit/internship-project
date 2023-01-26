package by.itacademy.profiler.storage;

import by.itacademy.profiler.api.exception.ImageStorageException;

import java.io.InputStream;

public interface ImageStorageService {
    void saveImage(InputStream content, String fileName) throws ImageStorageException;
}
