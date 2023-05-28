package by.itacademy.profiler.usecasses.dto;


import by.itacademy.profiler.usecasses.annotation.CountryValidation;
import by.itacademy.profiler.usecasses.annotation.PhoneCodeValidation;
import by.itacademy.profiler.usecasses.annotation.PositionValidation;
import by.itacademy.profiler.usecasses.annotation.UserImageValidation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;

import static by.itacademy.profiler.usecasses.util.ValidationConstants.REGEXP_VALIDATE_CELL_PHONE;
import static by.itacademy.profiler.usecasses.util.ValidationConstants.REGEXP_VALIDATE_EMAIL;
import static by.itacademy.profiler.usecasses.util.ValidationConstants.REGEXP_VALIDATE_NAME;
import static by.itacademy.profiler.usecasses.util.ValidationConstants.REGEXP_VALIDATE_SURNAME;

@Builder(setterPrefix = "with")
public record UserProfileDto(@Length(max = 50, message = "The name is too long, the max number of symbols is 50")
                             @Pattern(regexp = REGEXP_VALIDATE_NAME, message = "Invalid name")
                             @Schema(defaultValue = "Jeffrey", description = "User name")
                             String name,
                             @Length(max = 50, message = "The surname is too long, the max number of symbols is 50")
                             @Pattern(regexp = REGEXP_VALIDATE_SURNAME, message = "Invalid surname")
                             @Schema(defaultValue = "Lebowski", description = "User surname")
                             String surname,
                             @CountryValidation
                             @Schema(defaultValue = "1 ", description = "Country id")
                             Long countryId,
                             @Length(max = 50, message = "The email is too long, the max number of symbols is 50")
                             @Pattern(regexp = REGEXP_VALIDATE_EMAIL,
                                     message = "Invalid email. Example of the correct variant: example@example.com ")
                             @Schema(defaultValue = "Lebowski@gmail.com", description = "Email address")
                             String email,
                             @PhoneCodeValidation
                             @NotNull(message = "Field must not be empty")
                             @Schema(defaultValue = "1", description = "Phone code id")
                             Long phoneCodeId,
                             @Length(max = 25, message = "The user's phone number is too long, the max number of symbols is 25")
                             @Pattern(regexp = REGEXP_VALIDATE_CELL_PHONE,
                                     message = "Invalid cell phone number. Example of the correct variant: 29233XXXX")
                             @Schema(defaultValue = "331112233", description = "User cell phone number")
                             String cellPhone,
                             @PositionValidation
                             @Schema(defaultValue = "1 ", description = "Position id")
                             Long positionId,
                             @UserImageValidation(toValidate = UserImageValidation.ValidatedDto.PROFILE_DTO)
                             @Schema(defaultValue = "601f41a2-f850-4e7a-b1ac-096f3202dfcd", description = "User profile image uuid")
                             String profileImageUuid) {
}