package by.itacademy.profiler.usecasses.mapper;

import by.itacademy.profiler.persistence.model.CvLanguage;
import by.itacademy.profiler.persistence.model.Language;
import by.itacademy.profiler.persistence.model.LanguageProficiencyEnum;
import by.itacademy.profiler.usecasses.LanguageService;
import by.itacademy.profiler.usecasses.dto.CvLanguageRequestDto;
import by.itacademy.profiler.usecasses.dto.CvLanguageResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static by.itacademy.profiler.util.CvLanguageTestData.createCvLanguageRequestDto;
import static by.itacademy.profiler.util.CvLanguageTestData.createCvLanguage;
import static by.itacademy.profiler.util.LanguageTestData.createLanguage;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CvLanguageMapperTest {

    @InjectMocks
    private final CvLanguageMapper cvLanguageMapper = Mappers.getMapper(CvLanguageMapper.class);

    @Mock
    private LanguageService languageService;

    @Test
    void shouldMapLanguageCorrectlyWhenInvokeFromDtoToEntity() {
        Long languageId = 1L;
        Language language = createLanguage().build();
        CvLanguageRequestDto cvLanguageRequestDto = createCvLanguageRequestDto(LanguageProficiencyEnum.A2);

        when(languageService.getLanguageById(languageId)).thenReturn(language);

        CvLanguage cvLanguage = cvLanguageMapper.fromDtoToEntity(cvLanguageRequestDto);
        assertEquals(language, cvLanguage.getLanguage());
    }

    @Test
    void shouldMapLanguageProficiencyEnumCorrectlyWhenInvokeFromDtoToEntity() {
        CvLanguageRequestDto cvLanguageRequestDto = createCvLanguageRequestDto(LanguageProficiencyEnum.A2);

        CvLanguage cvLanguage = cvLanguageMapper.fromDtoToEntity(cvLanguageRequestDto);
        assertEquals(LanguageProficiencyEnum.A2, cvLanguage.getProficiency());
    }

    @Test
    void shouldIgnoreIdWhenInvokeFromDtoToEntity() {
        CvLanguageRequestDto cvLanguageRequestDto = createCvLanguageRequestDto(LanguageProficiencyEnum.A2);

        CvLanguage cvLanguage = cvLanguageMapper.fromDtoToEntity(cvLanguageRequestDto);
        assertNull(cvLanguage.getId());
    }

    @Test
    void shouldMapLanguageNameCorrectlyWhenInvokeFromEntityToDto() {
        CvLanguage cvLanguage = createCvLanguage().build();

        CvLanguageResponseDto cvLanguageResponseDto = cvLanguageMapper.fromEntityToDto(cvLanguage);
        assertEquals(cvLanguageResponseDto.name(), cvLanguage.getLanguage().getName());
    }

    @Test
    void shouldMapLanguageProficiencyEnumCorrectlyWhenInvokeFromEntityToDto() {
        CvLanguage cvLanguage = createCvLanguage().build();

        CvLanguageResponseDto cvLanguageResponseDto = cvLanguageMapper.fromEntityToDto(cvLanguage);
        assertEquals(cvLanguage.getProficiency(), cvLanguageResponseDto.languageProficiency());
    }

    @Test
    void shouldMapLanguageIdCorrectlyWhenInvokeFromEntityToDto() {
        CvLanguage cvLanguage = createCvLanguage().build();

        CvLanguageResponseDto cvLanguageResponseDto = cvLanguageMapper.fromEntityToDto(cvLanguage);
        assertEquals(cvLanguage.getLanguage().getId(), cvLanguageResponseDto.id());
    }

}
