package by.itacademy.profiler.usecasses.dto;

import java.io.Serializable;

public record CurriculumVitaeResponseDto(String uuid, String imageUuid, String name, String surname, Long positionId,
                                         String position, Long countryId, String country, String city,
                                         boolean isReadyToRelocate, boolean isReadyForRemoteWork,
                                         Boolean isContactsExists, Boolean isAboutExists, String status) implements Serializable {
}