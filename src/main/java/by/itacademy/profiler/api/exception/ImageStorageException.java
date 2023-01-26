package by.itacademy.profiler.api.exception;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class ImageStorageException extends IOException {

    public ImageStorageException(String message) {
        super(message);
        log.error(message);
    }
}
