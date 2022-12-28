package by.itacademy.profiler.usecasses.impl;


import by.itacademy.profiler.persistence.model.User;
import by.itacademy.profiler.persistence.model.UserProfile;
import by.itacademy.profiler.persistence.repository.UserProfileRepository;
import by.itacademy.profiler.persistence.repository.UserRepository;
import by.itacademy.profiler.usecasses.UserProfileService;
import by.itacademy.profiler.usecasses.dto.UserProfileDto;
import by.itacademy.profiler.usecasses.mapper.UserProfileMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepository userProfileRepository;

    private final UserRepository userRepository;

    private final UserProfileMapper userProfileMapper;

    @Override
    @Transactional
    public UserProfileDto saveUserProfile(UserProfileDto userProfileDto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(username);
        UserProfile userProfile = userProfileMapper.userProfileDtoToUserProfile(userProfileDto);
        userProfile.setId(user.getId());
        userProfileRepository.save(userProfile);
        return userProfileDto;
    }
}
