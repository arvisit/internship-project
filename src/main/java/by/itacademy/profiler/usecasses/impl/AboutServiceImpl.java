package by.itacademy.profiler.usecasses.impl;

import by.itacademy.profiler.persistence.model.About;
import by.itacademy.profiler.persistence.model.CurriculumVitae;
import by.itacademy.profiler.persistence.repository.AboutRepository;
import by.itacademy.profiler.persistence.repository.CurriculumVitaeRepository;
import by.itacademy.profiler.usecasses.AboutService;
import by.itacademy.profiler.usecasses.dto.AboutDto;
import by.itacademy.profiler.usecasses.mapper.AboutMapper;
import by.itacademy.profiler.usecasses.util.AuthService;
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
}
