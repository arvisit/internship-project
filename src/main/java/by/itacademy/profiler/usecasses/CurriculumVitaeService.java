package by.itacademy.profiler.usecasses;

import by.itacademy.profiler.persistence.model.CvLanguage;
import by.itacademy.profiler.persistence.model.Skill;
import by.itacademy.profiler.usecasses.dto.CurriculumVitaeRequestDto;
import by.itacademy.profiler.usecasses.dto.CurriculumVitaeResponseDto;

import java.util.List;

public interface CurriculumVitaeService {

    CurriculumVitaeResponseDto save(CurriculumVitaeRequestDto curriculumVitaeRequestDto);

    boolean isCreationCvAvailable();

    List<CurriculumVitaeResponseDto> getAllCvOfUser();

    CurriculumVitaeResponseDto getCvOfUser(String uuid);

    CurriculumVitaeResponseDto update(String curriculumVitaeUuid, CurriculumVitaeRequestDto curriculumVitaeRequestDto);

    boolean isCurriculumVitaeExists(String uuid);

    void saveSkillsToCv(String cvUuid, List<Skill> skills);

    void saveLanguagesToCv(String cvUuid, List<CvLanguage> languages);
}
