package by.itacademy.profiler.usecasses.impl;

import by.itacademy.profiler.persistence.model.User;
import by.itacademy.profiler.persistence.repository.UserRepository;
import by.itacademy.profiler.usecasses.AuthenticationService;
import by.itacademy.profiler.usecasses.dto.AuthenticationRequestDto;
import by.itacademy.profiler.usecasses.dto.AuthenticationUserDto;
import by.itacademy.profiler.usecasses.mapper.AuthenticationUserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final AuthenticationUserMapper authenticationUserMapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthenticationUserDto findByEmailAndPassword(AuthenticationRequestDto requestDto) {
        User user = userRepository.findByEmail(requestDto.email());
        AuthenticationUserDto authenticationUserDto = authenticationUserMapper.userToAuthenticationUserDto(user);
        if (user != null) {
            if (passwordEncoder.matches(requestDto.password(), user.getPassword())) {
                log.info("IN findByEmailAndPassword - authenticationUserDto: {} found by email: {}", authenticationUserDto, authenticationUserDto.email());
                return authenticationUserDto;
            }
        }
        log.info("IN findByEmailAndPassword - Invalid username or password");
        return null;
    }
}
