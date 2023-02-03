package by.itacademy.profiler.usecasses.impl;

import by.itacademy.profiler.api.exception.BadRequestException;
import by.itacademy.profiler.api.exception.ImageStorageException;
import by.itacademy.profiler.persistence.model.CurriculumVitae;
import by.itacademy.profiler.persistence.model.User;
import by.itacademy.profiler.persistence.repository.CountryRepository;
import by.itacademy.profiler.persistence.repository.CurriculumVitaeRepository;
import by.itacademy.profiler.persistence.repository.ImageRepository;
import by.itacademy.profiler.persistence.repository.PositionRepository;
import by.itacademy.profiler.persistence.repository.UserRepository;
import by.itacademy.profiler.storage.ImageStorageService;
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
import static java.util.Objects.isNull;

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
    private final ImageStorageService imageStorageService;

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
    @Transactional
    public CurriculumVitaeResponseDto update(String curriculumVitaeUuid,
                                             CurriculumVitaeRequestDto curriculumVitaeDto) {
        String username = getUsername();
        CurriculumVitae curriculumVitae = curriculumVitaeRepository.findByUuidAndUsername(curriculumVitaeUuid, username);
        updateCurriculumVitaeByRequestDto(curriculumVitae, curriculumVitaeDto);
        CurriculumVitae updatedCurriculumVitae = curriculumVitaeRepository.save(curriculumVitae);
        return curriculumVitaeMapper.curriculumVitaeToCurriculumVitaeResponseDto(updatedCurriculumVitae);
    }

    @Override
    public CurriculumVitaeResponseDto getCvOfUser(String uuid) {
        String username = getUsername();
        CurriculumVitae curriculumVitae = curriculumVitaeRepository.findByUuidAndUsername(uuid, username);
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

    private void updateCurriculumVitaeByRequestDto(CurriculumVitae curriculumVitae,
                                                   CurriculumVitaeRequestDto curriculumVitaeRequestDto) {
        replaceImage(curriculumVitaeRequestDto, curriculumVitae);
        if (!curriculumVitaeRequestDto.name().equals(curriculumVitae.getName())) {
            curriculumVitae.setName(curriculumVitaeRequestDto.name());
        }
        if (!curriculumVitaeRequestDto.surname().equals(curriculumVitae.getName())) {
            curriculumVitae.setSurname(curriculumVitaeRequestDto.surname());
        }
        if (!curriculumVitaeRequestDto.positionId().equals(curriculumVitae.getPosition().getId())) {
            positionRepository.findById(curriculumVitaeRequestDto.positionId())
                    .ifPresent(curriculumVitae::setPosition);
        }
        if (!curriculumVitaeRequestDto.countryId().equals(curriculumVitae.getCountry().getId())) {
            countryRepository.findById(curriculumVitaeRequestDto.countryId())
                    .ifPresent(curriculumVitae::setCountry);
        }
        if (!curriculumVitaeRequestDto.city().equals(curriculumVitae.getCity())) {
            curriculumVitae.setCity(curriculumVitaeRequestDto.city());
        }
        if (!curriculumVitaeRequestDto.isReadyToRelocate().equals(curriculumVitae.getIsReadyToRelocate())) {
            curriculumVitae.setIsReadyToRelocate(curriculumVitaeRequestDto.isReadyToRelocate());
        }
        if (!curriculumVitaeRequestDto.isReadyForRemoteWork().equals(curriculumVitae.getIsReadyForRemoteWork())) {
            curriculumVitae.setIsReadyForRemoteWork(curriculumVitaeRequestDto.isReadyForRemoteWork());
        }
    }

    private void replaceImage(CurriculumVitaeRequestDto curriculumVitaeRequestDto, CurriculumVitae curriculumVitae) {
        if (isNull(curriculumVitaeRequestDto.imageUuid())) {
            String uuid = curriculumVitae.getImage().getUuid();
            try {
                imageStorageService.delete(uuid);
            } catch (ImageStorageException e) {
                throw new BadRequestException(String.format("Image with UUID %s could not be remove", uuid));
            }
            curriculumVitae.setImage(null);
        } else imageRepository.findByUuid(curriculumVitaeRequestDto.imageUuid()).ifPresent(curriculumVitae::setImage);
    }
}
