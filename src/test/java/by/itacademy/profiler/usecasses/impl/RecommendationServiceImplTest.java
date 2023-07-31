package by.itacademy.profiler.usecasses.impl;

import by.itacademy.profiler.persistence.model.Recommendation;
import by.itacademy.profiler.usecasses.CurriculumVitaeService;
import by.itacademy.profiler.usecasses.dto.RecommendationRequestDto;
import by.itacademy.profiler.usecasses.dto.RecommendationResponseDto;
import by.itacademy.profiler.usecasses.mapper.RecommendationMapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static by.itacademy.profiler.util.RecommendationTestData.CV_UUID_FOR_RECOMMENDATION;
import static by.itacademy.profiler.util.RecommendationTestData.createRecommendation;
import static by.itacademy.profiler.util.RecommendationTestData.createRecommendationRequestDto;
import static by.itacademy.profiler.util.RecommendationTestData.createRecommendationResponseDto;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

    @Test
    void shouldGetRecommendationsWhenInvokeGet() {
        List<Recommendation> recommendations = List.of(createRecommendation().build());
        RecommendationResponseDto recommendationResponseDto = createRecommendationResponseDto().build();
        List<RecommendationResponseDto> expectedResponse = List.of(recommendationResponseDto);

        when(curriculumVitaeService.getRecommendationsByCvUuid(CV_UUID_FOR_RECOMMENDATION)).thenReturn(recommendations);
        when(recommendationMapper.fromEntityToDto(Mockito.any(Recommendation.class)))
                .thenReturn(recommendationResponseDto);

        List<RecommendationResponseDto> actualResponse = recommendationService
                .getRecommendationsByCvUuid(CV_UUID_FOR_RECOMMENDATION);

        verify(curriculumVitaeService, times(1)).getRecommendationsByCvUuid(CV_UUID_FOR_RECOMMENDATION);
        verify(recommendationMapper, times(1)).fromEntityToDto(Mockito.any(Recommendation.class));

        assertEquals(expectedResponse, actualResponse);
    }
}
