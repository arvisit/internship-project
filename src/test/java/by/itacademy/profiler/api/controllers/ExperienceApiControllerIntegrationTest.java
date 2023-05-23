package by.itacademy.profiler.api.controllers;

import by.itacademy.profiler.MysqlSQLTestContainerExtension;
import by.itacademy.profiler.persistence.repository.ExperienceRepository;
import by.itacademy.profiler.usecasses.dto.ExperienceRequestDto;
import by.itacademy.profiler.usecasses.dto.ExperienceResponseDto;
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
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

import java.util.List;
import java.util.Map;

import static by.itacademy.profiler.util.AuthenticationTestData.AUTH_URL_TEMPLATE;
import static by.itacademy.profiler.util.ExperienceTestData.CV_EXPERIENCE_URL_TEMPLATE;
import static by.itacademy.profiler.util.ExperienceTestData.EXPECTED_SIZE_OF_EXPERIENCE_LIST;
import static by.itacademy.profiler.util.ExperienceTestData.createListOfExperienceRequestDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@ExtendWith(MysqlSQLTestContainerExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ExperienceApiControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ExperienceRepository experienceRepository;

    private static final Integer EXPECTED_EXPERIENCE_SIZE = 1;
    private static final String EXPECTED_EXPERIENCE_COMPANY = "Something";
    private static final String EXPECTED_EXPERIENCE_POSITION = "Someone";

    @Test
    void shouldReturn201AndJsonContentTypeWhenCreateSuccessful() {
        List<ExperienceRequestDto> request = createListOfExperienceRequestDto();
        HttpEntity<List<ExperienceRequestDto>> requestEntity = new HttpEntity<>(request, getAuthHeader());

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                CV_EXPERIENCE_URL_TEMPLATE,
                HttpMethod.POST,
                requestEntity,
                String.class);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());

        experienceRepository.deleteAll();
    }

    @Test
    void shouldReturnExpectedResponseJson() {
        List<ExperienceRequestDto> request = createListOfExperienceRequestDto();
        HttpEntity<List<ExperienceRequestDto>> requestEntity = new HttpEntity<>(request, getAuthHeader());

        ResponseEntity<List<ExperienceResponseDto>> responseEntity = restTemplate.exchange(
                CV_EXPERIENCE_URL_TEMPLATE,
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<>() {});

        List<ExperienceResponseDto> actualResponse = responseEntity.getBody();
        assertThat(actualResponse).hasSize(EXPECTED_SIZE_OF_EXPERIENCE_LIST);

        experienceRepository.deleteAll();
    }

    @Sql(scripts = "classpath:testdata/add_experience.sql",
            config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Test
    void shouldReturn200AndJsonContentTypeWhenGetExperienceByCvUuidForValidCurriculumVitaeUuid() {
        HttpEntity<String> requestEntity = new HttpEntity<>(getAuthHeader());

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                CV_EXPERIENCE_URL_TEMPLATE,
                HttpMethod.GET,
                requestEntity,
                String.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());

        experienceRepository.deleteAll();
    }

    @Sql(scripts = "classpath:testdata/add_experience.sql",
            config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Test
    void shouldReturnExpectedResponseJsonWhenGetExperienceByCvUuidForValidCurriculumVitaeUuid() {
        HttpEntity<String> requestEntity = new HttpEntity<>(getAuthHeader());

        ResponseEntity<List<ExperienceResponseDto>> response = restTemplate.exchange(
                CV_EXPERIENCE_URL_TEMPLATE,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });

        List<ExperienceResponseDto> body = response.getBody();
        ExperienceResponseDto experienceResponseDto = body.get(0);

        assertEquals(EXPECTED_EXPERIENCE_SIZE, body.size());
        assertEquals(EXPECTED_EXPERIENCE_COMPANY, experienceResponseDto.company());
        assertEquals(EXPECTED_EXPERIENCE_POSITION, experienceResponseDto.position());

        experienceRepository.deleteAll();
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
