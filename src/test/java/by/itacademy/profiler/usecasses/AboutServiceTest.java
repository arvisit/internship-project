package by.itacademy.profiler.usecasses;

import by.itacademy.profiler.api.exception.AboutNotFoundException;
import by.itacademy.profiler.persistence.model.About;
import by.itacademy.profiler.persistence.model.CurriculumVitae;
import by.itacademy.profiler.persistence.model.User;
import by.itacademy.profiler.persistence.repository.AboutRepository;
import by.itacademy.profiler.persistence.repository.CurriculumVitaeRepository;
import by.itacademy.profiler.usecasses.dto.AboutDto;
import by.itacademy.profiler.usecasses.impl.AboutServiceImpl;
import by.itacademy.profiler.usecasses.mapper.AboutMapper;
import by.itacademy.profiler.usecasses.util.AuthService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class AboutServiceTest {

    public static final String USER_NAME = "user@mail.com";

    @Mock
    private AboutMapper aboutMapper;

    @Mock
    private AboutRepository aboutRepository;

    @Mock
    private AuthService authService;

    @InjectMocks
    private AboutServiceImpl aboutService;
    private static final String CV_UUID = "20c3cb38-abb4-11ed-afa1-0242ac120002";

    private static final String WRONG_CV_UUID = "a034aa4d-5c1e-4ea7-bbff-5661ae5c45db";
    private static final User USER = new User();
    private static final CurriculumVitae CV = new CurriculumVitae();
    private static final About ABOUT = new About();
    private static final AboutDto ABOUT_DTO = new AboutDto("test description", "https://test.com/12334");
    @Mock
    private CurriculumVitaeRepository curriculumVitaeRepository;

    @BeforeAll
    static void beforeAll() {
        USER.setEmail(USER_NAME);
        CV.setId(1L);
        ABOUT.setId(1L);
        ABOUT.setDescription("test description");
        ABOUT.setSelfPresentation("https://test.com/12334");
    }

    @BeforeEach
    void setUp() {
        when(authService.getUsername()).thenReturn(USER_NAME);
    }

    @Test
    void shouldReturnSavedAboutDtoWhenSendAboutDtoToSave() {
        when(aboutMapper.aboutDtoToAbout(ABOUT_DTO)).thenReturn(ABOUT);
        when(curriculumVitaeRepository.findByUuidAndUsername(CV_UUID, USER_NAME)).thenReturn(CV);
        when(aboutRepository.save(ABOUT)).thenReturn(ABOUT);
        when(aboutMapper.aboutToAboutDto(ABOUT)).thenReturn(ABOUT_DTO);

        AboutDto savedAboutDto = aboutService.save(CV_UUID, ABOUT_DTO);

        assertEquals(ABOUT_DTO.description(), savedAboutDto.description());
        assertEquals(ABOUT_DTO.selfPresentation(), savedAboutDto.selfPresentation());
    }

    @Test
    void shouldReturnSavedAboutDtoWhenSendAboutDtoAndCallCvRepository() {
        when(aboutMapper.aboutDtoToAbout(ABOUT_DTO)).thenReturn(ABOUT);
        when(curriculumVitaeRepository.findByUuidAndUsername(CV_UUID, USER_NAME)).thenReturn(CV);
        when(aboutRepository.save(ABOUT)).thenReturn(ABOUT);
        when(aboutMapper.aboutToAboutDto(ABOUT)).thenReturn(ABOUT_DTO);

        AboutDto savedAboutDto = aboutService.save(CV_UUID, ABOUT_DTO);

        assertEquals(ABOUT_DTO.description(), savedAboutDto.description());
        assertEquals(ABOUT_DTO.selfPresentation(), savedAboutDto.selfPresentation());
        verify(curriculumVitaeRepository, times(1)).findByUuidAndUsername(CV_UUID, USER_NAME);
    }

    @Test
    void shouldReturnSavedAboutDtoWhenSendAboutDtoAndCallAboutRepository() {
        when(aboutMapper.aboutDtoToAbout(ABOUT_DTO)).thenReturn(ABOUT);
        when(curriculumVitaeRepository.findByUuidAndUsername(CV_UUID, USER_NAME)).thenReturn(CV);
        when(aboutRepository.save(ABOUT)).thenReturn(ABOUT);
        when(aboutMapper.aboutToAboutDto(ABOUT)).thenReturn(ABOUT_DTO);

        AboutDto savedAboutDto = aboutService.save(CV_UUID, ABOUT_DTO);

        assertEquals(ABOUT_DTO.description(), savedAboutDto.description());
        assertEquals(ABOUT_DTO.selfPresentation(), savedAboutDto.selfPresentation());
        verify(aboutRepository, times(1)).save(ABOUT);
    }

    @Test
    void shouldReturnSavedAboutDtoWhenSendAboutDtoAndCallAboutMapper() {
        when(aboutMapper.aboutDtoToAbout(ABOUT_DTO)).thenReturn(ABOUT);
        when(curriculumVitaeRepository.findByUuidAndUsername(CV_UUID, USER_NAME)).thenReturn(CV);
        when(aboutRepository.save(ABOUT)).thenReturn(ABOUT);
        when(aboutMapper.aboutToAboutDto(ABOUT)).thenReturn(ABOUT_DTO);

        AboutDto savedAboutDto = aboutService.save(CV_UUID, ABOUT_DTO);

        assertEquals(ABOUT_DTO.description(), savedAboutDto.description());
        assertEquals(ABOUT_DTO.selfPresentation(), savedAboutDto.selfPresentation());
        verify(aboutMapper, times(1)).aboutToAboutDto(ABOUT);
        verify(aboutMapper, times(1)).aboutDtoToAbout(ABOUT_DTO);
    }

    @Test
    void shouldReturnSavedAboutDtoWhenSendAboutDtoAndCallAuthService() {
        when(aboutMapper.aboutDtoToAbout(ABOUT_DTO)).thenReturn(ABOUT);
        when(curriculumVitaeRepository.findByUuidAndUsername(CV_UUID, USER_NAME)).thenReturn(CV);
        when(aboutRepository.save(ABOUT)).thenReturn(ABOUT);
        when(aboutMapper.aboutToAboutDto(ABOUT)).thenReturn(ABOUT_DTO);

        AboutDto savedAboutDto = aboutService.save(CV_UUID, ABOUT_DTO);

        assertEquals(ABOUT_DTO.description(), savedAboutDto.description());
        assertEquals(ABOUT_DTO.selfPresentation(), savedAboutDto.selfPresentation());
        verify(authService, times(1)).getUsername();
    }

    @Test
    void shouldUpdateAboutSection() {
        when(aboutRepository.findByUuidAndUsername(CV_UUID, USER_NAME)).thenReturn(Optional.of(ABOUT));
        when(aboutRepository.save(ABOUT)).thenReturn(ABOUT);
        when(aboutMapper.aboutToAboutDto(ABOUT)).thenReturn(ABOUT_DTO);

        AboutDto updatedAboutDto = aboutService.update(CV_UUID, ABOUT_DTO);
        assertEquals(ABOUT_DTO.description(), updatedAboutDto.description());
        assertEquals(ABOUT_DTO.selfPresentation(), updatedAboutDto.selfPresentation());
    }

    @Test
    void shouldThrowExceptionAndNotUpdateAboutSection() {
        assertThrows(AboutNotFoundException.class, () -> aboutService.update(WRONG_CV_UUID, ABOUT_DTO));
        verify(aboutRepository, never()).save(any(About.class));
    }

    @Test
    void shouldGetAboutSection() {
        when(aboutRepository.findByUuidAndUsername(CV_UUID, USER_NAME)).thenReturn(Optional.of(ABOUT));
        when(aboutMapper.aboutToAboutDto(ABOUT)).thenReturn(ABOUT_DTO);

        AboutDto getAboutDto = aboutService.getAbout(CV_UUID);
        assertEquals(getAboutDto.description(), ABOUT.getDescription());
        assertEquals(getAboutDto.selfPresentation(), ABOUT.getSelfPresentation());
    }

    @Test
    void shouldThrowAboutNotFoundException() {
        AboutNotFoundException aboutNotFoundException = assertThrows(AboutNotFoundException.class, () -> aboutService.getAbout(WRONG_CV_UUID));
        assertEquals(aboutNotFoundException.getMessage(), String.format("About section is not available for CV UUID: %s of user %s", WRONG_CV_UUID, USER_NAME));
    }
}