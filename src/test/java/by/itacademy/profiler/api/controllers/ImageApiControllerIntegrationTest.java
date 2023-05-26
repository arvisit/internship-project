package by.itacademy.profiler.api.controllers;

import by.itacademy.profiler.MysqlSQLTestContainerExtension;
import by.itacademy.profiler.persistence.model.Image;
import by.itacademy.profiler.persistence.repository.ImageRepository;
import by.itacademy.profiler.usecasses.dto.ImageDto;
import by.itacademy.profiler.util.AuthenticationTestData;
import by.itacademy.profiler.util.ImageTestData;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import static by.itacademy.profiler.util.AuthenticationTestData.AUTH_URL_TEMPLATE;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(MysqlSQLTestContainerExtension.class)
class ImageApiControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ImageRepository imageRepository;

    @Value("${image.storage-dir}")
    private String imageStorageLocation;

    private static final int IMAGE_UUID_LENGTH = 36;

    @Test
    void shouldReturn201AndJsonContentTypeWhenUploadImage() throws Exception {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<String, Object>();

        ByteArrayResource contentsAsResource = getFileToUpload();
        body.add(ImageTestData.REQUEST_PART_NAME, contentsAsResource);

        HttpHeaders headers = getAuthHeader();
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<ImageDto> responseEntity = restTemplate.exchange(
                ImageTestData.IMAGES_URL_TEMPLATE,
                HttpMethod.POST,
                requestEntity,
                ImageDto.class);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());

        ImageDto responseDto = responseEntity.getBody();
        Image img = imageRepository.findByUuid(responseDto.uuid()).get();
        imageRepository.delete(img);
        deleteFile(img.getUuid());
    }

    @Test
    void shouldReturn201AndRightLengthImageUuidWhenUploadImage() throws Exception {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<String, Object>();

        ByteArrayResource contentsAsResource = getFileToUpload();
        body.add(ImageTestData.REQUEST_PART_NAME, contentsAsResource);

        HttpHeaders headers = getAuthHeader();
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<ImageDto> responseEntity = restTemplate.exchange(
                ImageTestData.IMAGES_URL_TEMPLATE,
                HttpMethod.POST,
                requestEntity,
                ImageDto.class);

        ImageDto responseDto = responseEntity.getBody();

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(IMAGE_UUID_LENGTH, responseDto.uuid().length());

        Image img = imageRepository.findByUuid(responseDto.uuid()).get();
        imageRepository.delete(img);
        deleteFile(img.getUuid());
    }

    private ByteArrayResource getFileToUpload() {
        ByteArrayResource contentsAsResource = new ByteArrayResource(ImageTestData.BYTE_SOURCE) {
            @Override
            public String getFilename() {
                return ImageTestData.ORIGINAL_FILENAME;
            }
        };
        return contentsAsResource;
    }

    private void deleteFile(String imageName) throws IOException {
        Path imageLocation = Paths.get(imageStorageLocation, imageName);
        Files.delete(imageLocation);
    }

    private HttpHeaders getAuthHeader() {
        HttpEntity<Map<String, String>> requestHttpAuthEntity = AuthenticationTestData.createLoginRequestHttpEntity();

        ResponseEntity<Map<String, String>> responseEntity = restTemplate.exchange(
                AUTH_URL_TEMPLATE,
                HttpMethod.POST,
                requestHttpAuthEntity,
                new ParameterizedTypeReference<>() {});

        Map<String, String> responseMap = responseEntity.getBody();
        String token = "Bearer " + responseMap.get("token");

        HttpHeaders headers = new HttpHeaders();
        headers.set(AUTHORIZATION, token);
        return headers;
    }
}
