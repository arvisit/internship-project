package by.itacademy.profiler.usecasses.dto;

import lombok.Builder;

import java.io.Serializable;

@Builder(setterPrefix = "with")
public record UserProfileResponseDto(String name,
                                     String surname,
                                     Long countryId, String country,
                                     String email,
                                     Long phoneCodeId, Integer phoneCode,
                                     String cellPhone,
                                     Long positionId, String position,
                                     String profileImageUuid,
                                     String uniqueStudentIdentifier) implements Serializable {
}