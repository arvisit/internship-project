package by.itacademy.profiler.util;

import by.itacademy.profiler.usecasses.dto.CvLanguageRequestDto;
import by.itacademy.profiler.usecasses.dto.CompetenceRequestDto;
import by.itacademy.profiler.usecasses.dto.CompetenceResponseDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.LongStream;

import static by.itacademy.profiler.util.CurriculumVitaeTestData.CV_UUID;
import static by.itacademy.profiler.util.CvLanguageTestData.createCvLanguageRequestDto;
import static by.itacademy.profiler.util.CvLanguageTestData.createCvLanguageRequestList;
import static by.itacademy.profiler.util.CvLanguageTestData.createCvLanguageResponseList;
import static by.itacademy.profiler.util.SkillTestData.createSkillResponseDtoList;

public final class CompetenceTestData {

    private CompetenceTestData() {
    }

    public static final String CV_COMPETENCE_URL_TEMPLATE = String.format("/api/v1/cvs/%s/competences", CV_UUID);
    public static final String INVALID_CV_UUID = "80bf2";
    public static final String INVALID_CV_COMPETENCE_URL_TEMPLATE = String.format("/api/v1/cvs/%s/competences", INVALID_CV_UUID);

    public static CompetenceRequestDto createCompetenceRequestDto(Long skillListSize, Long languageListSize) {
        List<Long> skillIds = LongStream.rangeClosed(1, skillListSize)
                .boxed()
                .toList();

        List<CvLanguageRequestDto> cvLanguageRequestDtos = new ArrayList<>();
        for (int i = 0; i < languageListSize; i++) {
            cvLanguageRequestDtos.add(createCvLanguageRequestDto());
        }

        return CompetenceRequestDto.builder()
                .withLanguages(cvLanguageRequestDtos)
                .withSkills(skillIds)
                .build();
    }

    public static CompetenceRequestDto createCompetenceRequestDto() {
        return CompetenceRequestDto.builder()
                .withLanguages(createCvLanguageRequestList())
                .withSkills(Arrays.asList(1L, 2L, 3L))
                .build();
    }

    public static CompetenceResponseDto createCompetenceResponseDto() {
        return CompetenceResponseDto.builder()
                .withLanguages(createCvLanguageResponseList())
                .withSkills(createSkillResponseDtoList())
                .build();
    }

    public static CompetenceRequestDto createCompetenceRequestDtoWithEmptyLanguageList() {
        return CompetenceRequestDto.builder()
                .withLanguages(new ArrayList<>())
                .withSkills(Arrays.asList(1L, 2L, 3L))
                .build();
    }

    public static CompetenceRequestDto createCompetenceRequestDtoWithEmptySkillList() {
        return CompetenceRequestDto.builder()
                .withLanguages(createCvLanguageRequestList())
                .withSkills(new ArrayList<>())
                .build();
    }

    public static CompetenceRequestDto createCompetenceRequestDtoWhichContainsNull() {
        return CompetenceRequestDto.builder()
                .withLanguages(createCvLanguageRequestList())
                .withSkills(Arrays.asList(1L, 2L, null))
                .build();
    }
}