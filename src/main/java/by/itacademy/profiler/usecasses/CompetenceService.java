package by.itacademy.profiler.usecasses;

import by.itacademy.profiler.usecasses.dto.CompetenceRequestDto;
import by.itacademy.profiler.usecasses.dto.CompetenceResponseDto;

public interface CompetenceService {

    CompetenceResponseDto save(CompetenceRequestDto competenceRequestDto, String cvUuid);

    CompetenceResponseDto getCompetenceByCvUuid(String cvUuid);

}
