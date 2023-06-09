package by.itacademy.profiler.usecasses;

import by.itacademy.profiler.persistence.model.Course;
import by.itacademy.profiler.persistence.model.CvLanguage;
import by.itacademy.profiler.persistence.model.Experience;
import by.itacademy.profiler.persistence.model.MainEducation;
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

    List<CvLanguage> getCvLanguagesByCvUuid(String cvUuid);

    List<Skill> getCvSkillsByCvUuid(String cvUuid);

    List<Experience> saveExperienceToCv(String cvUuid, List<Experience> experience);

    List<Experience> getCvExperienceByCvUuid(String cvUuid);

    List<MainEducation> saveMainEducationsToCv(String cvUuid, List<MainEducation> mainEducations);

    List<Course> saveCoursesToCv(String cvUuid, List<Course> courses);
}
