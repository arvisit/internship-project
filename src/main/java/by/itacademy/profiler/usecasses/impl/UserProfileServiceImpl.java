package by.itacademy.profiler.usecasses.impl;


import by.itacademy.profiler.api.exception.BadRequestException;
import by.itacademy.profiler.api.exception.ImageStorageException;
import by.itacademy.profiler.persistence.model.User;
import by.itacademy.profiler.persistence.model.UserProfile;
import by.itacademy.profiler.persistence.repository.CountryRepository;
import by.itacademy.profiler.persistence.repository.ImageRepository;
import by.itacademy.profiler.persistence.repository.PhoneCodeRepository;
import by.itacademy.profiler.persistence.repository.PositionRepository;
import by.itacademy.profiler.persistence.repository.UserProfileRepository;
import by.itacademy.profiler.persistence.repository.UserRepository;
import by.itacademy.profiler.storage.ImageStorageService;
import by.itacademy.profiler.usecasses.UserProfileService;
import by.itacademy.profiler.usecasses.dto.UserProfileDto;
import by.itacademy.profiler.usecasses.dto.UserProfileResponseDto;
import by.itacademy.profiler.usecasses.mapper.UserProfileMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static by.itacademy.profiler.usecasses.util.AuthUtil.getUsername;
import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final UserRepository userRepository;
    private final CountryRepository countryRepository;
    private final PhoneCodeRepository phoneCodeRepository;
    private final PositionRepository positionRepository;
    private final UserProfileMapper userProfileMapper;
    private final ImageRepository imageRepository;
    private final ImageStorageService imageStorageService;

    @Override
    @Transactional
    public UserProfileDto saveUserProfile(UserProfileDto userProfileDto) {
        String username = getUsername();
        User user = userRepository.findByEmail(username);
        UserProfile userProfile = userProfileMapper.userProfileDtoToUserProfile(userProfileDto);
        userProfile.setId(user.getId());
        imageRepository.findByUuid(userProfileDto.profileImageUuid()).ifPresent(userProfile::setProfileImage);
        userProfileRepository.save(userProfile);
        return userProfileDto;
    }

    @Override
    public UserProfileResponseDto getUserProfile() {
        String username = getUsername();
        UserProfile userProfile = userProfileRepository.findByUsername(username).orElse(null);
        return userProfileMapper.userProfileToUserProfileResponseDto(userProfile);
    }

    @Override
    @Transactional
    public Optional<UserProfileResponseDto> updateUserProfile(UserProfileDto userProfileDto) {
        String username = getUsername();
        Optional<UserProfile> userProfile = userProfileRepository.findByUsername(username);
        if (userProfile.isPresent()) {
            UserProfile profile = userProfile.get();
            updateProfile(userProfileDto, profile);
            userProfileRepository.save(profile);
            return Optional.ofNullable(userProfileMapper.userProfileToUserProfileResponseDto(profile));
        }
        return Optional.empty();
    }

    private void updateProfile(UserProfileDto userProfileDto, UserProfile userProfile) {
        if (!isNull(userProfileDto.name())) {
            userProfile.setName(userProfileDto.name());
        }
        if (!isNull(userProfileDto.surname())) {
            userProfile.setSurname(userProfileDto.surname());
        }
        if (!isNull(userProfileDto.cellPhone())) {
            userProfile.setCellPhone(userProfileDto.cellPhone());
        }
        if (!isNull(userProfileDto.email())) {
            userProfile.setEmail(userProfileDto.email());
        }
        if (!isNull(userProfileDto.countryId())) {
            countryRepository.findById(userProfileDto.countryId()).ifPresent(userProfile::setCountry);
        }
        if (!isNull(userProfileDto.phoneCodeId())) {
            phoneCodeRepository.findById(userProfileDto.phoneCodeId()).ifPresent(userProfile::setPhoneCode);
        }
        if (!isNull(userProfileDto.positionId())) {
            positionRepository.findById(userProfileDto.positionId()).ifPresent(userProfile::setPosition);
        }
        replaceImage(userProfileDto, userProfile);
    }

    private void replaceImage(UserProfileDto userProfileDto, UserProfile userProfile) {
        if (isNull(userProfileDto.profileImageUuid())) {
            String uuid = userProfile.getProfileImage().getUuid();
            try {
                imageStorageService.delete(uuid);
            } catch (ImageStorageException e) {
                throw new BadRequestException(String.format("Image with UUID %s could not be remove", uuid));
            }
            userProfile.setProfileImage(null);
        } else imageRepository.findByUuid(userProfileDto.profileImageUuid()).ifPresent(userProfile::setProfileImage);
    }
}
