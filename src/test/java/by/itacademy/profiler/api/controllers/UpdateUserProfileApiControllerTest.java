package by.itacademy.profiler.api.controllers;

import by.itacademy.profiler.usecasses.CountryService;
import by.itacademy.profiler.usecasses.ImageValidationService;
import by.itacademy.profiler.usecasses.PhoneCodeService;
import by.itacademy.profiler.usecasses.PositionService;
import by.itacademy.profiler.usecasses.UserProfileService;
import by.itacademy.profiler.usecasses.dto.UserProfileDto;
import by.itacademy.profiler.usecasses.dto.UserProfileResponseDto;
import by.itacademy.profiler.usecasses.util.AuthService;
import by.itacademy.profiler.util.UserProfileTestData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserProfileApiController.class)
@AutoConfigureMockMvc(addFilters = false)
class UpdateUserProfileApiControllerTest {
    private static final String USERNAME = "User";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UserProfileService profileService;
    @MockBean
    private CountryService countryService;
    @MockBean
    private ImageValidationService imageValidationService;
    @MockBean
    private PositionService positionService;
    @MockBean
    private PhoneCodeService phoneCodeService;
    @MockBean
    private AuthService authService;
    private static final String USER_PROFILE_URL_TEMPLATE = "/api/v1/profile";

    @Test
    void shouldReturn200WhenUpdateValidInputUserProfile() throws Exception {
        String valideJsonUserProfileRequestDto = UserProfileTestData.getJsonUserProfileRequestDto();
        UserProfileDto userProfileDto = jsonToUserProfileDto(valideJsonUserProfileRequestDto);
        UserProfileResponseDto userProfileResponseDto = UserProfileTestData.getUserProfileResponseDto(userProfileDto);
        String expectedJsonUserProfileResponseDto = userProfileResponseDtoToJson(userProfileResponseDto);

        stubbingForService(userProfileDto);
        when(profileService.updateUserProfile(userProfileDto)).thenReturn(Optional.of(userProfileResponseDto));

        mockMvc.perform(put(USER_PROFILE_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(valideJsonUserProfileRequestDto))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(expectedJsonUserProfileResponseDto));
    }

