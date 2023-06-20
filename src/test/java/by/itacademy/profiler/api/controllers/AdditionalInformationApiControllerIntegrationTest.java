package by.itacademy.profiler.api.controllers;

import by.itacademy.profiler.MysqlSQLTestContainerExtension;
import by.itacademy.profiler.persistence.repository.AdditionalInformationRepository;
import by.itacademy.profiler.usecasses.dto.AdditionalInformationRequestDto;
import by.itacademy.profiler.usecasses.dto.AdditionalInformationResponseDto;
import by.itacademy.profiler.util.AuthenticationTestData;
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
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static by.itacademy.profiler.util.AdditionalInfoTestData.CV_ADDITIONAL_INFORMATION_URL_TEMPLATE;
import static by.itacademy.profiler.util.AdditionalInfoTestData.createAdditionalInformationRequestDto;
import static by.itacademy.profiler.util.AdditionalInfoTestData.createAdditionalInformationResponseDto;
import static by.itacademy.profiler.util.AuthenticationTestData.AUTH_URL_TEMPLATE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(MysqlSQLTestContainerExtension.class)
class AdditionalInformationApiControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AdditionalInformationRepository additionalInformationRepository;

    @Test
    void shouldReturn201AndCorrectJsonWhenSaveSuccessful() throws JsonProcessingException {
        AdditionalInformationResponseDto expectedResponse = createAdditionalInformationResponseDto().build();
        AdditionalInformationRequestDto request = createAdditionalInformationRequestDto().build();
        HttpEntity<AdditionalInformationRequestDto> requestEntity = new HttpEntity<>(request, getAuthHeader());

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                CV_ADDITIONAL_INFORMATION_URL_TEMPLATE,
                HttpMethod.POST,
                requestEntity,
                String.class);

        AdditionalInformationResponseDto actualResponse = objectMapper.readValue(responseEntity.getBody(), new TypeReference<>() {
        });

        assertEquals(expectedResponse, actualResponse);
        assertEquals(CREATED, responseEntity.getStatusCode());
        assertEquals(APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        additionalInformationRepository.deleteAll();
    }

    @SneakyThrows
    private HttpHeaders getAuthHeader() {
        HttpEntity<Map<String, String>> requestHttpAuthEntity = AuthenticationTestData.createLoginRequestHttpEntity();

        ResponseEntity<Map<String, String>> responseEntity = restTemplate.exchange(
                AUTH_URL_TEMPLATE,
                HttpMethod.POST,
                requestHttpAuthEntity,
                new ParameterizedTypeReference<>() {
                }
        );

        Map<String, String> responseMap = responseEntity.getBody();
        String token = "Bearer " + responseMap.get("token");

        HttpHeaders headers = new HttpHeaders();
        headers.set(AUTHORIZATION, token);
        return headers;
    }
}
