package by.itacademy.profiler.usecasses.impl;

import by.itacademy.profiler.usecasses.CurriculumVitaeService;
import by.itacademy.profiler.usecasses.dto.ExperienceRequestDto;
import by.itacademy.profiler.usecasses.mapper.ExperienceMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static by.itacademy.profiler.util.ExperienceTestData.CV_UUID_FOR_EXPERIENCE;
import static by.itacademy.profiler.util.ExperienceTestData.createExperienceRequestDto;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
class ExperienceServiceImplTest {

    @InjectMocks
    private ExperienceServiceImpl experienceService;

    @Mock
    private CurriculumVitaeService curriculumVitaeService;

    @Mock
    private ExperienceMapper experienceMapper;

    @Test
    void shouldSaveExperienceToCvWhenInvokeSave() {

        List<ExperienceRequestDto> requestList = List.of(
                createExperienceRequestDto().build(),
                createExperienceRequestDto().withCompany("Other company").build(),
                createExperienceRequestDto().withPosition("Other position").build());

        assertDoesNotThrow(() -> experienceService.save(requestList, CV_UUID_FOR_EXPERIENCE));
    }
}
