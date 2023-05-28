package by.itacademy.profiler.usecasses.impl;

import by.itacademy.profiler.api.exception.BadRequestException;
import by.itacademy.profiler.api.exception.ImageNotFoundException;
import by.itacademy.profiler.api.exception.ImageStorageException;
import by.itacademy.profiler.persistence.model.Image;
import by.itacademy.profiler.persistence.model.User;
import by.itacademy.profiler.persistence.repository.ImageRepository;
import by.itacademy.profiler.persistence.repository.UserRepository;
import by.itacademy.profiler.storage.ImageStorageService;
import by.itacademy.profiler.usecasses.dto.ImageDto;
import by.itacademy.profiler.usecasses.mapper.ImageMapper;
import by.itacademy.profiler.usecasses.util.AuthService;
import by.itacademy.profiler.util.ImageTestData;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static by.itacademy.profiler.util.ImageTestData.ACTUAL_IMAGE_BYTE_SOURCE;
import static by.itacademy.profiler.util.ImageTestData.IMAGE_FOLDER;
import static by.itacademy.profiler.util.ImageTestData.IMAGE_UUID;
import static by.itacademy.profiler.util.ImageTestData.NEW_IMAGE_UUID;
import static by.itacademy.profiler.util.ImageTestData.ORIGINAL_FILENAME;
import static by.itacademy.profiler.util.ImageTestData.createImage;
import static by.itacademy.profiler.util.ImageTestData.createImageInputStream;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ImageServiceImplTest {

    private static final String DELETE_STORED_IMAGE_MESSAGE_TEMPLATE = "Image with UUID %s could not be remove";

    @InjectMocks
    private ImageServiceImpl imageService;

    @InjectMocks
    @Spy
    private ImageServiceImpl imageServiceSpy;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ImageStorageService imageStorageService;

    @Mock
    private ImageRepository imageRepository;

    @Mock
    private ImageMapper imageMapper;

    @Mock
    AuthService authService;

    @Test
    void shouldReturnImageDtoWhenInvokeStorageImage() throws IOException {
        Image image = createImage();
        User user = image.getUser();
        String username = user.getEmail();

        when(userRepository.findByEmail(username)).thenReturn(user);
        doNothing().when(imageStorageService).save(any(InputStream.class), anyString());
        when(imageRepository.save(any(Image.class))).thenReturn(image);

        ImageDto imageDto = ImageTestData.createImageDto();
        when(imageMapper.imageToImageDto(image)).thenReturn(imageDto);

        try (InputStream imageInputStream = createImageInputStream()) {
            ImageDto saved = imageService.storageImage(imageInputStream, username);
            assertEquals(image.getUuid(), saved.uuid());
        }
        verify(imageStorageService, times(1)).save(any(InputStream.class), anyString());

    }

    @Test
    void shouldReturnByteArrayWhenInvokeGetImageWithExistingImage() throws IOException {
        Image image = createImage();
        String username = image.getUser().getEmail();
        ClassLoader classLoader = getClass().getClassLoader();
        File imageFile = new File(classLoader.getResource(IMAGE_FOLDER + '/' + ORIGINAL_FILENAME).getFile());

        when(authService.getUsername()).thenReturn(username);
        when(imageRepository.findByUuidAndUsername(anyString(), anyString())).thenReturn(image);
        when(imageStorageService.getImage(anyString())).thenReturn(imageFile);

        byte[] result = imageService.getImage(image.getUuid());

        verify(authService, times(1)).getUsername();
        verify(imageRepository, times(1)).findByUuidAndUsername(anyString(), anyString());
        verify(imageStorageService, times(1)).getImage(anyString());

        byte[] expected = ACTUAL_IMAGE_BYTE_SOURCE;
        assertArrayEquals(expected, result);
    }

    @Test
    void shouldThrowImageNotFoundExceptionWhenInvokeGetImageWithNotExistingImage() throws IOException {
        Image image = createImage();
        String username = image.getUser().getEmail();

        when(authService.getUsername()).thenReturn(username);
        when(imageRepository.findByUuidAndUsername(anyString(), anyString())).thenReturn(null);

        String imageUuid = image.getUuid();
        assertThrows(ImageNotFoundException.class, () -> imageService.getImage(imageUuid));

        verify(authService, times(1)).getUsername();
        verify(imageRepository, times(1)).findByUuidAndUsername(anyString(), anyString());
        verify(imageStorageService, times(0)).getImage(anyString());

    }

    @Test
    void shouldReturnImageDtoWhenInvokeReplaceImageSuccessfully() throws IOException {
        Image image = createImage();
        String username = image.getUser().getEmail();

        when(authService.getUsername()).thenReturn(username);
        when(imageRepository.findByUuidAndUsername(anyString(), anyString())).thenReturn(image);
        doNothing().when(imageStorageService).delete(anyString());
        doNothing().when(imageStorageService).save(any(InputStream.class), anyString());

        ImageDto imageDto = ImageTestData.createImageDto();
        when(imageMapper.imageToImageDto(image)).thenReturn(imageDto);

        try (InputStream imageInputStream = createImageInputStream()) {
            ImageDto result = imageService.replaceImage(imageInputStream, image.getUuid());
            assertEquals(imageDto, result);
        }
        verify(authService, times(1)).getUsername();
        verify(imageRepository, times(1)).findByUuidAndUsername(anyString(), anyString());
        verify(imageStorageService, times(1)).delete(anyString());
        verify(imageStorageService, times(1)).save(any(InputStream.class), anyString());
        verify(imageMapper, times(1)).imageToImageDto(any(Image.class));
    }

    @Test
    void shouldThrowImageStorageExceptionWhenInvokeReplaceImageWithImageThatUserDoNotHave()
            throws IOException {
        Image image = createImage();
        String username = image.getUser().getEmail();

        when(authService.getUsername()).thenReturn(username);
        when(imageRepository.findByUuidAndUsername(anyString(), anyString())).thenReturn(null);

        try (InputStream imageInputStream = createImageInputStream()) {
            assertThrows(ImageStorageException.class,
                    () -> imageService.replaceImage(imageInputStream, image.getUuid()));
        }
        verify(authService, times(1)).getUsername();
        verify(imageRepository, times(1)).findByUuidAndUsername(anyString(), anyString());
        verify(imageStorageService, times(0)).delete(anyString());
        verify(imageStorageService, times(0)).save(any(InputStream.class), anyString());
        verify(imageMapper, times(0)).imageToImageDto(any(Image.class));
    }

    @Test
    void shouldReturnFalseWhenInvokeIsImageChangingWithNullIncomingImageUuidAndNullStoredImage() {

        assertFalse(imageService.isImageChanging(null, null));
    }

    @Test
    void shouldReturnTrueWhenInvokeIsImageChangingWithNonNullIncomingImageUuidAndNullStoredImage() {

        assertTrue(imageService.isImageChanging(IMAGE_UUID, null));
    }

    @Test
    void shouldReturnTrueWhenInvokeIsImageChangingWithNullIncomingImageUuidAndNonNullStoredImage() {
        Image image = createImage();
        assertTrue(imageService.isImageChanging(null, image));
    }

    @Test
    void shouldReturnTrueWhenInvokeIsImageChangingWithNonEqualIncomingImageUuidAndStoredImageUuid() {
        Image image = createImage();
        String incomingImageUuid = NEW_IMAGE_UUID;
        assertTrue(imageService.isImageChanging(incomingImageUuid, image));
    }

    @Test
    void shouldReturnFalseWhenInvokeIsImageChangingWithEqualIncomingImageUuidAndStoredImageUuid() {
        Image image = createImage();
        String incomingImageUuid = image.getUuid();
        assertFalse(imageService.isImageChanging(incomingImageUuid, image));
    }

    @Test
    void shouldNotThrowExceptionWhenInvokeDeleteStoredImageFileSuccessfully() throws ImageStorageException {
        Image image = createImage();
        doNothing().when(imageStorageService).delete(image.getUuid());
        assertDoesNotThrow(() -> imageService.deleteStoredImageFile(image));
    }

    @Test
    void shouldThrowBadRequestExceptionWhenInvokeDeleteStoredImageFileAndImageStorageExceptionOccurs()
            throws ImageStorageException {
        Image image = createImage();
        String imageUuid = image.getUuid();
        doThrow(ImageStorageException.class).when(imageStorageService).delete(imageUuid);
        String expectedMessage = String.format(DELETE_STORED_IMAGE_MESSAGE_TEMPLATE, imageUuid);
        assertThrows(BadRequestException.class, () -> imageService.deleteStoredImageFile(image), expectedMessage);
    }

    @Test
    void shouldReturnNullWhenInvokeReplaceImageWithNullIncomingImageUuidAndNonNullStoredImage() {
        Image image = createImage();
        doNothing().when(imageServiceSpy).deleteStoredImageFile(any(Image.class));
        String result = imageServiceSpy.replaceImage(null, image);
        assertNull(result);
        verify(imageServiceSpy, times(1)).deleteStoredImageFile(any(Image.class));
    }

    @Test
    void shouldReturnNewUuidWhenInvokeReplaceImageWithNonNullIncomingImageUuidAndNonNullStoredImage() {
        Image image = createImage();
        doNothing().when(imageServiceSpy).deleteStoredImageFile(any(Image.class));
        String result = imageServiceSpy.replaceImage(NEW_IMAGE_UUID, image);
        assertEquals(NEW_IMAGE_UUID, result);
        verify(imageServiceSpy, times(1)).deleteStoredImageFile(any(Image.class));
    }

    @Test
    void shouldReturnNewUuidWhenInvokeReplaceImageWithNonNullIncomingImageUuidAndNullStoredImage() {
        String result = imageServiceSpy.replaceImage(NEW_IMAGE_UUID, null);
        assertEquals(NEW_IMAGE_UUID, result);
        verify(imageServiceSpy, times(0)).deleteStoredImageFile(any(Image.class));
    }

    @Test
    void shouldReturnNullWhenInvokeReplaceImageWithNullIncomingImageUuidAndNullStoredImage() {
        String imageUuid = null;
        assertNull(imageServiceSpy.replaceImage(imageUuid, null));
        verify(imageServiceSpy, times(0)).deleteStoredImageFile(any(Image.class));
    }
}
