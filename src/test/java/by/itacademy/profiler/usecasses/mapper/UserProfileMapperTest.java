package by.itacademy.profiler.usecasses.mapper;

import by.itacademy.profiler.persistence.model.UserProfile;
import by.itacademy.profiler.usecasses.dto.UserProfileDto;
import by.itacademy.profiler.usecasses.dto.UserProfileResponseDto;
import by.itacademy.profiler.util.UserProfileTestData;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserProfileMapperTest {
    private final UserProfileMapper userProfileMapper = Mappers.getMapper(UserProfileMapper.class);

    @Test
    void givenUserProfileDto_whenMapsUserProfileDtoToUserProfile_thenReturnUserProfile() {
        UserProfileDto profileDto = UserProfileTestData.getValideUserProfileDto();

        UserProfile userProfile = userProfileMapper.userProfileDtoToUserProfile(profileDto);
        assertEquals(profileDto.name(), userProfile.getName());
        assertEquals(profileDto.surname(), userProfile.getSurname());
        assertEquals(profileDto.countryId(), userProfile.getCountry().getId());
        assertEquals(profileDto.email(), userProfile.getEmail());
        assertEquals(profileDto.phoneCodeId(), userProfile.getPhoneCode().getId());
        assertEquals(profileDto.cellPhone(), userProfile.getCellPhone());
        assertEquals(profileDto.positionId(), userProfile.getPosition().getId());
        assertEquals(profileDto.profileImageUuid(), userProfile.getProfileImage().getUuid());
    }

    @Test
    void givenUserProfile_whenMapsUserProfileToUserProfileResponseDto_thenReturnUserProfileResponseDto() {
        UserProfile profile = UserProfileTestData.getUserProfile(UserProfileTestData.getValideUserProfileDto());

        UserProfileResponseDto userProfileResponseDto = userProfileMapper.userProfileToUserProfileResponseDto(profile);

        assertEquals(profile.getName(), userProfileResponseDto.name());
        assertEquals(profile.getSurname(), userProfileResponseDto.surname());
        assertEquals(profile.getCountry().getId(), userProfileResponseDto.countryId());
        assertEquals(profile.getCountry().getCountryName(), userProfileResponseDto.country());
        assertEquals(profile.getEmail(), userProfileResponseDto.email());
        assertEquals(profile.getPhoneCode().getId(), userProfileResponseDto.phoneCodeId());
        assertEquals(profile.getPhoneCode().getCode(), userProfileResponseDto.phoneCode());
        assertEquals(profile.getCellPhone(), userProfileResponseDto.cellPhone());
        assertEquals(profile.getPosition().getId(), userProfileResponseDto.positionId());
        assertEquals(profile.getPosition().getName(), userProfileResponseDto.position());
        assertEquals(profile.getProfileImage().getUuid(), userProfileResponseDto.profileImageUuid());
        assertEquals(profile.getUniqueStudentIdentifier(), userProfileResponseDto.uniqueStudentIdentifier());
    }
}