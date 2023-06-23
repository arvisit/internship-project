package by.itacademy.profiler.usecasses;

import by.itacademy.profiler.usecasses.dto.AdditionalInformationRequestDto;
import by.itacademy.profiler.usecasses.dto.AdditionalInformationResponseDto;

public interface AdditionalInformationService {

    AdditionalInformationResponseDto save(AdditionalInformationRequestDto requestDto, String cvUuid);

    AdditionalInformationResponseDto getAdditionalInformationByCvUuid(String cvUuid);
}