    @Test
    void shouldReturn404WhenUpdateProfileNotFound() throws Exception {
        String valideJsonUserProfileRequestDto = UserProfileTestData.getJsonUserProfileRequestDto();
        UserProfileDto userProfileDto = jsonToUserProfileDto(valideJsonUserProfileRequestDto);

        stubbingForService(userProfileDto);

        when(profileService.updateUserProfile(userProfileDto)).thenReturn(Optional.empty());

        mockMvc.perform(put(USER_PROFILE_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(valideJsonUserProfileRequestDto))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Is.is("Profile not updated")));
    }

    @Test
    void shouldReturn400WhenUpdateInvalidNameUserProfile() throws Exception {
        String invalidedNameInJsonUserProfileRequestDto = UserProfileTestData.getInvalidedNameInJsonUserProfileRequestDto();

        stubbingForService(jsonToUserProfileDto(invalidedNameInJsonUserProfileRequestDto));
        mockMvc.perform(put(USER_PROFILE_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidedNameInJsonUserProfileRequestDto))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Is.is("Invalid name")));
    }

    @Test
    void shouldReturn400WhenUpdateInvalidSurnameUserProfile() throws Exception {
        String invalidedSurnameInJsonUserProfileRequestDto = UserProfileTestData.getInvalidedSurnameInJsonUserProfileRequestDto();

        stubbingForService(jsonToUserProfileDto(invalidedSurnameInJsonUserProfileRequestDto));
        mockMvc.perform(put(USER_PROFILE_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidedSurnameInJsonUserProfileRequestDto))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.surname", Is.is("Invalid surname")));
    }

    @Test
    void shouldReturn400WhenUpdateInvalidCountryIdUserProfile() throws Exception {
        String jsonUserProfileRequestDto = UserProfileTestData.getJsonUserProfileRequestDto();

        UserProfileDto userProfileDto = jsonToUserProfileDto(jsonUserProfileRequestDto);

        when(countryService.isCountryExist(userProfileDto.countryId())).thenReturn(false);
        when(imageValidationService.isImageBelongsToUser(userProfileDto.profileImageUuid())).thenReturn(true);
        when(positionService.isPositionExist(userProfileDto.positionId())).thenReturn(true);
        when(phoneCodeService.isPhoneCodeExist(userProfileDto.phoneCodeId())).thenReturn(true);

        mockMvc.perform(put(USER_PROFILE_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUserProfileRequestDto))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.countryId", Is.is("Invalid id: country not found")));
    }

    @Test
    void shouldReturn400WhenUpdateInvalidEmailUserProfile() throws Exception {
        String invalidedEmailInJsonUserProfileRequestDto = UserProfileTestData.getInvalidedEmailInJsonUserProfileRequestDto();

        stubbingForService(jsonToUserProfileDto(invalidedEmailInJsonUserProfileRequestDto));

        mockMvc.perform(put(USER_PROFILE_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidedEmailInJsonUserProfileRequestDto))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.email", Is
                                .is("Invalid email. Example of the correct variant: example@example.com ")));
    }

    @Test
    void shouldReturn400WhenUpdateInvalidPhoneCodeIdUserProfile() throws Exception {
        String jsonUserProfileRequestDto = UserProfileTestData.getJsonUserProfileRequestDto();

        UserProfileDto userProfileDto = jsonToUserProfileDto(jsonUserProfileRequestDto);

        when(countryService.isCountryExist(userProfileDto.countryId())).thenReturn(true);
        when(imageValidationService.isImageBelongsToUser(userProfileDto.profileImageUuid())).thenReturn(true);
        when(positionService.isPositionExist(userProfileDto.positionId())).thenReturn(true);
        when(phoneCodeService.isPhoneCodeExist(userProfileDto.phoneCodeId())).thenReturn(false);

        mockMvc.perform(put(USER_PROFILE_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUserProfileRequestDto))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.phoneCodeId", Is.is("Invalid id: phone code not found")));
    }

    @Test
    void shouldReturn400WhenUpdateInvalidCellPhoneUserProfile() throws Exception {
        String invalidedCellPhoneInJsonUserProfileRequestDto = UserProfileTestData.getInvalidedCellPhoneInJsonUserProfileRequestDto();

        stubbingForService(jsonToUserProfileDto(invalidedCellPhoneInJsonUserProfileRequestDto));

        mockMvc.perform(put(USER_PROFILE_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidedCellPhoneInJsonUserProfileRequestDto))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.cellPhone", Is
                                .is("Invalid cell phone number. Example of the correct variant: 29233XXXX")));
    }

    @Test
    void shouldReturn400WhenUpdateInvalidPositionIdUserProfile() throws Exception {
        String jsonUserProfileRequestDto = UserProfileTestData.getJsonUserProfileRequestDto();

        UserProfileDto userProfileDto = jsonToUserProfileDto(jsonUserProfileRequestDto);

        when(countryService.isCountryExist(userProfileDto.countryId())).thenReturn(true);
        when(imageValidationService.isImageBelongsToUser(userProfileDto.profileImageUuid())).thenReturn(true);
        when(positionService.isPositionExist(userProfileDto.positionId())).thenReturn(false);
        when(phoneCodeService.isPhoneCodeExist(userProfileDto.phoneCodeId())).thenReturn(true);

        mockMvc.perform(put(USER_PROFILE_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUserProfileRequestDto))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.positionId", Is.is("Invalid id: position not found")));
    }

    @Test
    void shouldReturn400WhenUpdateInvalidProfileImageUuidUserProfile() throws Exception {
        String jsonUserProfileRequestDto = UserProfileTestData.getJsonUserProfileRequestDto();

        UserProfileDto userProfileDto = jsonToUserProfileDto(jsonUserProfileRequestDto);

        when(countryService.isCountryExist(userProfileDto.countryId())).thenReturn(true);
        when(imageValidationService.isImageBelongsToUser(userProfileDto.profileImageUuid())).thenReturn(false);
        when(positionService.isPositionExist(userProfileDto.positionId())).thenReturn(true);
        when(phoneCodeService.isPhoneCodeExist(userProfileDto.phoneCodeId())).thenReturn(true);

        mockMvc.perform(put(USER_PROFILE_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUserProfileRequestDto))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.profileImageUuid", Is.is("Image UUID is not valid!")));
    }

    private UserProfileDto jsonToUserProfileDto(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, UserProfileDto.class);
    }

    private String userProfileResponseDtoToJson(UserProfileResponseDto userProfileResponseDto) throws JsonProcessingException {
        return objectMapper.writeValueAsString(userProfileResponseDto);
    }

    private void stubbingForService(UserProfileDto userProfileDto) {
        when(authService.getUsername()).thenReturn(USERNAME);
        when(countryService.isCountryExist(userProfileDto.countryId())).thenReturn(true);
        when(imageValidationService.isImageBelongsToUser(userProfileDto.profileImageUuid())).thenReturn(true);
        when(imageValidationService.validateImageForProfile(userProfileDto.profileImageUuid())).thenReturn(true);
        when(positionService.isPositionExist(userProfileDto.positionId())).thenReturn(true);
        when(phoneCodeService.isPhoneCodeExist(userProfileDto.phoneCodeId())).thenReturn(true);
    }
}