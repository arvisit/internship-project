package by.itacademy.profiler.usecasses.mapper;

import by.itacademy.profiler.persistence.model.CurriculumVitae;
import by.itacademy.profiler.usecasses.dto.CurriculumVitaeResponseDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;
import java.util.stream.Stream;

import static java.util.Objects.nonNull;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        builder = @Builder(disableBuilder = true))
public interface CurriculumVitaeMapper {

    @Mapping(target = "isCompetencesExists", expression = "java(mapList(curriculumVitae.getLanguages(), curriculumVitae.getSkills()))")
    @Mapping(target = "isEducationsExists", expression = "java(mapList(curriculumVitae.getMainEducations(), curriculumVitae.getCourses()))")
    @Mapping(target = "isAboutExists", expression = "java(map(curriculumVitae.getAbout()))")
    @Mapping(target = "isContactsExists", expression = "java(map(curriculumVitae.getContacts()))")
    @Mapping(target = "isExperienceExists", expression = "java(mapList(curriculumVitae.getExperience()))")
    @Mapping(target = "isAdditionalInformationExists", expression = "java(map(curriculumVitae.getAdditionalInformation()))")
    @Mapping(target = "isRecommendationsExists", expression = "java(mapList(curriculumVitae.getRecommendations()))")
    @Mapping(target = "imageUuid", source = "image.uuid")
    @Mapping(target = "positionId", source = "position.id")
    @Mapping(target = "position", source = "position.name")
    @Mapping(target = "countryId", source = "country.id")
    @Mapping(target = "country", source = "country.countryName")
    CurriculumVitaeResponseDto curriculumVitaeToCurriculumVitaeResponseDto(CurriculumVitae curriculumVitae);

    List<CurriculumVitaeResponseDto> curriculumVitaeListToCurriculumVitaeResponseDtoList(List<CurriculumVitae> curriculumVitaeList);

    default <T> boolean map(T partsOfCv) {
        return nonNull(partsOfCv);
    }

    default boolean mapList(List<?>... lists) {
        return Stream.of(lists).noneMatch(List::isEmpty);
    }
}