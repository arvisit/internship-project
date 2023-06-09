package by.itacademy.profiler.usecasses;

import by.itacademy.profiler.usecasses.dto.EducationRequestDto;
import by.itacademy.profiler.usecasses.dto.EducationResponseDto;

public interface EducationService {

    EducationResponseDto save(EducationRequestDto educationRequestDto, String cvUuid);
}
