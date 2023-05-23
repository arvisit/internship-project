package by.itacademy.profiler.usecasses.dto;


import by.itacademy.profiler.usecasses.annotation.PhoneCodeValidation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;

import static by.itacademy.profiler.usecasses.util.ValidationConstants.REGEXP_VALIDATE_CELL_PHONE;
import static by.itacademy.profiler.usecasses.util.ValidationConstants.REGEXP_VALIDATE_EMAIL;
import static by.itacademy.profiler.usecasses.util.ValidationConstants.REGEXP_VALIDATE_NOT_BLANK_BUT_NULL;

@Builder(setterPrefix = "with")
public record ContactsDto(@PhoneCodeValidation
                          @NotNull(message = "Field must not be empty")
                          @Schema(defaultValue = "1", description = "Phone code id")
                          Long phoneCodeId,
                          @Length(max = 25, message = "The user's phone number is too long, the max number of symbols is 25")
                          @Pattern(regexp = REGEXP_VALIDATE_CELL_PHONE, message = "Invalid cell phone number. Example of the correct variant: 29233XXXX")
                          @NotBlank(message = "Field must not be empty")
                          @Schema(defaultValue = "291112233", description = "Phone number")
                          String phoneNumber,
                          @Length(max = 50, message = "The email is too long, the max number of symbols is 50")
                          @Pattern(regexp = REGEXP_VALIDATE_EMAIL, message = "Invalid email. Example of the correct variant: example@example.com")
                          @NotNull(message = "Field must not be null")
                          @Schema(defaultValue = "user@gmail.com", description = "Email address")
                          String email,
                          @Length(max = 50, message = "The Skype address is too long, the max number of symbols is 50")
                          @Pattern(regexp = REGEXP_VALIDATE_NOT_BLANK_BUT_NULL, message = "Field must be filled or null")
                          @Schema(defaultValue = "live:irina_18", description = "Skype login")
                          String skype,
                          @Length(max = 255, message = "The LinkedIn link is too long, the max number of symbols is 255")
                          @NotBlank(message = "Field must not be empty")
                          @Schema(defaultValue = "https://www.linkedin.com/example", description = "LinkedIn link")
                          String linkedin,
                          @Length(max = 255, message = "The portfolio link is too long, the max number of symbols is 255")
                          @Pattern(regexp = REGEXP_VALIDATE_NOT_BLANK_BUT_NULL, message = "Field must be filled or null")
                          @Schema(defaultValue = "https://www.example.com/", description = "Portfolio link")
                          String portfolio) {
}
