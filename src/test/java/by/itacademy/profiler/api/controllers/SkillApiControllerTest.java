package by.itacademy.profiler.api.controllers;

import by.itacademy.profiler.usecasses.PositionService;
import by.itacademy.profiler.usecasses.SkillService;
import by.itacademy.profiler.usecasses.dto.SkillResponseDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.core.Is;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static by.itacademy.profiler.util.SkillTestData.createSkillResponseDto;
import static by.itacademy.profiler.util.SkillTestData.getJsonSkillsResponseDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SkillApiController.class)
@AutoConfigureMockMvc(addFilters = false)
class SkillApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PositionService positionService;

    @MockBean
    private SkillService skillService;

    public static final String SKILLS_URL_TEMPLATE = "/api/v1/skills";
    private final String REQUEST_PARAM = "position_id";

    @Test
    void shouldReturn200WhenGetListOfSkills() throws Exception {
        List<SkillResponseDto> listOfSkills = createSkillResponseDtoList();
        when(skillService.getSkills(null)).thenReturn(listOfSkills);

        MvcResult mvcResult = mockMvc.perform(get(SKILLS_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        verify(skillService, times(1)).getSkills(null);
        String responseBody = mvcResult.getResponse().getContentAsString();
        List<SkillResponseDto> result = objectMapper.readValue(responseBody, new TypeReference<>() {});

        assertEquals("Java Core", result.get(0).name());
        assertEquals(responseBody, getJsonSkillsResponseDto());
        assertThat(responseBody).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(listOfSkills));
    }

    @Test
    void shouldReturn200WhenGetListOfSkillsByValidPositionId() throws Exception {
        List<SkillResponseDto> listOfSkills = createSkillResponseDtoList();
        when(positionService.isPositionExist(1L)).thenReturn(true);
        when(skillService.getSkills(1L)).thenReturn(listOfSkills);

        MvcResult mvcResult = mockMvc.perform(get(SKILLS_URL_TEMPLATE)
                        .param(REQUEST_PARAM, "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        verify(skillService, times(1)).getSkills(1L);
        String responseBody = mvcResult.getResponse().getContentAsString();
        List<SkillResponseDto> result = objectMapper.readValue(responseBody, new TypeReference<>() {});

        assertEquals("Java Core", result.get(0).name());
        assertEquals(responseBody, getJsonSkillsResponseDto());
        assertThat(responseBody).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(listOfSkills));
    }

    @Test
    void shouldReturn400WhenGetListOfSkillsByInvalidPositionId() throws Exception {
        when(positionService.isPositionExist(1L)).thenReturn(false);
        mockMvc.perform(get(SKILLS_URL_TEMPLATE).param(REQUEST_PARAM, "1"))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Is.is("{Invalid id: position not found=arg0}")))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void shouldReturn400WhenGetListOfSkillsByInvalidRequestParam() throws Exception {
        mockMvc.perform(get(SKILLS_URL_TEMPLATE).param(REQUEST_PARAM, "kd"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn400WhenGetListOfSkillsByInvalidRequestParamWithSymbol() throws Exception {
        mockMvc.perform(get(SKILLS_URL_TEMPLATE).param(REQUEST_PARAM, "/"))
                .andExpect(status().isBadRequest());
    }

    @NotNull
    private static List<SkillResponseDto> createSkillResponseDtoList() {
        List<SkillResponseDto> listOfSkills= new ArrayList<>();
        SkillResponseDto skillResponseDto = createSkillResponseDto().build();
        listOfSkills.add(skillResponseDto);
        return listOfSkills;
    }
}
