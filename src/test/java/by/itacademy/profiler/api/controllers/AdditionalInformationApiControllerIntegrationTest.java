package by.itacademy.profiler.api.controllers;

import by.itacademy.profiler.MysqlSQLTestContainerExtension;
import by.itacademy.profiler.persistence.repository.AdditionalInformationRepository;
import by.itacademy.profiler.usecasses.dto.AdditionalInformationRequestDto;
import by.itacademy.profiler.usecasses.dto.AdditionalInformationResponseDto;
import by.itacademy.profiler.usecasses.dto.AwardDto;
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
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;

import java.time.YearMonth;
import java.util.List;
import java.util.Map;

import static by.itacademy.profiler.util.AdditionalInfoTestData.CV_ADDITIONAL_INFORMATION_URL_TEMPLATE;
import static by.itacademy.profiler.util.AdditionalInfoTestData.createAdditionalInformationRequestDto;
import static by.itacademy.profiler.util.AdditionalInfoTestData.createAdditionalInformationResponseDto;
import static by.itacademy.profiler.util.AdditionalInfoTestData.createAwardDto;
import static by.itacademy.profiler.util.AuthenticationTestData.AUTH_URL_TEMPLATE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
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

    @Test
    @SqlGroup({
            @Sql(scripts = "classpath:testdata/add_additional_info_test_data.sql",
                    executionPhase = ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "classpath:testdata/clear_additional_info_test_data.sql",
                    executionPhase = ExecutionPhase.AFTER_TEST_METHOD)})
    void shouldReturn200AndCorrectJsonWhenGetSuccessful() throws JsonProcessingException {
        HttpEntity<String> requestEntity = new HttpEntity<>(getAuthHeader());

        ResponseEntity<AdditionalInformationResponseDto> responseEntity = restTemplate.exchange(
                CV_ADDITIONAL_INFORMATION_URL_TEMPLATE,
                HttpMethod.GET,
                requestEntity,
                AdditionalInformationResponseDto.class);

        AdditionalInformationResponseDto actualResponse = responseEntity.getBody();

        AdditionalInformationResponseDto expectedResponse = createAdditionalInformationResponseDtoFromSqlScript();
        assertEquals(expectedResponse, actualResponse);
        assertEquals(OK, responseEntity.getStatusCode());
        assertEquals(APPLICATION_JSON, responseEntity.getHeaders().getContentType());
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

    private AdditionalInformationResponseDto createAdditionalInformationResponseDtoFromSqlScript() {
        List<AwardDto> awards = List.of(
                createAwardDto().withDate(YearMonth.parse("2010-05"))
                        .withTitle("Title1")
                        .withIssuer("Issuer1")
                        .withDescription("Very important award")
                        .withLink("https://example.com/link1")
                        .build(),
                createAwardDto().withDate(YearMonth.parse("2020-01"))
                        .withTitle("Title2")
                        .withIssuer("Issuer2")
                        .withDescription("Another very important award")
                        .withLink("https://example.com/link2")
                        .build());
        return createAdditionalInformationResponseDto()
                .withAdditionalInfo("Additional information")
                .withHobby("Photography")
                .withAwards(awards)
                .build();
    }
}
