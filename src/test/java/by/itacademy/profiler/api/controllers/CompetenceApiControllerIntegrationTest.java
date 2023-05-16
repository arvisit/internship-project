package by.itacademy.profiler.api.controllers;

import by.itacademy.profiler.MysqlSQLTestContainerExtension;
import by.itacademy.profiler.usecasses.dto.CompetenceRequestDto;
import by.itacademy.profiler.usecasses.dto.CompetenceResponseDto;
import by.itacademy.profiler.util.AuthenticationTestData;
import by.itacademy.profiler.util.CompetenceTestData;
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
import static by.itacademy.profiler.util.CompetenceTestData.CV_COMPETENCE_URL_TEMPLATE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@SqlGroup({
        @Sql(scripts = "classpath:testdata/add_cvs.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "classpath:testdata/clear_competence_test_data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)})
@ExtendWith(MysqlSQLTestContainerExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CompetenceApiControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldReturn201AndJsonContentTypeWhenCreateSuccessful() {
        CompetenceRequestDto request = CompetenceTestData.createCompetenceRequestDto();
        HttpEntity<CompetenceRequestDto> requestEntity = new HttpEntity<>(request, getAuthHeader());

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                CV_COMPETENCE_URL_TEMPLATE,
                HttpMethod.POST,
                requestEntity,
                String.class);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
    }

    @Test
    void shouldReturnExpectedResponseJson() {
        CompetenceRequestDto request = CompetenceTestData.createCompetenceRequestDto();
        int expectedSkillsSize = request.skills().size();
        int expectedLanguagesSize = request.languages().size();
        HttpEntity<CompetenceRequestDto> requestEntity = new HttpEntity<>(request, getAuthHeader());

        ResponseEntity<CompetenceResponseDto> responseEntity = restTemplate.exchange(
                CV_COMPETENCE_URL_TEMPLATE,
                HttpMethod.POST,
                requestEntity,
                CompetenceResponseDto.class);

        CompetenceResponseDto actualResponse = responseEntity.getBody();

        assertThat(actualResponse.skills()).hasSize(expectedSkillsSize);
        assertThat(actualResponse.languages()).hasSize(expectedLanguagesSize);
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
