package by.itacademy.profiler.usecasses.impl;

import by.itacademy.profiler.api.exception.AdditionalInformationNotFoundException;
import by.itacademy.profiler.persistence.model.AdditionalInformation;
import by.itacademy.profiler.persistence.model.CurriculumVitae;
import by.itacademy.profiler.persistence.repository.AdditionalInformationRepository;
import by.itacademy.profiler.persistence.repository.CurriculumVitaeRepository;
import by.itacademy.profiler.usecasses.AdditionalInformationService;
import by.itacademy.profiler.usecasses.dto.AdditionalInformationRequestDto;
import by.itacademy.profiler.usecasses.dto.AdditionalInformationResponseDto;
import by.itacademy.profiler.usecasses.mapper.AdditionalInformationMapper;
import by.itacademy.profiler.usecasses.util.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class AdditionalInformationServiceImpl implements AdditionalInformationService {

    private final AdditionalInformationMapper additionalInformationMapper;
    private final CurriculumVitaeRepository curriculumVitaeRepository;
    private final AdditionalInformationRepository additionalInformationRepository;
    private final AuthService authService;

    @Override
    @Transactional
    public AdditionalInformationResponseDto save(AdditionalInformationRequestDto requestDto, String cvUuid) {
        CurriculumVitae curriculumVitae = curriculumVitaeRepository.getReferenceByUuid(cvUuid);
        AdditionalInformation additionalInformation = additionalInformationMapper.fromDtoToEntity(requestDto);
        additionalInformation.setId(curriculumVitae.getId());
        AdditionalInformation savedAdditionalInformation = additionalInformationRepository.save(additionalInformation);
        return additionalInformationMapper.fromEntityToDto(savedAdditionalInformation);
    }

    @Override
    @Transactional(readOnly = true)
    public AdditionalInformationResponseDto getAdditionalInformationByCvUuid(String cvUuid) {
        String username = authService.getUsername();
        AdditionalInformation additionalInformation = additionalInformationRepository
                .findByCvUuidAndUsername(cvUuid, username)
                .orElseThrow(() -> new AdditionalInformationNotFoundException(
                        String.format("Additional information section is not available for CV UUID: %s of user %s",
                                cvUuid, username)));
        return additionalInformationMapper.fromEntityToDto(additionalInformation);
    }
}
