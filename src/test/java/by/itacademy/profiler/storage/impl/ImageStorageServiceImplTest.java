package by.itacademy.profiler.storage.impl;

import by.itacademy.profiler.api.exception.ImageNotFoundException;
import by.itacademy.profiler.api.exception.ImageStorageException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.CleanupMode;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static by.itacademy.profiler.util.ImageTestData.BYTE_SOURCE;
import static by.itacademy.profiler.util.ImageTestData.IMAGE_FOLDER;
import static by.itacademy.profiler.util.ImageTestData.IMAGE_UUID;
import static by.itacademy.profiler.util.ImageTestData.NONEXISTING_FILENAME;
import static by.itacademy.profiler.util.ImageTestData.ORIGINAL_FILENAME;
import static by.itacademy.profiler.util.ImageTestData.createImageInputStream;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.RETURNS_DEFAULTS;
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
        try (InputStream content = createImageInputStream()) {
            imageStorageService.save(content, IMAGE_UUID);
        }
        Path saved = Paths.get(storageLocation, IMAGE_UUID);
        assertTrue(Files.exists(saved));
        assertEquals(BYTE_SOURCE.length, saved.toFile().length());
    }

    @Test
    void shouldSaveFileWhenSaveContentInNonExistingDirectory(@TempDir(cleanup = CleanupMode.ALWAYS) Path storage)
            throws Exception {
        String storageLocation = storage.toString() + FileSystems.getDefault().getSeparator() + NON_EXISING_DIRECTORY;
        ReflectionTestUtils.setField(imageStorageService, IMAGE_STORAGE_LOCATION_FIELD, storageLocation);
        try (InputStream content = createImageInputStream()) {
            imageStorageService.save(content, IMAGE_UUID);
        }
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
            filesUtil.when(() -> Files.copy(any(InputStream.class), any(Path.class)))
                    .thenThrow(IOException.class);

            try (InputStream content = createImageInputStream()) {
                assertThatExceptionOfType(ImageStorageException.class)
                        .isThrownBy(() -> imageStorageService.save(content, IMAGE_UUID));
            }
        }
    }

    @Test
    void shouldReturnFileWhenInvokeGetImageWithExistingFilename() {
        ClassLoader classLoader = getClass().getClassLoader();
        File storage = new File(classLoader.getResource(IMAGE_FOLDER).getFile());
        String storageLocation = storage.getAbsolutePath();
        ReflectionTestUtils.setField(imageStorageService, IMAGE_STORAGE_LOCATION_FIELD, storageLocation);
        String imageName = ORIGINAL_FILENAME;

        File imageFile = imageStorageService.getImage(imageName);
        File expectedImageFile = new File(classLoader.getResource(IMAGE_FOLDER + '/' + ORIGINAL_FILENAME).getFile());
        assertEquals(expectedImageFile.getAbsolutePath(), imageFile.getAbsolutePath());
    }

    @Test
    void shouldThrowImageNotFoundExceptionWhenInvokeGetImageWithNonExistingFilename() {
        String storageLocation = IMAGE_FOLDER;
        ReflectionTestUtils.setField(imageStorageService, IMAGE_STORAGE_LOCATION_FIELD, storageLocation);
        String imageName = NONEXISTING_FILENAME;

        assertThrows(ImageNotFoundException.class, () -> imageStorageService.getImage(imageName));
    }

    @Test
    void shouldThrowNothingWhenInvokeDeleteWithExistingImage() {
        String imageName = ORIGINAL_FILENAME;
        String storageLocation = IMAGE_FOLDER;
        ReflectionTestUtils.setField(imageStorageService, IMAGE_STORAGE_LOCATION_FIELD, storageLocation);
        try (MockedStatic<Files> filesUtil = mockStatic(Files.class)) {
            filesUtil.when(() -> Files.delete(any(Path.class))).then(RETURNS_DEFAULTS);

            Assertions.assertDoesNotThrow(() -> imageStorageService.delete(imageName));
        }
    }

    @Test
    void shouldThrowImageStorageExceptionWhenInvokeDeleteWithNonExistingImage() {
        String imageName = NONEXISTING_FILENAME;
        String storageLocation = IMAGE_FOLDER;
        ReflectionTestUtils.setField(imageStorageService, IMAGE_STORAGE_LOCATION_FIELD, storageLocation);
        try (MockedStatic<Files> filesUtil = mockStatic(Files.class)) {
            filesUtil.when(() -> Files.delete(any(Path.class))).thenThrow(IOException.class);

            assertThrows(ImageStorageException.class, () -> imageStorageService.delete(imageName));
        }
    }
}
