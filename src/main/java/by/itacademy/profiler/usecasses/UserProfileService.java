package by.itacademy.profiler.usecasses;


import by.itacademy.profiler.usecasses.dto.UserProfileResponseDto;
import by.itacademy.profiler.usecasses.dto.UserProfileDto;

import java.util.Optional;

public interface UserProfileService {

    UserProfileDto saveUserProfile(UserProfileDto userProfileDto);

    UserProfileResponseDto getUserProfile();

    Optional<UserProfileResponseDto> updateUserProfile(UserProfileDto userProfileDto);
}
