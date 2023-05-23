package by.itacademy.profiler.usecasses.impl;

import by.itacademy.profiler.persistence.model.Experience;
import by.itacademy.profiler.usecasses.CurriculumVitaeService;
import by.itacademy.profiler.usecasses.ExperienceService;
import by.itacademy.profiler.usecasses.dto.ExperienceRequestDto;
import by.itacademy.profiler.usecasses.dto.ExperienceResponseDto;
import by.itacademy.profiler.usecasses.mapper.ExperienceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExperienceServiceImpl implements ExperienceService {

    private final CurriculumVitaeService curriculumVitaeService;
    private final ExperienceMapper experienceMapper;

    @Override
    @Transactional
    public List<ExperienceResponseDto> save(List<ExperienceRequestDto> listOfExperience, String cvUuid) {
        List<Experience> experience = listOfExperience.stream()
                .map(experienceMapper::fromDtoToEntity)
                .toList();
        List<Experience> experienceList = curriculumVitaeService.saveExperienceToCv(cvUuid, experience);
        List<ExperienceResponseDto> experienceResponse = experienceList.stream()
                .map(experienceMapper::fromEntityToDto)
                .toList();
        return experienceResponse;
    }
}
