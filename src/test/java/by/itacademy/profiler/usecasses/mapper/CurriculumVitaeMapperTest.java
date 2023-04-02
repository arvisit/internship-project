package by.itacademy.profiler.usecasses.mapper;

import by.itacademy.profiler.persistence.model.CurriculumVitae;
import by.itacademy.profiler.persistence.model.CvStatus;
import by.itacademy.profiler.usecasses.dto.CurriculumVitaeResponseDto;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.IntStream;

import static by.itacademy.profiler.util.CurriculumVitaeTestData.getListOfCvsOfUser;
import static by.itacademy.profiler.util.CurriculumVitaeTestData.getValidCurriculumVitae;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CurriculumVitaeMapperTest {
    private final CurriculumVitaeMapper curriculumVitaeMapper = Mappers.getMapper(CurriculumVitaeMapper.class);

    @Test
    void givenCvStatusDraftToCvStatusDraftDto_whenMaps_thenCorrect() {
        CvStatus status = CvStatus.DRAFT;
        CurriculumVitae curriculumVitae = new CurriculumVitae();
        curriculumVitae.setStatus(status);
        CurriculumVitaeResponseDto cvResponseDto = getCvResponseDto(curriculumVitae);
        assertEquals(status, CvStatus.valueOf(cvResponseDto.status()));
    }

    @Test
    void givenCvStatusOnReviewToCvStatusOnReviewDto_whenMaps_thenCorrect() {
        CvStatus status = CvStatus.ON_REVIEW;
        CurriculumVitae curriculumVitae = new CurriculumVitae();
        curriculumVitae.setStatus(status);
        CurriculumVitaeResponseDto cvResponseDto = getCvResponseDto(curriculumVitae);
        assertEquals(status, CvStatus.valueOf(cvResponseDto.status()));
    }

    @Test
    void shouldReturnCvResponseDtoWhenMappingCvToCvResponseDto() {
        CurriculumVitae curriculumVitae = getValidCurriculumVitae();

        CurriculumVitaeResponseDto curriculumVitaeResponseDto = curriculumVitaeMapper.curriculumVitaeToCurriculumVitaeResponseDto(curriculumVitae);

        assertEquals(curriculumVitae.getUuid(), curriculumVitaeResponseDto.uuid());
        assertEquals(curriculumVitae.getImage().getUuid(), curriculumVitaeResponseDto.imageUuid());
        assertEquals(curriculumVitae.getName(), curriculumVitaeResponseDto.name());
        assertEquals(curriculumVitae.getSurname(), curriculumVitaeResponseDto.surname());
        assertEquals(curriculumVitae.getPosition().getId(), curriculumVitaeResponseDto.positionId());
        assertEquals(curriculumVitae.getPosition().getName(), curriculumVitaeResponseDto.position());
        assertEquals(curriculumVitae.getCountry().getId(), curriculumVitaeResponseDto.countryId());
        assertEquals(curriculumVitae.getCountry().getCountryName(), curriculumVitaeResponseDto.country());
        assertEquals(curriculumVitae.getCity(), curriculumVitaeResponseDto.city());
        assertEquals(curriculumVitae.getIsReadyToRelocate(), curriculumVitaeResponseDto.isReadyToRelocate());
        assertEquals(curriculumVitae.getIsReadyForRemoteWork(), curriculumVitaeResponseDto.isReadyForRemoteWork());
        assertNotNull(curriculumVitaeResponseDto.status());
        assertEquals(curriculumVitae.getStatus().name(), curriculumVitaeResponseDto.status());
    }

    @Test
    void shouldReturnListOfCvResponseDtoWhenMappingListOfCvToListOfCvResponseDto() {
        List<CurriculumVitae> listOfCvsOfUser = getListOfCvsOfUser(4);
        List<CurriculumVitaeResponseDto> listOfCurriculumVitaeResponseDto = curriculumVitaeMapper.curriculumVitaeListToCurriculumVitaeResponseDtoList(listOfCvsOfUser);

        assertEquals(listOfCvsOfUser.size(), listOfCurriculumVitaeResponseDto.size());

        IntStream.range(0, listOfCurriculumVitaeResponseDto.size()).forEach(i -> {
            assertEquals(listOfCvsOfUser.get(i).getUuid(), listOfCurriculumVitaeResponseDto.get(i).uuid());
            assertEquals(listOfCvsOfUser.get(i).getImage().getUuid(), listOfCurriculumVitaeResponseDto.get(i).imageUuid());
            assertEquals(listOfCvsOfUser.get(i).getName(), listOfCurriculumVitaeResponseDto.get(i).name());
            assertEquals(listOfCvsOfUser.get(i).getSurname(), listOfCurriculumVitaeResponseDto.get(i).surname());
            assertEquals(listOfCvsOfUser.get(i).getPosition().getId(), listOfCurriculumVitaeResponseDto.get(i).positionId());
            assertEquals(listOfCvsOfUser.get(i).getPosition().getName(), listOfCurriculumVitaeResponseDto.get(i).position());
            assertEquals(listOfCvsOfUser.get(i).getCountry().getId(), listOfCurriculumVitaeResponseDto.get(i).countryId());
            assertEquals(listOfCvsOfUser.get(i).getCountry().getCountryName(), listOfCurriculumVitaeResponseDto.get(i).country());
            assertEquals(listOfCvsOfUser.get(i).getCity(), listOfCurriculumVitaeResponseDto.get(i).city());
            assertEquals(listOfCvsOfUser.get(i).getIsReadyToRelocate(), listOfCurriculumVitaeResponseDto.get(i).isReadyToRelocate());
            assertEquals(listOfCvsOfUser.get(i).getIsReadyForRemoteWork(), listOfCurriculumVitaeResponseDto.get(i).isReadyForRemoteWork());
            assertNotNull(listOfCurriculumVitaeResponseDto.get(i).status());
            assertEquals(listOfCvsOfUser.get(i).getStatus().name(), listOfCurriculumVitaeResponseDto.get(i).status());
        });
    }

    private CurriculumVitaeResponseDto getCvResponseDto(CurriculumVitae curriculumVitae) {
        return curriculumVitaeMapper.curriculumVitaeToCurriculumVitaeResponseDto(curriculumVitae);
    }
}