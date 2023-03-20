package by.itacademy.profiler.usecasses;


import by.itacademy.profiler.usecasses.dto.UserProfileDto;
import by.itacademy.profiler.usecasses.dto.UserProfileResponseDto;

import java.util.Optional;

public interface UserProfileService {

    UserProfileResponseDto saveUserProfile(UserProfileDto userProfileDto);

    UserProfileResponseDto getUserProfile();

    Optional<UserProfileResponseDto> updateUserProfile(UserProfileDto userProfileDto);
}
