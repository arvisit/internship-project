package by.itacademy.profiler.usecasses.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.io.Serializable;

import static by.itacademy.profiler.usecasses.util.ValidationConstants.REGEXP_VALIDATE_EMAIL;

public record AuthenticationRequestDto(
        @NotBlank(message = "Email is mandatory!")
        @Pattern(regexp = REGEXP_VALIDATE_EMAIL, message = "Invalid e-mail address")
        @Schema(defaultValue = "user@gmail.com", description = "Email address")
        String email,

        @NotBlank(message = "Password is mandatory!")
        @Schema(defaultValue = "2222", description = "Password")
        String password) implements Serializable {
}
