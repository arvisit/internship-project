package by.itacademy.profiler.usecasses.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public record AuthenticationRequestDto(
        @NotBlank(message = "Email is mandatory!")
        @Email(message = "Invalid e-mail address")
        String email,

        @NotBlank(message = "Password is mandatory!")
        String password) implements Serializable {
}
