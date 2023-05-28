package by.itacademy.profiler.api.controllers;

import by.itacademy.profiler.MysqlSQLTestContainerExtension;
import by.itacademy.profiler.usecasses.dto.UserProfileDto;
import by.itacademy.profiler.usecasses.dto.UserProfileResponseDto;
import by.itacademy.profiler.util.AuthenticationTestData;
import by.itacademy.profiler.util.UserProfileTestData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.Map;

import static by.itacademy.profiler.util.AuthenticationTestData.AUTH_URL_TEMPLATE;
import static by.itacademy.profiler.util.UserProfileTestData.USER_PROFILE_URL_TEMPLATE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@SqlGroup({
        @Sql(scripts = "classpath:testdata/add_user_profile_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "classpath:testdata/clear_user_profile_and_image_test_data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)})
@ExtendWith(MysqlSQLTestContainerExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserProfileApiControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturn200AndCorrectJsonWhenGetUserProfileSuccessful() throws JsonProcessingException {
        UserProfileDto expectedResponse = UserProfileTestData.getValideUserProfileDto();
        HttpEntity<String> requestEntity = new HttpEntity<>(getAuthHeader());

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                USER_PROFILE_URL_TEMPLATE,
                HttpMethod.GET,
                requestEntity,
                String.class);

        UserProfileResponseDto actualResponse = objectMapper.readValue(responseEntity.getBody(), new TypeReference<>() {
        });

        assertEquals(expectedResponse.cellPhone(), actualResponse.cellPhone());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
    }

    @Test
    @SqlGroup({
            @Sql(scripts = "classpath:testdata/clear_user_profile_and_image_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "classpath:testdata/add_user_image.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "classpath:testdata/clear_user_profile_and_image_test_data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)})
    void shouldReturn201AndCorrectJsonWhenUserProfileCreateSuccessful() throws JsonProcessingException {
        UserProfileDto request = UserProfileTestData.createUserProfileDto()
                .withEmail("testUpdate@gmail.com")
                .withProfileImageUuid("056de4eb-20d8-47fd-ad8a-73df30a9444a")
                .withSurname("testUpdate").build();

        HttpEntity<UserProfileDto> requestEntity = new HttpEntity<>(request, getAuthHeader());

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                USER_PROFILE_URL_TEMPLATE,
                HttpMethod.POST,
                requestEntity,
                String.class);

        UserProfileResponseDto actualResponse = objectMapper.readValue(responseEntity.getBody(), new TypeReference<>() {
        });

        assertEquals(request.name(), actualResponse.name());
        assertEquals(request.surname(), actualResponse.surname());
        assertEquals(request.email(), actualResponse.email());
        assertEquals(request.cellPhone(), actualResponse.cellPhone());
        assertEquals(request.positionId(), actualResponse.positionId());
        assertEquals(request.profileImageUuid(), actualResponse.profileImageUuid());
        assertEquals(request.phoneCodeId(), actualResponse.phoneCodeId());

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
    }

    @Test
    void shouldReturn200AndCorrectJsonWhenUserProfileUpdateSuccessful() throws JsonProcessingException {
        UserProfileDto request = UserProfileTestData.createUserProfileDto()
                .withEmail("testUpdate@gmail.com")
                .withSurname("testUpdate")
                .build();

        HttpEntity<UserProfileDto> requestEntity = new HttpEntity<>(request, getAuthHeader());

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                USER_PROFILE_URL_TEMPLATE,
                HttpMethod.PUT,
                requestEntity,
                String.class);

        UserProfileResponseDto actualResponse = objectMapper.readValue(responseEntity.getBody(), new TypeReference<>() {
        });

        assertEquals(request.name(), actualResponse.name());
        assertEquals(request.surname(), actualResponse.surname());
        assertEquals(request.email(), actualResponse.email());
        assertEquals(request.cellPhone(), actualResponse.cellPhone());
        assertEquals(request.positionId(), actualResponse.positionId());
        assertEquals(request.profileImageUuid(), actualResponse.profileImageUuid());
        assertEquals(request.phoneCodeId(), actualResponse.phoneCodeId());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
    }

    @SneakyThrows
    private HttpHeaders getAuthHeader() {
        HttpEntity<Map<String, String>> requestHttpAuthEntity = AuthenticationTestData.createLoginRequestHttpEntity();

        ResponseEntity<Map<String, String>> responseEntity = restTemplate.exchange(
                AUTH_URL_TEMPLATE,
                HttpMethod.POST,
                requestHttpAuthEntity,
                new ParameterizedTypeReference<>() {}
        );

        Map<String, String> responseMap = responseEntity.getBody();
        String token = "Bearer " + responseMap.get("token");

        HttpHeaders headers = new HttpHeaders();
        headers.set(AUTHORIZATION, token);
        return headers;
    }
}
