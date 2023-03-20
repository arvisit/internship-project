package by.itacademy.profiler.api.controllers;


import by.itacademy.profiler.api.exception.BadRequestException;
import by.itacademy.profiler.api.exception.UserProfileNotFoundException;
import by.itacademy.profiler.usecasses.UserProfileService;
import by.itacademy.profiler.usecasses.dto.UserProfileDto;
import by.itacademy.profiler.usecasses.dto.UserProfileResponseDto;
import by.itacademy.profiler.usecasses.util.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/profile")
@Slf4j
public class UserProfileApiController {

    private final UserProfileService userProfileService;
    private final AuthService authService;

    @PostMapping
    public ResponseEntity<UserProfileResponseDto> saveUserProfile(@RequestBody @Valid UserProfileDto userProfile) {
        if (userProfileService.getUserProfile() != null) {
            String username = authService.getUsername();
            throw new BadRequestException(String.format("The %s already has a profile", username));
        }
        log.debug("Input data for creating profile: {} ", userProfile);
        UserProfileResponseDto profile = userProfileService.saveUserProfile(userProfile);
        return new ResponseEntity<>(profile, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<UserProfileResponseDto> getUserProfile() {
        UserProfileResponseDto profile = userProfileService.getUserProfile();
        log.debug("Getting profile from database: {} ", profile);
        if (profile == null) {
            throw new UserProfileNotFoundException("Profile not found");
        }
        return ResponseEntity.ok(profile);
    }

    @PutMapping
    public ResponseEntity<UserProfileResponseDto> updateUserProfile(@RequestBody @Valid UserProfileDto userProfileDto) {
        UserProfileResponseDto profile = userProfileService.updateUserProfile(userProfileDto)
                .orElseThrow(() -> new UserProfileNotFoundException("Profile not updated"));
        log.debug("Updated profile from database: {} ", profile);
        return ResponseEntity.ok(profile);
    }
}
