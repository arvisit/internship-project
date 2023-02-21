package by.itacademy.profiler.usecasses.mapper;

import by.itacademy.profiler.persistence.model.CurriculumVitae;
import by.itacademy.profiler.persistence.model.CvStatus;
import by.itacademy.profiler.usecasses.dto.CurriculumVitaeResponseDto;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CurriculumVitaeMapperTest {
    private final CurriculumVitaeMapper mapper = Mappers.getMapper(CurriculumVitaeMapper.class);

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

    private CurriculumVitaeResponseDto getCvResponseDto(CurriculumVitae curriculumVitae) {
        return mapper.curriculumVitaeToCurriculumVitaeResponseDto(curriculumVitae);
    }
}