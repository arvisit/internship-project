package by.itacademy.profiler.api.controllers;


import by.itacademy.profiler.api.exception.BadRequestException;
import by.itacademy.profiler.security.jwt.JwtTokenProvider;
import by.itacademy.profiler.usecasses.dto.AuthenticationRequestDto;
import by.itacademy.profiler.usecasses.dto.AuthenticationUserDto;
import by.itacademy.profiler.usecasses.impl.AuthenticationServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth/")
public class AuthenticationApiController {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationServiceImpl authenticationService;

    @PostMapping("login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthenticationRequestDto requestDto) {
        AuthenticationUserDto user = authenticationService.findByEmailAndPassword(requestDto);
        if (user == null) {
            throw new BadRequestException("Wrong email or password");
        }
        Map<Object, Object> response = new HashMap<>();
        response.put("username", user.email());
        response.put("token", jwtTokenProvider.createToken(user));
        return ResponseEntity.ok(response);
    }
}
