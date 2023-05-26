package by.itacademy.profiler.usecasses.impl;

import by.itacademy.profiler.api.exception.ImageStorageException;
import by.itacademy.profiler.persistence.model.Image;
import by.itacademy.profiler.persistence.model.User;
import by.itacademy.profiler.persistence.repository.ImageRepository;
import by.itacademy.profiler.persistence.repository.UserRepository;
import by.itacademy.profiler.storage.ImageStorageService;
import by.itacademy.profiler.usecasses.dto.ImageDto;
import by.itacademy.profiler.usecasses.mapper.ImageMapper;
import by.itacademy.profiler.util.ImageTestData;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.InputStream;

import static by.itacademy.profiler.util.ImageTestData.createImage;
import static by.itacademy.profiler.util.ImageTestData.createImageInputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ImageServiceImplTest {

    @InjectMocks
    private ImageServiceImpl imageService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ImageStorageService imageStorageService;

    @Mock
    private ImageRepository imageRepository;

    @Mock
    private ImageMapper imageMapper;

    @Test
    void shouldReturnImageDtoWhenInvokeCreateImage() throws ImageStorageException {
        Image image = createImage();
        User user = image.getUser();
        String username = user.getEmail();

        when(userRepository.findByEmail(username)).thenReturn(user);
        doNothing().when(imageStorageService).save(any(InputStream.class), anyString());
        when(imageRepository.save(any(Image.class))).thenReturn(image);

        ImageDto imageDto = ImageTestData.createImageDto();
        when(imageMapper.imageToImageDto(image)).thenReturn(imageDto);

        InputStream imageInputStream = createImageInputStream();
        ImageDto saved = imageService.storageImage(imageInputStream, username);
        verify(imageStorageService, times(1)).save(any(InputStream.class), anyString());
        assertEquals(image.getUuid(), saved.uuid());

    }
}
