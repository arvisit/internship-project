package by.itacademy.profiler.usecasses.impl;

import by.itacademy.profiler.usecasses.CurriculumVitaeService;
import by.itacademy.profiler.usecasses.dto.RecommendationRequestDto;
import by.itacademy.profiler.usecasses.mapper.RecommendationMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static by.itacademy.profiler.util.RecommendationTestData.CV_UUID_FOR_RECOMMENDATION;
import static by.itacademy.profiler.util.RecommendationTestData.createRecommendationRequestDto;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
class RecommendationServiceImplTest {

    @InjectMocks
    private RecommendationServiceImpl recommendationService;

    @Mock
    private CurriculumVitaeService curriculumVitaeService;

    @Mock
    private RecommendationMapper recommendationMapper;

    @Test
    void shouldSaveRecommendationToCvWhenInvokeSave() {

        List<RecommendationRequestDto> requestList = List.of(
                createRecommendationRequestDto().build(),
                createRecommendationRequestDto().withCompany("Other company").build(),
                createRecommendationRequestDto().withPosition("Other position").build());

        assertDoesNotThrow(() -> recommendationService.save(requestList, CV_UUID_FOR_RECOMMENDATION));
    }
}
