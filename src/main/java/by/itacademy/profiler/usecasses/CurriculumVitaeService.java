package by.itacademy.profiler.usecasses;

import by.itacademy.profiler.usecasses.dto.CurriculumVitaeRequestDto;
import by.itacademy.profiler.usecasses.dto.CurriculumVitaeResponseDto;

public interface CurriculumVitaeService {

    CurriculumVitaeResponseDto save(CurriculumVitaeRequestDto curriculumVitaeRequestDto);

    boolean isCreationCvAvailable();
}
