package by.itacademy.profiler.usecasses;

import by.itacademy.profiler.api.exception.AboutNotFoundException;
import by.itacademy.profiler.persistence.model.About;
import by.itacademy.profiler.persistence.repository.AboutRepository;
import by.itacademy.profiler.usecasses.dto.AboutDto;
import by.itacademy.profiler.usecasses.impl.AboutServiceImpl;
import by.itacademy.profiler.usecasses.mapper.AboutMapper;
import by.itacademy.profiler.usecasses.util.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class AboutServiceImplTest {

    @Mock
    private AboutMapper aboutMapper;

    @Mock
    private AboutRepository aboutRepository;

    @Mock
    private AuthService authService;

    @InjectMocks
    private AboutServiceImpl aboutService;

    @Test
    void shouldUpdateAboutSection() {
        String cvUUID = "a034aa4d-5c1e-4ea7-bbff-5661ae5c45db";
        AboutDto aboutDto = new AboutDto("test description", "https://test.com/1");
        String username = authService.getUsername();
        About about = new About();
        about.setDescription("description");
        about.setSelfPresentation("https://test.com/222");
        given(aboutRepository.save(about)).willReturn(about);
        given(aboutRepository.findByUuidAndUsername(cvUUID, username)).willReturn(Optional.of(about));
        when(aboutMapper.aboutToAboutDto(about)).thenReturn(aboutDto);
        AboutDto updatedAboutDto = aboutService.update(cvUUID, aboutDto);
        assertEquals(aboutDto.description(), updatedAboutDto.description());
        assertEquals(aboutDto.selfPresentation(), updatedAboutDto.selfPresentation());
    }

    @Test
    void shouldThrowExceptionAndNotUpdateAboutSection() {
        String cvUUID = "a034aa4d-5c1e-4ea7-bbff-5661ae5c45db";
        AboutDto aboutDto = new AboutDto("test description", "https://test.com/1");
        assertThrows(AboutNotFoundException.class, () -> aboutService.update(cvUUID, aboutDto));
        verify(aboutRepository, never()).save(any(About.class));
    }
}