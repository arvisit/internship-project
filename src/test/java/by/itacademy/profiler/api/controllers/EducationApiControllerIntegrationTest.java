package by.itacademy.profiler.api.controllers;

import by.itacademy.profiler.MysqlSQLTestContainerExtension;
import by.itacademy.profiler.persistence.repository.CourseRepository;
import by.itacademy.profiler.persistence.repository.MainEducationRepository;
import by.itacademy.profiler.usecasses.dto.EducationRequestDto;
import by.itacademy.profiler.usecasses.dto.EducationResponseDto;
import by.itacademy.profiler.util.AuthenticationTestData;
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

import java.util.List;
import java.util.Map;

import static by.itacademy.profiler.util.AuthenticationTestData.AUTH_URL_TEMPLATE;
import static by.itacademy.profiler.util.CourseTestData.createCourseRequestDto;
import static by.itacademy.profiler.util.EducationTestData.CV_EDUCATIONS_URL_TEMPLATE;
import static by.itacademy.profiler.util.EducationTestData.CV_UUID_FOR_EDUCATIONS;
import static by.itacademy.profiler.util.EducationTestData.createEducationRequestDto;
import static by.itacademy.profiler.util.MainEducationTestData.createMainEducationRequestDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@ExtendWith(MysqlSQLTestContainerExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EducationApiControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MainEducationRepository mainEducationRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Test
    void shouldReturn201AndJsonContentTypeWhenAddEducationInfoSuccessfully() {
        EducationRequestDto request = createEducationRequestDto().build();
        HttpEntity<EducationRequestDto> requestEntity = new HttpEntity<>(request, getAuthHeader());

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                CV_EDUCATIONS_URL_TEMPLATE,
                HttpMethod.POST,
                requestEntity,
                String.class,
                CV_UUID_FOR_EDUCATIONS);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());

        mainEducationRepository.deleteAll();
        courseRepository.deleteAll();
    }

    @Test
    void shouldReturnExpectedResponseJsonWhenAddEducationInfoSuccessfully() {
        EducationRequestDto request = createEducationRequestDto().build();
        int expectedMainEducationsSize = request.mainEducations().size();
        int expectedCoursesSize = request.courses().size();
        HttpEntity<EducationRequestDto> requestEntity = new HttpEntity<>(request, getAuthHeader());

        ResponseEntity<EducationResponseDto> responseEntity = restTemplate.exchange(
                CV_EDUCATIONS_URL_TEMPLATE,
                HttpMethod.POST,
                requestEntity,
                EducationResponseDto.class,
                CV_UUID_FOR_EDUCATIONS);

        EducationResponseDto actualResponse = responseEntity.getBody();

        assertThat(actualResponse.mainEducations()).hasSize(expectedMainEducationsSize);
        assertThat(actualResponse.courses()).hasSize(expectedCoursesSize);

        mainEducationRepository.deleteAll();
        courseRepository.deleteAll();
    }

    @Test
    void shouldRewriteEducationInfoWhenAddEducationInfoTwice() {
        EducationRequestDto firstRequest = createEducationRequestDto()
                .withMainEducations(
                        List.of(createMainEducationRequestDto().withSequenceNumber(1).build(),
                                createMainEducationRequestDto().withSequenceNumber(2).build()))
                .withCourses(
                        List.of(createCourseRequestDto().withSequenceNumber(1).build(),
                                createCourseRequestDto().withSequenceNumber(2).build()))
                .build();
        HttpEntity<EducationRequestDto> requestEntity = new HttpEntity<>(firstRequest, getAuthHeader());

        ResponseEntity<EducationResponseDto> responseEntity = restTemplate.exchange(
                CV_EDUCATIONS_URL_TEMPLATE,
                HttpMethod.POST,
                requestEntity,
                EducationResponseDto.class,
                CV_UUID_FOR_EDUCATIONS);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());

        long mainEducationsCountAfterFirstRequest = mainEducationRepository.count();
        long coursesCountAfterFirstRequest = courseRepository.count();

        EducationRequestDto secondRequest = createEducationRequestDto().build();
        requestEntity = new HttpEntity<>(secondRequest, getAuthHeader());

        responseEntity = restTemplate.exchange(
                CV_EDUCATIONS_URL_TEMPLATE,
                HttpMethod.POST,
                requestEntity,
                EducationResponseDto.class,
                CV_UUID_FOR_EDUCATIONS);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());

        long mainEducationsCountAfterSecondRequest = mainEducationRepository.count();
        long coursesCountAfterSecondRequest = courseRepository.count();

        assertTrue(mainEducationsCountAfterFirstRequest > mainEducationsCountAfterSecondRequest);
        assertTrue(coursesCountAfterFirstRequest > coursesCountAfterSecondRequest);

        mainEducationRepository.deleteAll();
        courseRepository.deleteAll();
    }

    @Test
    @Sql(scripts = "classpath:testdata/add_education_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldReturnExpectedResponseJsonWhenGetEducationSuccessful() {
        HttpEntity<String> requestEntity = new HttpEntity<>(getAuthHeader());

        String expectedInstitution = "GSU";
        String expectedSchoolName = "IT-ACADEMY";

        ResponseEntity<EducationResponseDto> responseEntity = restTemplate.exchange(
                CV_EDUCATIONS_URL_TEMPLATE,
                HttpMethod.GET,
                requestEntity,
                EducationResponseDto.class,
                CV_UUID_FOR_EDUCATIONS);

        EducationResponseDto actualResponse = responseEntity.getBody();

        assertThat(actualResponse.courses()).hasSize(2);
        assertThat(actualResponse.mainEducations()).hasSize(2);
        assertEquals(actualResponse.courses().get(0).school(),expectedSchoolName);
        assertEquals(actualResponse.mainEducations().get(0).institution(),expectedInstitution);
        mainEducationRepository.deleteAll();
        courseRepository.deleteAll();
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
