package by.itacademy.profiler.usecasses.mapper;

import by.itacademy.profiler.persistence.model.Language;
import by.itacademy.profiler.usecasses.dto.LanguageResponseDto;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static by.itacademy.profiler.util.LanguageTestData.createLanguage;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LanguageMapperTest {

    private final LanguageMapper languageMapper = Mappers.getMapper(LanguageMapper.class);

    @Test
    void shouldMapCorrectlyAllFieldWhenInvokeFromEntityToDto() {
        Language language = createLanguage().withId(1L).build();
        LanguageResponseDto languageResponseDto = languageMapper.fromEntityToDto(language);
        assertEquals(language.getId(),languageResponseDto.id());
        assertEquals(language.getName(),languageResponseDto.name());
        assertEquals(language.getIsTop(),languageResponseDto.isTop());
    }

}
