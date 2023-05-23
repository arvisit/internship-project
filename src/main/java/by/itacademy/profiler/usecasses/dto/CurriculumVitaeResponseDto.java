package by.itacademy.profiler.usecasses.dto;

import lombok.Builder;

import java.io.Serializable;

@Builder(setterPrefix = "with")
public record CurriculumVitaeResponseDto(String uuid, String imageUuid, String name, String surname, Long positionId,
                                         String position, Long countryId, String country, String city,
                                         boolean isReadyToRelocate, boolean isReadyForRemoteWork,
                                         Boolean isContactsExists, Boolean isAboutExists,
                                         Boolean isCompetencesExists, Boolean isExperienceExists, String status) implements Serializable {
}