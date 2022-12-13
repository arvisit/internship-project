package by.itacademy.profiler.usecasses;

import by.itacademy.profiler.usecasses.dto.AuthenticationRequestDto;
import by.itacademy.profiler.usecasses.dto.AuthenticationUserDto;

public interface AuthenticationService {

    AuthenticationUserDto findByEmailAndPassword(AuthenticationRequestDto requestDto);
}
