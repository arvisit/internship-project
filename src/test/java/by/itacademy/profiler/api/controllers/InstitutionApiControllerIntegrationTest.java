package by.itacademy.profiler.api.controllers;

import by.itacademy.profiler.MysqlSQLTestContainerExtension;
import by.itacademy.profiler.usecasses.dto.InstitutionResponseDto;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static by.itacademy.profiler.util.AuthenticationTestData.AUTH_URL_TEMPLATE;
import static by.itacademy.profiler.util.InstitutionsTestData.INSTITUTION_URL_TEMPLATE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(MysqlSQLTestContainerExtension.class)
class InstitutionApiControllerIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturn200AndJsonContentTypeWhenGetListOfInstitutions() {
        ResponseEntity<String> response = restTemplate.exchange(
                INSTITUTION_URL_TEMPLATE,
                HttpMethod.GET,
                getAuthHttpEntity(),
                String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
    }

    @Test
    void shouldReturnNotEmptyListWhenGetListOfInstitutions() {
        ResponseEntity<List<InstitutionResponseDto>> responseEntity = restTemplate.exchange(
                INSTITUTION_URL_TEMPLATE,
                HttpMethod.GET,
                getAuthHttpEntity(),
                new ParameterizedTypeReference<>() {}
        );

        List<InstitutionResponseDto> actualResponse = responseEntity.getBody();
        assertNotNull(actualResponse);
    }

    @Test
    void shouldReturnExpectedInstitutionResponseJson() throws JsonProcessingException {
        ResponseEntity<String> response = restTemplate.exchange(
                INSTITUTION_URL_TEMPLATE,
                HttpMethod.GET,
                getAuthHttpEntity(),
                String.class
        );

        List<InstitutionResponseDto> actualResponse = objectMapper.readValue(response.getBody(), new TypeReference<>() {});
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertThat(actualResponse).hasSize(92);
    }

    @SneakyThrows
    private HttpEntity<String> getAuthHttpEntity() {
        HttpEntity<Map<String, String>> requestHttpAuthEntity = AuthenticationTestData.createLoginRequestHttpEntity();

        ResponseEntity<Map<String,String>> responseEntity = restTemplate.exchange(
                AUTH_URL_TEMPLATE,
                HttpMethod.POST,
                requestHttpAuthEntity,
                new ParameterizedTypeReference<>() {}
        );

        Map<String, String> responseMap  = responseEntity.getBody();
        String token = "Bearer " + responseMap.get("token");

        HttpHeaders headers = new HttpHeaders();
        headers.set(AUTHORIZATION, token);
        return new HttpEntity<>(headers);
    }
}
