package by.itacademy.profiler.api.controllers;

import by.itacademy.profiler.MysqlSQLTestContainerExtension;
import by.itacademy.profiler.persistence.model.Skill;
import by.itacademy.profiler.persistence.repository.SkillRepository;
import by.itacademy.profiler.usecasses.dto.SkillResponseDto;
import by.itacademy.profiler.usecasses.mapper.SkillMapper;
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
import java.util.Optional;

import static by.itacademy.profiler.api.controllers.SkillApiControllerTest.SKILLS_URL_TEMPLATE;
import static by.itacademy.profiler.util.AuthenticationTestData.AUTH_URL_TEMPLATE;
import static by.itacademy.profiler.util.AuthenticationTestData.createLoginRequestHttpEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(MysqlSQLTestContainerExtension.class)
class SkillApiControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SkillMapper skillMapper;

    @Autowired
    private SkillRepository skillRepository;

    private final String SKILLS_URL_TEMPLATE_BY_POSITION = "/api/v1/skills?position_id=1";
    private final Integer EXPECTED_NUMBER_OF_SKILLS = 358;
    private final Integer EXPECTED_NUMBER_OF_SKILLS_BY_POSITION = 8;

    @Test
    void shouldReturnExpectedSkillResponseJsonAnd200WhenGetListOfSkills() throws Exception {
        ResponseEntity<String> response = restTemplate.exchange(
                SKILLS_URL_TEMPLATE,
                HttpMethod.GET,
                getAuthHttpEntity(),
                String.class);

        List<SkillResponseDto> result = objectMapper.readValue(response.getBody(), new TypeReference<>() {});
        Optional<Skill> skill = skillRepository.findById(1L);
        if (skill.isPresent()) {
            SkillResponseDto skillResponseDto = skillMapper.fromEntityToDto(skill.get());
            assertEquals(skillResponseDto.name(), result.get(0).name());
        }

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        assertEquals(EXPECTED_NUMBER_OF_SKILLS, result.size());
    }

    @Test
    void shouldReturnExpectedSkillResponseJsonAnd200WhenGetListOfSkillsByValidFirstPosition() throws Exception {
        ResponseEntity<String> response = restTemplate.exchange(
                SKILLS_URL_TEMPLATE_BY_POSITION,
                HttpMethod.GET,
                getAuthHttpEntity(),
                String.class);

        List<SkillResponseDto> result = objectMapper.readValue(response.getBody(), new TypeReference<>() {});

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        assertEquals(EXPECTED_NUMBER_OF_SKILLS_BY_POSITION, result.size());
    }

    @SneakyThrows
    private HttpEntity<String> getAuthHttpEntity() {
        HttpEntity<Map<String, String>> requestHttpAuthEntity = createLoginRequestHttpEntity();

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
        return new HttpEntity<>(headers);
    }
}
