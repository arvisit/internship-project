package by.itacademy.profiler.util;

import by.itacademy.profiler.persistence.model.RoleNameEnum;
import by.itacademy.profiler.usecasses.dto.AuthenticationRequestDto;
import by.itacademy.profiler.usecasses.dto.AuthenticationUserDto;
import org.springframework.http.HttpEntity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class AuthenticationTestData {

    public static final String AUTH_URL_TEMPLATE = "/api/v1/auth/login";

    private AuthenticationTestData() {
    }

    public static AuthenticationUserDto createAuthenticationUserDto() {
        Set<RoleNameEnum> roleNames = new HashSet<>();
        roleNames.add(RoleNameEnum.ROLE_ADMIN);
        return new AuthenticationUserDto("admin@mail.ru", roleNames);
    }

    public static AuthenticationRequestDto createAuthenticationRequestDto() {
        return new AuthenticationRequestDto("admin@mail.ru", "1234");
    }

    public static HttpEntity<Map<String, String>> createLoginRequestHttpEntity() {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("email", "admin@mail.ru");
        requestBody.put("password", "1234");
        return new HttpEntity<>(requestBody);
    }
}

