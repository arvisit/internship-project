package by.itacademy.profiler.api.controllers;

import by.itacademy.profiler.api.exception.EmptyFileException;
import by.itacademy.profiler.api.exception.ImageStorageException;
import by.itacademy.profiler.api.exception.WrongMediaTypeException;
import by.itacademy.profiler.usecasses.ImageService;
import by.itacademy.profiler.usecasses.dto.ImageDto;
import by.itacademy.profiler.usecasses.util.AuthService;
import by.itacademy.profiler.usecasses.util.ValidateImage;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.io.InputStream;

import static by.itacademy.profiler.util.ImageTestData.IMAGES_URL_TEMPLATE;
import static by.itacademy.profiler.util.ImageTestData.createImageDto;
import static by.itacademy.profiler.util.ImageTestData.createMultipartImageFileWithNoContent;
import static by.itacademy.profiler.util.ImageTestData.createMultipartImageFileWithUnsupportedType;
import static by.itacademy.profiler.util.ImageTestData.createValidMultipartImageFile;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ImageApiController.class)
@AutoConfigureMockMvc(addFilters = false)
class ImageApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ImageService imageService;

    @MockBean
    private AuthService authService;

    private static final String USERNAME = "username";

    @Test
    void shouldReturn201WhenUploadImageSuccessfully() throws Exception {
        MockMultipartFile uploadingFile = createValidMultipartImageFile();

        try (MockedStatic<ValidateImage> validateImageUtil = mockStatic(ValidateImage.class)) {
            validateImageUtil.when(() -> ValidateImage.validate(uploadingFile)).then(invocationOnMock -> null);

            when(authService.getUsername()).thenReturn(USERNAME);

            ImageDto imageDto = createImageDto();
            when(imageService.storageImage(any(InputStream.class), anyString())).thenReturn(imageDto);

            mockMvc.perform(multipart(HttpMethod.POST, IMAGES_URL_TEMPLATE).file(uploadingFile))
                    .andDo(print())
                    .andExpect(status().isCreated());
        }
    }

    @Test
    void shouldReturnExpectedResponseJsonWhenUploadImageSuccessfully() throws Exception {
        MockMultipartFile uploadingFile = createValidMultipartImageFile();

        try (MockedStatic<ValidateImage> validateImageUtil = mockStatic(ValidateImage.class)) {
            validateImageUtil.when(() -> ValidateImage.validate(uploadingFile)).then(invocationOnMock -> null);

            when(authService.getUsername()).thenReturn(USERNAME);

            ImageDto imageDto = createImageDto();
            when(imageService.storageImage(any(InputStream.class), anyString())).thenReturn(imageDto);

            mockMvc.perform(multipart(HttpMethod.POST, IMAGES_URL_TEMPLATE).file(uploadingFile))
                    .andDo(print())
                    .andExpect(status().isCreated())
                    .andExpect(content().string(objectMapper.writeValueAsString(imageDto)));
        }
    }

    @Test
    void shouldInvokeBusinessLogicWhenUploadImageSuccesfully() throws Exception {
        MockMultipartFile uploadingFile = createValidMultipartImageFile();

        try (MockedStatic<ValidateImage> validateImageUtil = mockStatic(ValidateImage.class)) {
            validateImageUtil.when(() -> ValidateImage.validate(uploadingFile)).then(invocationOnMock -> null);

            when(authService.getUsername()).thenReturn(USERNAME);

            ImageDto imageDto = createImageDto();
            when(imageService.storageImage(any(InputStream.class), anyString())).thenReturn(imageDto);

            mockMvc.perform(multipart(HttpMethod.POST, IMAGES_URL_TEMPLATE).file(uploadingFile))
                    .andDo(print())
                    .andExpect(status().isCreated());

            verify(imageService, times(1)).storageImage(any(InputStream.class), anyString());
            verify(authService, times(1)).getUsername();
        }
    }

    @Test
    void shouldReturn400WhenUploadImageWithNoContent() throws Exception {
        MockMultipartFile uploadingFile = createMultipartImageFileWithNoContent();

        try (MockedStatic<ValidateImage> validateImageUtil = mockStatic(ValidateImage.class)) {
            validateImageUtil.when(() -> ValidateImage.validate(uploadingFile)).thenThrow(EmptyFileException.class);

            mockMvc.perform(multipart(HttpMethod.POST, IMAGES_URL_TEMPLATE).file(uploadingFile))
                    .andDo(print())
                    .andExpect(status().isBadRequest());

            verify(imageService, times(0)).storageImage(any(InputStream.class), anyString());
            verify(authService, times(0)).getUsername();
        }
    }

    @Test
    void shouldReturn415WhenUploadImageWithUnsupportedType() throws Exception {
        MockMultipartFile uploadingFile = createMultipartImageFileWithUnsupportedType();

        try (MockedStatic<ValidateImage> validateImageUtil = mockStatic(ValidateImage.class)) {
            validateImageUtil.when(() -> ValidateImage.validate(uploadingFile))
                    .thenThrow(WrongMediaTypeException.class);

            mockMvc.perform(multipart(HttpMethod.POST, IMAGES_URL_TEMPLATE).file(uploadingFile))
                    .andDo(print())
                    .andExpect(status().isUnsupportedMediaType());

            verify(imageService, times(0)).storageImage(any(InputStream.class), anyString());
            verify(authService, times(0)).getUsername();
        }
    }

    @Test
    void shouldReturn400WhenUploadImageAndImageStorageExceptionOccurs() throws Exception {
        MockMultipartFile uploadingFile = createValidMultipartImageFile();

        try (MockedStatic<ValidateImage> validateImageUtil = mockStatic(ValidateImage.class)) {
            validateImageUtil.when(() -> ValidateImage.validate(uploadingFile)).then(invocationOnMock -> null);

            when(authService.getUsername()).thenReturn(USERNAME);

            when(imageService.storageImage(any(InputStream.class), anyString())).thenThrow(ImageStorageException.class);

            mockMvc.perform(multipart(HttpMethod.POST, IMAGES_URL_TEMPLATE).file(uploadingFile))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
        }
    }
}
