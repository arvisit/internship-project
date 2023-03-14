package by.itacademy.profiler.usecasses.dto;

import by.itacademy.profiler.usecasses.annotation.CountryValidation;
import by.itacademy.profiler.usecasses.annotation.PositionValidation;
import by.itacademy.profiler.usecasses.annotation.UserImageValidation;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

import static by.itacademy.profiler.usecasses.util.ValidationConstants.REGEXP_VALIDATE_CITY;
import static by.itacademy.profiler.usecasses.util.ValidationConstants.REGEXP_VALIDATE_NAME;
import static by.itacademy.profiler.usecasses.util.ValidationConstants.REGEXP_VALIDATE_SURNAME;

public record CurriculumVitaeRequestDto(
        @UserImageValidation(toValidate = UserImageValidation.ValidatedDto.CV_DTO) String imageUuid,
        @Length(max = 50, message = "Maximum length of name is 50 symbols")
        @NotNull(message = "Field must to be filled")
        @Pattern(regexp = REGEXP_VALIDATE_NAME, message = "Invalid name")
        String name,
        @Length(max = 50, message = "Maximum length of surname is 50 symbols")
        @NotNull(message = "Field must to be filled")
        @Pattern(regexp = REGEXP_VALIDATE_SURNAME, message = "Invalid surname")
        String surname,
        @NotNull
        @PositionValidation
        Long positionId,
        @NotNull
        @CountryValidation
        Long countryId,
        @Length(max = 50, message = "Maximum length of city name is 50 symbols")
        @NotNull(message = "Field must to be filled")
        @Pattern(regexp = REGEXP_VALIDATE_CITY, message = "Invalid city name")
        String city,
        Boolean isReadyToRelocate,
        Boolean isReadyForRemoteWork) implements Serializable {
}