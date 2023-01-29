package by.itacademy.profiler.usecasses.dto;


import by.itacademy.profiler.usecasses.annotation.CountryValidation;
import by.itacademy.profiler.usecasses.annotation.PhoneCodeValidation;
import by.itacademy.profiler.usecasses.annotation.PositionValidation;
import by.itacademy.profiler.usecasses.annotation.UserImageValidation;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UUID;

import static by.itacademy.profiler.usecasses.util.ValidationConstants.REGEXP_VALIDATE_CELL_PHONE;
import static by.itacademy.profiler.usecasses.util.ValidationConstants.REGEXP_VALIDATE_EMAIL;
import static by.itacademy.profiler.usecasses.util.ValidationConstants.REGEXP_VALIDATE_NAME;
import static by.itacademy.profiler.usecasses.util.ValidationConstants.REGEXP_VALIDATE_SURNAME;

public record UserProfileDto(@Length(max = 50, message = "The name is too long, the max number of symbols is 50")
                             @Pattern(regexp = REGEXP_VALIDATE_NAME, message = "Invalid name")
                             String name,
                             @Length(max = 50, message = "The surname is too long, the max number of symbols is 50")
                             @Pattern(regexp = REGEXP_VALIDATE_SURNAME, message = "Invalid surname")
                             String surname,
                             @CountryValidation
                             Long countryId,
                             @Length(max = 50, message = "The email is too long, the max number of symbols is 50")
                             @Pattern(regexp = REGEXP_VALIDATE_EMAIL,
                                     message = "Invalid email. Example of the correct variant: example@example.com ")
                             String email,
                             @PhoneCodeValidation
                             Long phoneCodeId,
                             @Length(max = 25, message = "The user's phone number is too long, the max number of symbols is 25")
                             @Pattern(regexp = REGEXP_VALIDATE_CELL_PHONE,
                                     message = "Invalid cell phone number. Example of the correct variant: 29233XXXX")
                             String cellPhone,
                             @PositionValidation
                             Long positionId,
                             @UUID(allowNil = false)
                             @UserImageValidation
                             String profileImageUuid) {
}