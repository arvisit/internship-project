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
import by.itacademy.profiler.usecasses.dto.UserProfileDto;
import by.itacademy.profiler.usecasses.dto.UserProfileResponseDto;
import by.itacademy.profiler.usecasses.mapper.UserProfileMapper;
import by.itacademy.profiler.usecasses.util.AuthService;
import by.itacademy.profiler.util.UserProfileTestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserProfileServiceImplTest {

    @Mock
    private UserProfileMapper profileMapper;

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserProfileRepository profileRepository;
    @Mock
    private ImageRepository imageRepository;
    @Mock
    private ImageService imageService;
    @Mock
    private PositionRepository positionRepository;
    @Mock
    private AuthService authService;
    @Mock
    private CountryRepository countryRepository;
    @Mock
    private PhoneCodeRepository phoneCodeRepository;
    @InjectMocks
    private UserProfileServiceImpl userProfileService;

    @Test
    void givenUserProfileDto_whenSaveUserProfile_thenReturnUserProfileResponseDto() {
        UserProfileDto userProfileDto = UserProfileTestData.getValideUserProfileDto();
        UserProfile userProfile = UserProfileTestData.getUserProfile(userProfileDto);

        stubbingForSave(userProfileDto, userProfile);

        UserProfileResponseDto userProfileResponseDto = userProfileService.saveUserProfile(userProfileDto);

        assertEquals(userProfileDto.name(), userProfileResponseDto.name());
        assertEquals(userProfileDto.surname(), userProfileResponseDto.surname());
        assertEquals(userProfileDto.countryId(), userProfileResponseDto.countryId());
        assertEquals(userProfile.getCountry().getCountryName(), userProfileResponseDto.country());
        assertEquals(userProfileDto.email(), userProfileResponseDto.email());
        assertEquals(userProfileDto.phoneCodeId(), userProfileResponseDto.phoneCodeId());
        assertEquals(userProfile.getPhoneCode().getCode(), userProfileResponseDto.phoneCode());
        assertEquals(userProfileDto.cellPhone(), userProfileResponseDto.cellPhone());
        assertEquals(userProfileDto.positionId(), userProfileResponseDto.positionId());
        assertEquals(userProfile.getPosition().getName(), userProfileResponseDto.position());
        assertEquals(userProfileDto.profileImageUuid(), userProfileResponseDto.profileImageUuid());
        assertEquals(userProfile.getUniqueStudentIdentifier(), userProfileResponseDto.uniqueStudentIdentifier());
    }

    @Test
    void givenUserProfileDto_whenSaveUserProfile_thenCallProfileMapper() {
        UserProfileDto userProfileDto = UserProfileTestData.getValideUserProfileDto();
        UserProfile userProfile = UserProfileTestData.getUserProfile(userProfileDto);

        stubbingForSave(userProfileDto, userProfile);

        userProfileService.saveUserProfile(userProfileDto);

        verify(authService, times(1)).getUsername();
        verify(profileMapper, times(1)).userProfileDtoToUserProfile(userProfileDto);
        verify(profileMapper, times(1)).userProfileToUserProfileResponseDto(userProfile);
        verify(userRepository, times(1)).findByEmail(UserProfileTestData.getUser().getEmail());
        verify(imageRepository, times(1)).findByUuid(userProfileDto.profileImageUuid());
        verify(profileRepository, times(1)).save(userProfile);
    }

    @Test
    void whenGetUserProfile_thenReturnUserProfileResponseDto() {
        UserProfileDto userProfileDto = UserProfileTestData.getValideUserProfileDto();
        UserProfile userProfile = UserProfileTestData.getUserProfile(userProfileDto);

        stubbingForGet(userProfile, userProfileDto);

        UserProfileResponseDto userProfileResponseDto = userProfileService.getUserProfile();

        assertEquals(userProfile.getName(), userProfileResponseDto.name());
        assertEquals(userProfile.getSurname(), userProfileResponseDto.surname());
        assertEquals(userProfile.getCountry().getId(), userProfileResponseDto.countryId());
        assertEquals(userProfile.getCountry().getCountryName(), userProfileResponseDto.country());
        assertEquals(userProfile.getEmail(), userProfileResponseDto.email());
        assertEquals(userProfile.getPhoneCode().getId(), userProfileResponseDto.phoneCodeId());
        assertEquals(userProfile.getPhoneCode().getCode(), userProfileResponseDto.phoneCode());
        assertEquals(userProfile.getCellPhone(), userProfileResponseDto.cellPhone());
        assertEquals(userProfile.getPosition().getId(), userProfileResponseDto.positionId());
        assertEquals(userProfile.getPosition().getName(), userProfileResponseDto.position());
        assertEquals(userProfile.getProfileImage().getUuid(), userProfileResponseDto.profileImageUuid());
        assertEquals(userProfile.getUniqueStudentIdentifier(), userProfileResponseDto.uniqueStudentIdentifier());
    }

    @Test
    void whenGetUserProfile_thenCallProfileRepository() {
        UserProfileDto userProfileDto = UserProfileTestData.getValideUserProfileDto();
        UserProfile userProfile = UserProfileTestData.getUserProfile(userProfileDto);

        stubbingForGet(userProfile, userProfileDto);

        userProfileService.getUserProfile();

        verify(authService, times(1)).getUsername();
        verify(profileRepository, times(1)).findByUsername(UserProfileTestData.getUser().getEmail());
        verify(profileMapper, times(1)).userProfileToUserProfileResponseDto(userProfile);
    }

    @Test
    void givenUserProfileDto_whenUpdateUserProfile_thenReturnUserProfileResponseDto() {
        UserProfileDto userProfileDto = UserProfileTestData.getValideUserProfileDto();
        UserProfile userProfile = UserProfileTestData.getUserProfile(userProfileDto);

        stubbingForUpdate(userProfileDto, userProfile);

        UserProfileResponseDto userProfileResponseDto = userProfileService.updateUserProfile(userProfileDto).get();

        assertEquals(userProfileDto.name(), userProfileResponseDto.name());
        assertEquals(userProfileDto.surname(), userProfileResponseDto.surname());
        assertEquals(userProfileDto.countryId(), userProfileResponseDto.countryId());
        assertEquals(userProfile.getCountry().getCountryName(), userProfileResponseDto.country());
        assertEquals(userProfileDto.email(), userProfileResponseDto.email());
        assertEquals(userProfileDto.phoneCodeId(), userProfileResponseDto.phoneCodeId());
        assertEquals(userProfile.getPhoneCode().getCode(), userProfileResponseDto.phoneCode());
        assertEquals(userProfileDto.cellPhone(), userProfileResponseDto.cellPhone());
        assertEquals(userProfileDto.positionId(), userProfileResponseDto.positionId());
        assertEquals(userProfile.getPosition().getName(), userProfileResponseDto.position());
        assertEquals(userProfileDto.profileImageUuid(), userProfileResponseDto.profileImageUuid());
        assertEquals(userProfile.getUniqueStudentIdentifier(), userProfileResponseDto.uniqueStudentIdentifier());
    }

    @Test
    void givenUserProfileDto_whenUpdateUserProfile_thenCallProfileRepository() {
        UserProfileDto userProfileDto = UserProfileTestData.getValideUserProfileDto();
        UserProfile userProfile = UserProfileTestData.getUserProfile(userProfileDto);
        stubbingForUpdate(userProfileDto, userProfile);

        userProfileService.updateUserProfile(userProfileDto);

        verify(authService, times(1)).getUsername();
        verify(profileRepository, times(1)).findByUsername(UserProfileTestData.getUser().getEmail());
        verify(imageRepository, times(1)).findByUuid(userProfileDto.profileImageUuid());
        verify(imageService, times(1)).isImageChanging(userProfile.getProfileImage().getUuid(), userProfile.getProfileImage());
        verify(imageService, times(1)).replaceImage(userProfileDto.profileImageUuid(), userProfile.getProfileImage());
        verify(countryRepository, times(1)).findById(userProfileDto.countryId());
        verify(phoneCodeRepository, times(1)).findById(userProfileDto.phoneCodeId());
        verify(positionRepository, times(1)).findById(userProfileDto.positionId());
        verify(profileMapper, times(1)).userProfileToUserProfileResponseDto(userProfile);
    }

    private void stubbingForSave(UserProfileDto userProfileDto, UserProfile userProfile) {
        UserProfileResponseDto userProfileResponseDto = UserProfileTestData.getUserProfileResponseDto(userProfileDto);
        User user = UserProfileTestData.getUser();
        Image image = UserProfileTestData.getImage();

        when(authService.getUsername()).thenReturn(user.getEmail());
        when(profileMapper.userProfileDtoToUserProfile(userProfileDto)).thenReturn(userProfile);
        when(profileMapper.userProfileToUserProfileResponseDto(userProfile)).thenReturn(userProfileResponseDto);
        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);
        when(imageRepository.findByUuid(userProfileDto.profileImageUuid())).thenReturn(Optional.of(image));
        when(profileRepository.save(userProfile)).thenReturn(userProfile);
    }

    private void stubbingForUpdate(UserProfileDto userProfileDto, UserProfile userProfile) {
        UserProfileResponseDto userProfileResponseDto = UserProfileTestData.getUserProfileResponseDto(userProfileDto);
        User user = UserProfileTestData.getUser();
        when(authService.getUsername()).thenReturn(user.getEmail());
        when(profileRepository.findByUsername(user.getEmail())).thenReturn(Optional.of(userProfile));
        when(imageRepository.findByUuid(userProfileDto.profileImageUuid()))
                .thenReturn(Optional.of(userProfile.getProfileImage()));
        when(imageService.isImageChanging(userProfileDto.profileImageUuid(), userProfile.getProfileImage()))
                .thenReturn(true);
        when(imageService.replaceImage(userProfileDto.profileImageUuid(), userProfile.getProfileImage()))
                .thenReturn(userProfileDto.profileImageUuid());
        when(countryRepository.findById(userProfileDto.countryId())).thenReturn(Optional.of(userProfile.getCountry()));
        when(phoneCodeRepository.findById(userProfileDto.phoneCodeId())).thenReturn(Optional.of(userProfile.getPhoneCode()));
        when(positionRepository.findById(userProfileDto.positionId())).thenReturn(Optional.of(userProfile.getPosition()));
        when(profileMapper.userProfileToUserProfileResponseDto(userProfile)).thenReturn(userProfileResponseDto);
    }

    private void stubbingForGet(UserProfile userProfile, UserProfileDto userProfileDto) {
        UserProfileResponseDto userProfileResponseDto = UserProfileTestData.getUserProfileResponseDto(userProfileDto);
        User user = UserProfileTestData.getUser();
        when(authService.getUsername()).thenReturn(user.getEmail());
        when(profileRepository.findByUsername(user.getEmail())).thenReturn(Optional.of(userProfile));
        when(profileMapper.userProfileToUserProfileResponseDto(userProfile)).thenReturn(userProfileResponseDto);
    }
}