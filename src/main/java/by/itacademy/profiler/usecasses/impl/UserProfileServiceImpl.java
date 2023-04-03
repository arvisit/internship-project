package by.itacademy.profiler.usecasses.impl;


import by.itacademy.profiler.persistence.model.Image;
import by.itacademy.profiler.persistence.model.User;
import by.itacademy.profiler.persistence.model.UserProfile;
import by.itacademy.profiler.persistence.repository.CountryRepository;
import by.itacademy.profiler.persistence.repository.ImageRepository;
import by.itacademy.profiler.persistence.repository.PhoneCodeRepository;
import by.itacademy.profiler.persistence.repository.PositionRepository;
import by.itacademy.profiler.persistence.repository.UserProfileRepository;
import by.itacademy.profiler.persistence.repository.UserRepository;
import by.itacademy.profiler.usecasses.ImageService;
import by.itacademy.profiler.usecasses.UserProfileService;
import by.itacademy.profiler.usecasses.dto.UserProfileDto;
import by.itacademy.profiler.usecasses.dto.UserProfileResponseDto;
import by.itacademy.profiler.usecasses.mapper.UserProfileMapper;
import by.itacademy.profiler.usecasses.util.AuthService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

    private static final int DEFAULT_PHONE_CODE = 375;
    private final UserProfileRepository userProfileRepository;
    private final UserRepository userRepository;
    private final CountryRepository countryRepository;
    private final PhoneCodeRepository phoneCodeRepository;
    private final PositionRepository positionRepository;
    private final UserProfileMapper userProfileMapper;
    private final ImageRepository imageRepository;
    private final ImageService imageService;
    private final AuthService authService;

    @Override
    @Transactional
    public UserProfileResponseDto saveUserProfile(UserProfileDto userProfileDto) {
        String username = authService.getUsername();
        User user = userRepository.findByEmail(username);
        UserProfile userProfile = userProfileMapper.userProfileDtoToUserProfile(userProfileDto);
        userProfile.setId(user.getId());
        imageRepository.findByUuid(userProfileDto.profileImageUuid()).ifPresent(userProfile::setProfileImage);
        userProfileRepository.save(userProfile);
        return userProfileMapper.userProfileToUserProfileResponseDto(userProfile);
    }

    @Override
    public UserProfileResponseDto getUserProfile() {
        String username = authService.getUsername();
        UserProfile userProfile = userProfileRepository.findByUsername(username).orElse(null);
        return userProfileMapper.userProfileToUserProfileResponseDto(userProfile);
    }

    @Override
    @Transactional
    public Optional<UserProfileResponseDto> updateUserProfile(UserProfileDto userProfileDto) {
        String username = authService.getUsername();
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
        String incomingImageUuid = userProfileDto.profileImageUuid();
        Image storedImage = userProfile.getProfileImage();
        if (imageService.isImageChanging(incomingImageUuid, storedImage)) {
            String imageUuid = imageService.replaceImage(incomingImageUuid, storedImage);
            Image image = imageRepository.findByUuid(imageUuid).orElse(null);
            userProfile.setProfileImage(image);
        }
        userProfile.setName(userProfileDto.name());
        userProfile.setSurname(userProfileDto.surname());
        userProfile.setCellPhone(userProfileDto.cellPhone());
        userProfile.setEmail(userProfileDto.email());
        if (userProfileDto.countryId() == null) {
            userProfile.setCountry(null);
        } else {
            countryRepository.findById(userProfileDto.countryId()).ifPresent(userProfile::setCountry);
        }
        if (isNull(userProfileDto.phoneCodeId())) {
            userProfile.setPhoneCode(phoneCodeRepository.findByCode(DEFAULT_PHONE_CODE));
        } else phoneCodeRepository.findById(userProfileDto.phoneCodeId()).ifPresent(userProfile::setPhoneCode);
        if (userProfileDto.positionId() == null) {
            userProfile.setPosition(null);
        } else {
            positionRepository.findById(userProfileDto.positionId()).ifPresent(userProfile::setPosition);
        }
    }
}
