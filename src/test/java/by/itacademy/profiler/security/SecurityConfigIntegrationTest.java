package by.itacademy.profiler.security;

import by.itacademy.profiler.MysqlSQLTestContainerExtension;
import by.itacademy.profiler.usecasses.dto.AuthenticationRequestDto;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@ExtendWith(MysqlSQLTestContainerExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SqlGroup({
        @Sql(scripts = "classpath:testdata/add_users_with_different_roles.sql",
                executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "classpath:testdata/clear_users_with_different_roles.sql",
                executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class SecurityConfigIntegrationTest {

    private static final int ACTUATOR_ENDPOINTS_COUNT = 4;

    private static final String[] ACTUATOR_ENDPOINT_IDS = { "self", "health", "health-path", "info" };

    private static final String ACTUATOR_RESPONSE_LINKS_KEY = "_links";

    private static final String AUTHENTICATION_RESPONSE_TOKEN_KEY = "token";

    private static final String IF_FORBIDDEN_MESSAGE_KEY = "error";

    private static final String IF_FORBIDDEN_MESSAGE = "Forbidden";

    private static final String AUTHENTICATION_RESPONSE_USERNAME_KEY = "username";

    private static final String AUTHENTICATION_WITH_INVALID_CREDENTIALS_MESSAGE = "Wrong email or password";

    private static final String RESPONSE_MESSAGE_KEY = "message";

    private static final String ACTUATOR_URL = "/actuator";

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturn200AndJsonContentTypeWhenAuthenticateWithValidCredentials() throws Exception {
        AuthenticationRequestDto request = createCredentialsForUserWithRoleUserOnly();
        HttpEntity<AuthenticationRequestDto> requestEntity = new HttpEntity<>(request);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                AUTH_URL_TEMPLATE,
                HttpMethod.POST,
                requestEntity,
                String.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
    }

    @Test
    void shouldReturn200AndTokenWhenAuthenticateWithValidCredentials() throws Exception {
        AuthenticationRequestDto request = createCredentialsForUserWithRoleUserOnly();
        HttpEntity<AuthenticationRequestDto> requestEntity = new HttpEntity<>(request);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                AUTH_URL_TEMPLATE,
                HttpMethod.POST,
                requestEntity,
                String.class);

        Map<String, String> actualResponse = objectMapper.readValue(responseEntity.getBody(), new TypeReference<>() {});

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertThat(actualResponse).containsKeys(AUTHENTICATION_RESPONSE_USERNAME_KEY,
                AUTHENTICATION_RESPONSE_TOKEN_KEY);
        assertEquals(request.email(), actualResponse.get(AUTHENTICATION_RESPONSE_USERNAME_KEY));
        assertNotNull(actualResponse.get(AUTHENTICATION_RESPONSE_TOKEN_KEY));
    }

    @Test
    void shouldReturn400AndJsonContentTypeWhenAuthenticateWithInvalidCredentials() throws Exception {
        AuthenticationRequestDto request = createInvalidCredentials();
        HttpEntity<AuthenticationRequestDto> requestEntity = new HttpEntity<>(request);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                AUTH_URL_TEMPLATE,
                HttpMethod.POST,
                requestEntity,
                String.class);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
    }

    @Test
    void shouldReturn400AndExpectedMessageWhenAuthenticateWithInvalidCredentials() throws Exception {
        AuthenticationRequestDto request = createInvalidCredentials();
        HttpEntity<AuthenticationRequestDto> requestEntity = new HttpEntity<>(request);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                AUTH_URL_TEMPLATE,
                HttpMethod.POST,
                requestEntity,
                String.class);

        Map<String, String> actualResponse = objectMapper.readValue(responseEntity.getBody(), new TypeReference<>() {});
        String expectedMessage = AUTHENTICATION_WITH_INVALID_CREDENTIALS_MESSAGE;

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertThat(actualResponse).containsKey(RESPONSE_MESSAGE_KEY);
        assertEquals(expectedMessage, actualResponse.get(RESPONSE_MESSAGE_KEY));
    }

    @Test
    void shouldReturn200AndJsonContentTypeWhenUseActuatorWithUserApiRole() throws Exception {
        AuthenticationRequestDto credentials = createCredentialsForUserWithRoleUserApiOnly();
        HttpEntity<AuthenticationRequestDto> requestEntity = new HttpEntity<>(getAuthHeader(credentials));

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                ACTUATOR_URL,
                HttpMethod.GET,
                requestEntity,
                String.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
    }

    @Test
    void shouldReturn200AndAvailableActuatorsWhenUseActuatorWithUserApiRole() throws Exception {
        AuthenticationRequestDto credentials = createCredentialsForUserWithRoleUserApiOnly();
        HttpEntity<AuthenticationRequestDto> requestEntity = new HttpEntity<>(getAuthHeader(credentials));

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                ACTUATOR_URL,
                HttpMethod.GET,
                requestEntity,
                String.class);

        Map<String, Map<String, Map<String, String>>> actualResponse = objectMapper.readValue(responseEntity.getBody(),
                new TypeReference<>() {});
        Map<String, Map<String, String>> responseLinks = actualResponse.get(ACTUATOR_RESPONSE_LINKS_KEY);
        String[] expectedEndpointIds = ACTUATOR_ENDPOINT_IDS;

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertThat(responseLinks).containsKeys(expectedEndpointIds).hasSize(ACTUATOR_ENDPOINTS_COUNT);
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
    }

    @Test
    void shouldReturn403AndJsonContentTypeWhenUseActuatorWithUserRole() throws Exception {
        AuthenticationRequestDto credentials = createCredentialsForUserWithRoleUserOnly();
        HttpEntity<AuthenticationRequestDto> requestEntity = new HttpEntity<>(getAuthHeader(credentials));

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                ACTUATOR_URL,
                HttpMethod.GET,
                requestEntity,
                String.class);

        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
    }

    @Test
    void shouldReturn403AndExpectedMessageWhenUseActuatorWithUserRole() throws Exception {
        AuthenticationRequestDto credentials = createCredentialsForUserWithRoleUserOnly();
        HttpEntity<AuthenticationRequestDto> requestEntity = new HttpEntity<>(getAuthHeader(credentials));

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                ACTUATOR_URL,
                HttpMethod.GET,
                requestEntity,
                String.class);

        Map<String, String> actualResponse = objectMapper.readValue(responseEntity.getBody(), new TypeReference<>() {});
        String expectedMessage = IF_FORBIDDEN_MESSAGE;

        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
        assertThat(actualResponse).containsKey(IF_FORBIDDEN_MESSAGE_KEY);
        assertEquals(expectedMessage, actualResponse.get(IF_FORBIDDEN_MESSAGE_KEY));
    }

    private AuthenticationRequestDto createInvalidCredentials() {
        return new AuthenticationRequestDto("no-such-email@mail.com", "password");
    }

    private AuthenticationRequestDto createCredentialsForUserWithRoleUserOnly() {
        return new AuthenticationRequestDto("user-role@mail.com", "user-role");
    }

    private AuthenticationRequestDto createCredentialsForUserWithRoleUserApiOnly() {
        return new AuthenticationRequestDto("user-api-role@mail.com", "user-api-role");
    }

    private HttpHeaders getAuthHeader(AuthenticationRequestDto credentials) {
        HttpEntity<AuthenticationRequestDto> requestHttpAuthEntity = new HttpEntity<>(credentials);

        ResponseEntity<Map<String, String>> responseEntity = restTemplate.exchange(
                AUTH_URL_TEMPLATE,
                HttpMethod.POST,
                requestHttpAuthEntity,
                new ParameterizedTypeReference<>() {});

        Map<String, String> responseMap = responseEntity.getBody();
        String token = "Bearer " + responseMap.get(AUTHENTICATION_RESPONSE_TOKEN_KEY);

        HttpHeaders headers = new HttpHeaders();
        headers.set(AUTHORIZATION, token);
        return headers;
    }
}
