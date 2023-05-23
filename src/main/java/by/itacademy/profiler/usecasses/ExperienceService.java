package by.itacademy.profiler.usecasses;

import by.itacademy.profiler.usecasses.dto.ExperienceRequestDto;
import by.itacademy.profiler.usecasses.dto.ExperienceResponseDto;

import java.util.List;

public interface ExperienceService {
    List<ExperienceResponseDto> save(List<ExperienceRequestDto> listOfExperience, String cvUuid);

    List<ExperienceResponseDto> getExperienceByCvUuid(String cvUuid);

}
