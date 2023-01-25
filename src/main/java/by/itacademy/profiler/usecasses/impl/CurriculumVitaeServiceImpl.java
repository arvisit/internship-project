package by.itacademy.profiler.usecasses.impl;

import by.itacademy.profiler.persistence.model.CurriculumVitae;
import by.itacademy.profiler.persistence.model.User;
import by.itacademy.profiler.persistence.repository.CountryRepository;
import by.itacademy.profiler.persistence.repository.CurriculumVitaeRepository;
import by.itacademy.profiler.persistence.repository.ImageRepository;
import by.itacademy.profiler.persistence.repository.PositionRepository;
import by.itacademy.profiler.persistence.repository.UserRepository;
import by.itacademy.profiler.usecasses.CurriculumVitaeService;
import by.itacademy.profiler.usecasses.dto.CurriculumVitaeRequestDto;
import by.itacademy.profiler.usecasses.dto.CurriculumVitaeResponseDto;
import by.itacademy.profiler.usecasses.mapper.CurriculumVitaeMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static by.itacademy.profiler.usecasses.util.AuthUtil.getUsername;

@Service
@RequiredArgsConstructor
public class CurriculumVitaeServiceImpl implements CurriculumVitaeService {

    @Value("${curriculum-vitae.number-of-user-curriculum-vitae}")
    private int numberOfUserCv;

    private final UserRepository userRepository;
    private final CurriculumVitaeRepository curriculumVitaeRepository;
    private final CurriculumVitaeMapper curriculumVitaeMapper;
    private final PositionRepository positionRepository;
    private final CountryRepository countryRepository;
    private final ImageRepository imageRepository;

    @Override
    @Transactional
    public CurriculumVitaeResponseDto save(CurriculumVitaeRequestDto curriculumVitaeRequestDto) {
        String username = getUsername();
        User user = userRepository.findByEmail(username);
        CurriculumVitae curriculumVitae = curriculumVitaeRequestDtoToCurriculumVitae(curriculumVitaeRequestDto);
        curriculumVitae.setUser(user);
        curriculumVitae.setUuid(UUID.randomUUID().toString());
        curriculumVitaeRepository.save(curriculumVitae);
        return curriculumVitaeMapper.curriculumVitaeToCurriculumVitaeResponseDto(curriculumVitae);
    }

    @Override
    public List<CurriculumVitaeResponseDto> getAllCvOfUser() {
        String username = getUsername();
        List<CurriculumVitae> curriculumVitaeList = curriculumVitaeRepository.findByUsername(username);
        return curriculumVitaeMapper.curriculumVitaeListToCurriculumVitaeResponseDtoList(curriculumVitaeList);
    }

    @Override
    public CurriculumVitaeResponseDto getCvOfUser(String uuid) {
        CurriculumVitae curriculumVitae = curriculumVitaeRepository.findByUuid(uuid);
        return curriculumVitaeMapper.curriculumVitaeToCurriculumVitaeResponseDto(curriculumVitae);
    }

    public Long getAllCvByUser() {
        String username = getUsername();
        return curriculumVitaeRepository.findCountByUsername(username);
    }

    public boolean isCreationCvAvailable() {
        return getAllCvByUser() < numberOfUserCv;
    }

    private CurriculumVitae curriculumVitaeRequestDtoToCurriculumVitae(CurriculumVitaeRequestDto curriculumVitaeRequestDto) {
        CurriculumVitae curriculumVitae = new CurriculumVitae();
        imageRepository.findByUuid(curriculumVitaeRequestDto.imageUuid()).ifPresent(curriculumVitae::setImage);
        curriculumVitae.setName(curriculumVitaeRequestDto.name());
        curriculumVitae.setSurname(curriculumVitaeRequestDto.surname());
        positionRepository.findById(curriculumVitaeRequestDto.positionId()).ifPresent(curriculumVitae::setPosition);
        countryRepository.findById(curriculumVitaeRequestDto.countryId()).ifPresent(curriculumVitae::setCountry);
        curriculumVitae.setCity(curriculumVitaeRequestDto.city());
        curriculumVitae.setIsReadyToRelocate(curriculumVitaeRequestDto.isReadyToRelocate());
        curriculumVitae.setIsReadyForRemoteWork(curriculumVitaeRequestDto.isReadyForRemoteWork());
        return curriculumVitae;
    }
}
