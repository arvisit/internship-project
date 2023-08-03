package by.itacademy.profiler.usecasses.impl;

import by.itacademy.profiler.persistence.model.Recommendation;
import by.itacademy.profiler.usecasses.CurriculumVitaeService;
import by.itacademy.profiler.usecasses.RecommendationService;
import by.itacademy.profiler.usecasses.dto.RecommendationRequestDto;
import by.itacademy.profiler.usecasses.dto.RecommendationResponseDto;
import by.itacademy.profiler.usecasses.mapper.RecommendationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationServiceImpl implements RecommendationService {

    private final CurriculumVitaeService curriculumVitaeService;
    private final RecommendationMapper recommendationMapper;

    @Override
    @Transactional
    public List<RecommendationResponseDto> save(List<RecommendationRequestDto> listOfRecommendation, String cvUuid) {
        List<Recommendation> recommendations = listOfRecommendation.stream()
                .map(recommendationMapper::fromDtoToEntity)
                .toList();
        List<Recommendation> savedRecommendations = curriculumVitaeService.saveRecommendationsToCv(cvUuid, recommendations);
        return savedRecommendations.stream()
                .map(recommendationMapper::fromEntityToDto)
                .toList();
    }
}
