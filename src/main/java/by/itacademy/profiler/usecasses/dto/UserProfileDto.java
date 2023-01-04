package by.itacademy.profiler.usecasses.dto;


import jakarta.validation.constraints.Pattern;

import static by.itacademy.profiler.usecasses.util.ValidationConstants.REGEXP_VALIDATE_CELL_PHONE;
import static by.itacademy.profiler.usecasses.util.ValidationConstants.REGEXP_VALIDATE_EMAIL;
import static by.itacademy.profiler.usecasses.util.ValidationConstants.REGEXP_VALIDATE_NAME;
import static by.itacademy.profiler.usecasses.util.ValidationConstants.REGEXP_VALIDATE_SURNAME;

public record UserProfileDto(@Pattern(regexp = REGEXP_VALIDATE_NAME,
                                     message = "The user name was entered incorrectly, it is allowed to use Latin letters, dashes and spaces.")
                             String name,
                             @Pattern(regexp = REGEXP_VALIDATE_SURNAME,
                                     message = "The surname of the user was entered incorrectly, it is allowed to use Latin letters, dashes and spaces.")
                             String surname,
                             Long countryId,
                             @Pattern(regexp = REGEXP_VALIDATE_EMAIL,
                                     message = "The email of the user was entered incorrectly. Email address must have four parts: Recipient name, @ symbol, Domain name, Top-level domain")
                             String email,
                             Long phoneCodeId,
                             @Pattern(regexp = REGEXP_VALIDATE_CELL_PHONE,
                                     message = "The user's phone number was entered incorrectly. It is not allowed to use a dashes and a spaces between numbers.")
                             String cellPhone,
                             Long positionId) {
}