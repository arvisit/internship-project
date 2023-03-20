package by.itacademy.profiler.api.controllers;

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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserProfileApiController.class)
@AutoConfigureMockMvc(addFilters = false)
class GetUserProfileApiControllerTest {
    private static final String USERNAME = "User";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UserProfileService profileService;

    @MockBean
    private AuthService authService;
    private static final String USER_PROFILE_URL_TEMPLATE = "/api/v1/profile";

    @Test
    void shouldReturn200WhenGetValidInputUserProfile() throws Exception {
        String valideJsonUserProfileRequestDto = UserProfileTestData.getJsonUserProfileRequestDto();
        UserProfileDto userProfileDto = jsonToUserProfileDto(valideJsonUserProfileRequestDto);
        UserProfileResponseDto userProfileResponseDto = UserProfileTestData.getUserProfileResponseDto(userProfileDto);
        String expectedJsonUserProfileResponseDto = userProfileResponseDtoToJson(userProfileResponseDto);

        when(authService.getUsername()).thenReturn(USERNAME);
        when(profileService.getUserProfile()).thenReturn(userProfileResponseDto);
        mockMvc.perform(get(USER_PROFILE_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(expectedJsonUserProfileResponseDto));
    }

    @Test
    void shouldReturn404WhenGetProfileNotFound() throws Exception {
        when(authService.getUsername()).thenReturn(USERNAME);
        when(profileService.getUserProfile()).thenReturn(null);

        mockMvc.perform(get(USER_PROFILE_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Is.is("Profile not found")))
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    private UserProfileDto jsonToUserProfileDto(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, UserProfileDto.class);
    }

    private String userProfileResponseDtoToJson(UserProfileResponseDto userProfileResponseDto) throws JsonProcessingException {
        return objectMapper.writeValueAsString(userProfileResponseDto);
    }
}