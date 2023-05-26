package by.itacademy.profiler.storage.impl;

import by.itacademy.profiler.api.exception.ImageStorageException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.CleanupMode;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static by.itacademy.profiler.util.ImageTestData.BYTE_SOURCE;
import static by.itacademy.profiler.util.ImageTestData.IMAGE_UUID;
import static by.itacademy.profiler.util.ImageTestData.createImageInputStream;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
class ImageStorageServiceImplTest {

    private static final String NON_EXISING_DIRECTORY = "folder";
    private static final String IMAGE_STORAGE_LOCATION_FIELD = "imageStorageLocation";

    @InjectMocks
    private ImageStorageServiceImpl imageStorageService;

    @Test
    void shouldSaveFileWhenSaveContentInExistingDirectory(@TempDir(cleanup = CleanupMode.ALWAYS) Path storage)
            throws Exception {
        String storageLocation = storage.toString();
        ReflectionTestUtils.setField(imageStorageService, IMAGE_STORAGE_LOCATION_FIELD, storageLocation);
        InputStream content = createImageInputStream();
        imageStorageService.save(content, IMAGE_UUID);

        Path saved = Paths.get(storageLocation, IMAGE_UUID);
        assertTrue(Files.exists(saved));
        assertEquals(BYTE_SOURCE.length, saved.toFile().length());
    }

    @Test
    void shouldSaveFileWhenSaveContentInNonExistingDirectory(@TempDir(cleanup = CleanupMode.ALWAYS) Path storage)
            throws Exception {
        String storageLocation = storage.toString() + FileSystems.getDefault().getSeparator() + NON_EXISING_DIRECTORY;
        ReflectionTestUtils.setField(imageStorageService, IMAGE_STORAGE_LOCATION_FIELD, storageLocation);
        InputStream content = createImageInputStream();
        imageStorageService.save(content, IMAGE_UUID);

        Path saved = Paths.get(storageLocation, IMAGE_UUID);
        assertTrue(Files.exists(saved));
        assertEquals(BYTE_SOURCE.length, saved.toFile().length());
    }

    @Test
    void shouldThrowImageStorageExceptionWhenIOExceptionOccurs(@TempDir(cleanup = CleanupMode.ALWAYS) Path storage)
            throws Exception {
        String storageLocation = storage.toString();
        ReflectionTestUtils.setField(imageStorageService, IMAGE_STORAGE_LOCATION_FIELD, storageLocation);
        try (MockedStatic<Files> filesUtil = mockStatic(Files.class)) {
            filesUtil.when(() -> Files.copy(Mockito.any(InputStream.class), Mockito.any(Path.class)))
                    .thenThrow(IOException.class);

            InputStream content = createImageInputStream();
            assertThatExceptionOfType(ImageStorageException.class)
                    .isThrownBy(() -> imageStorageService.save(content, IMAGE_UUID));
        }
    }

}
