package by.itacademy.profiler.util;

import by.itacademy.profiler.persistence.model.Image;
import by.itacademy.profiler.usecasses.dto.ImageDto;

import org.springframework.mock.web.MockMultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static by.itacademy.profiler.util.UserProfileTestData.getUser;

import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

public class ImageTestData {

    public static final String IMAGE_UUID = "946de4eb-20d8-47fd-ad8a-73df30a9444a";
    public static final byte[] BYTE_SOURCE = { 65, 66, 67, 68, 69 };
    public static final byte[] EMPTY_BYTE_SOURCE = {};
    public static final String UNSUPPORTED_TYPE = "unsupported_type";
    public static final String IMAGES_URL_TEMPLATE = "/api/v1/images";
    public static final String REQUEST_PART_NAME = "image";
    public static final String ORIGINAL_FILENAME = "image.png";

    private ImageTestData() {
    }

    public static Image createImage() {
        Image image = new Image();
        image.setId(1L);
        image.setUser(getUser());
        image.setUuid(IMAGE_UUID);
        return image;
    }

    public static ImageDto createImageDto() {
        return new ImageDto(IMAGE_UUID);
    }

    public static InputStream createImageInputStream() {
        return new ByteArrayInputStream(BYTE_SOURCE);
    }

    public static MockMultipartFile createValidMultipartImageFile() {
        return new MockMultipartFile(REQUEST_PART_NAME, ORIGINAL_FILENAME, IMAGE_PNG_VALUE, BYTE_SOURCE);
    }

    public static MockMultipartFile createMultipartImageFileWithNoContent() {
        return new MockMultipartFile(REQUEST_PART_NAME, ORIGINAL_FILENAME, IMAGE_PNG_VALUE, EMPTY_BYTE_SOURCE);
    }

    public static MockMultipartFile createMultipartImageFileWithUnsupportedType() {
        return new MockMultipartFile(REQUEST_PART_NAME, ORIGINAL_FILENAME, UNSUPPORTED_TYPE, BYTE_SOURCE);
    }
}
