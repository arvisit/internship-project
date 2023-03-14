package by.itacademy.profiler.usecasses.dto;


import by.itacademy.profiler.usecasses.annotation.PhoneCodeValidation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

import static by.itacademy.profiler.usecasses.util.ValidationConstants.REGEXP_VALIDATE_CELL_PHONE;
import static by.itacademy.profiler.usecasses.util.ValidationConstants.REGEXP_VALIDATE_EMAIL;
import static by.itacademy.profiler.usecasses.util.ValidationConstants.REGEXP_VALIDATE_NOT_BLANK_BUT_NULL;

public record ContactsDto(@PhoneCodeValidation
                          @NotNull(message = "Field must not be empty")
                          Long phoneCodeId,
                          @Length(max = 25, message = "The user's phone number is too long, the max number of symbols is 25")
                          @Pattern(regexp = REGEXP_VALIDATE_CELL_PHONE, message = "Invalid cell phone number. Example of the correct variant: 29233XXXX")
                          @NotBlank(message = "Field must not be empty")
                          String phoneNumber,
                          @Length(max = 50, message = "The email is too long, the max number of symbols is 50")
                          @Pattern(regexp = REGEXP_VALIDATE_EMAIL, message = "Invalid email. Example of the correct variant: example@example.com")
                          @NotNull(message = "Field must not be null")
                          String email,
                          @Length(max = 50, message = "The Skype address is too long, the max number of symbols is 50")
                          @Pattern(regexp = REGEXP_VALIDATE_NOT_BLANK_BUT_NULL, message = "Field must be filled or null")
                          String skype,
                          @Length(max = 255, message = "The LinkedIn link is too long, the max number of symbols is 255")
                          @NotBlank(message = "Field must not be empty")
                          String linkedin,
                          @Length(max = 255, message = "The portfolio link is too long, the max number of symbols is 255")
                          @Pattern(regexp = REGEXP_VALIDATE_NOT_BLANK_BUT_NULL, message = "Field must be filled or null")
                          String portfolio) {
}
