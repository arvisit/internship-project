package by.itacademy.profiler.usecasses;

import by.itacademy.profiler.usecasses.dto.RecommendationRequestDto;
import by.itacademy.profiler.usecasses.dto.RecommendationResponseDto;

import java.util.List;

public interface RecommendationService {

    List<RecommendationResponseDto> save(List<RecommendationRequestDto> listOfRecommendation, String cvUuid);

    List<RecommendationResponseDto> getRecommendationsByCvUuid(String cvUuid);
}
