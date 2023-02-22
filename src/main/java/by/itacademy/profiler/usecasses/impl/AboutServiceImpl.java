package by.itacademy.profiler.usecasses.impl;

import by.itacademy.profiler.api.exception.AboutNotFoundException;
import by.itacademy.profiler.persistence.model.About;
import by.itacademy.profiler.persistence.model.CurriculumVitae;
import by.itacademy.profiler.persistence.repository.AboutRepository;
import by.itacademy.profiler.persistence.repository.CurriculumVitaeRepository;
import by.itacademy.profiler.usecasses.AboutService;
import by.itacademy.profiler.usecasses.dto.AboutDto;
import by.itacademy.profiler.usecasses.mapper.AboutMapper;
import by.itacademy.profiler.usecasses.util.AuthService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AboutServiceImpl implements AboutService {

    private final AboutRepository aboutRepository;

    private final CurriculumVitaeRepository curriculumVitaeRepository;

    private final AboutMapper aboutMapper;

    private final AuthService authService;

    @Override
    public AboutDto save(String uuid, AboutDto aboutDto) {
        String username = authService.getUsername();
        CurriculumVitae curriculumVitae = curriculumVitaeRepository.findByUuidAndUsername(uuid, username);
        About about = aboutMapper.aboutDtoToAbout(aboutDto);
        about.setId(curriculumVitae.getId());
        About savedAbout = aboutRepository.save(about);
        return aboutMapper.aboutToAboutDto(savedAbout);
    }

    @Override
    @Transactional
    public AboutDto update(String uuid, AboutDto aboutDto) {
        String username = authService.getUsername();
        About about = aboutRepository.findByUuidAndUsername(uuid, username).orElseThrow(() ->
                new AboutNotFoundException(String.format("About section is not available for CV UUID: %s of user %s", uuid, username)));
        updateAbout(aboutDto, about);
        About updateAbout = aboutRepository.save(about);
        return aboutMapper.aboutToAboutDto(updateAbout);
    }

    @Override
    public AboutDto getAbout(String uuid) {
        String username = authService.getUsername();
        About about = aboutRepository.findByUuidAndUsername(uuid, username).orElseThrow(() ->
                new AboutNotFoundException(String.format("About section is not available for CV UUID: %s of user %s", uuid, username)));
        return aboutMapper.aboutToAboutDto(about);
    }

    private void updateAbout(AboutDto aboutDto, About about) {
        if (!about.getDescription().equals(aboutDto.description())) {
            about.setDescription(aboutDto.description());
        }
        about.setSelfPresentation(aboutDto.selfPresentation());
    }
}
