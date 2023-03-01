package by.itacademy.profiler.usecasses.mapper;

import by.itacademy.profiler.persistence.model.About;
import by.itacademy.profiler.usecasses.dto.AboutDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class AboutMapperTest {

    public static final String DESCRIPTION = "Some description.";
    public static final String PRESENTATION = "https://test.com/12334";
    private static final About ABOUT = new About();
    private static final AboutDto ABOUT_DTO = new AboutDto(DESCRIPTION, PRESENTATION);
    private final AboutMapper aboutMapper = Mappers.getMapper(AboutMapper.class);

    @BeforeAll
    static void beforeAll() {
        ABOUT.setId(1L);
        ABOUT.setDescription(DESCRIPTION);
        ABOUT.setSelfPresentation(PRESENTATION);
    }

    @Test
    void shouldReturnAboutWhenMappingAboutDtoToAbout() {
        About about = aboutMapper.aboutDtoToAbout(ABOUT_DTO);
        assertNotNull(about);
        assertNull(about.getId());
        assertEquals(ABOUT_DTO.description(), about.getDescription());
        assertEquals(ABOUT_DTO.selfPresentation(), about.getSelfPresentation());
    }

    @Test
    void aboutToAboutDto() {
        AboutDto aboutDto = aboutMapper.aboutToAboutDto(ABOUT);
        assertNotNull(aboutDto);
        assertEquals(ABOUT.getDescription(), aboutDto.description());
        assertEquals(ABOUT.getSelfPresentation(), aboutDto.selfPresentation());
    }
}