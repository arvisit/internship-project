package by.itacademy.profiler.api.controllers;

import by.itacademy.profiler.MysqlSQLTestContainerExtension;
import by.itacademy.profiler.persistence.repository.RecommendationRepository;
import by.itacademy.profiler.usecasses.dto.RecommendationRequestDto;
import by.itacademy.profiler.usecasses.dto.RecommendationResponseDto;
import by.itacademy.profiler.util.AuthenticationTestData;
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

import java.util.List;
import java.util.Map;

import static by.itacademy.profiler.util.AuthenticationTestData.AUTH_URL_TEMPLATE;
import static by.itacademy.profiler.util.RecommendationTestData.CV_RECOMMENDATION_URL_TEMPLATE;
import static by.itacademy.profiler.util.RecommendationTestData.EXPECTED_SIZE_OF_RECOMMENDATION_LIST;
import static by.itacademy.profiler.util.RecommendationTestData.createListOfRecommendationRequestDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@ExtendWith(MysqlSQLTestContainerExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RecommendationApiControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private RecommendationRepository recommendationRepository;

    @Test
    void shouldReturn201AndJsonContentTypeWhenCreateSuccessful() {
        List<RecommendationRequestDto> request = createListOfRecommendationRequestDto();
        HttpEntity<List<RecommendationRequestDto>> requestEntity = new HttpEntity<>(request, getAuthHeader());

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                CV_RECOMMENDATION_URL_TEMPLATE,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());

        recommendationRepository.deleteAll();
    }

    @Test
    void shouldReturnExpectedResponseJsonWhenCreateSuccessful() {
        List<RecommendationRequestDto> request = createListOfRecommendationRequestDto();
        HttpEntity<List<RecommendationRequestDto>> requestEntity = new HttpEntity<>(request, getAuthHeader());

        ResponseEntity<List<RecommendationResponseDto>> responseEntity = restTemplate.exchange(
                CV_RECOMMENDATION_URL_TEMPLATE,
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<>() {}
        );

        List<RecommendationResponseDto> actualResponse = responseEntity.getBody();
        assertThat(actualResponse).hasSize(EXPECTED_SIZE_OF_RECOMMENDATION_LIST);

        recommendationRepository.deleteAll();
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
