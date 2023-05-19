package by.itacademy.profiler.api.controllers;

import by.itacademy.profiler.persistence.model.LanguageProficiencyEnum;
import by.itacademy.profiler.usecasses.CompetenceService;
import by.itacademy.profiler.usecasses.CurriculumVitaeService;
import by.itacademy.profiler.usecasses.LanguageService;
import by.itacademy.profiler.usecasses.SkillService;
import by.itacademy.profiler.usecasses.dto.CompetenceRequestDto;
import by.itacademy.profiler.usecasses.dto.CompetenceResponseDto;
import by.itacademy.profiler.usecasses.dto.CvLanguageRequestDto;
import by.itacademy.profiler.util.CompetenceTestData;
import by.itacademy.profiler.util.CvLanguageTestData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static by.itacademy.profiler.util.CompetenceTestData.CV_COMPETENCE_URL_TEMPLATE;
import static by.itacademy.profiler.util.CompetenceTestData.INVALID_CV_UUID;
import static by.itacademy.profiler.util.CompetenceTestData.INVALID_CV_COMPETENCE_URL_TEMPLATE;
import static by.itacademy.profiler.util.CurriculumVitaeTestData.CV_UUID;
import static by.itacademy.profiler.util.CvLanguageTestData.getInvalidJsonCompetencesRequestDto;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CompetenceApiController.class)
@AutoConfigureMockMvc(addFilters = false)
class CompetenceApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CompetenceService competenceService;

    @MockBean
    private LanguageService languageService;

    @MockBean
    private SkillService skillService;

    @MockBean
    private CurriculumVitaeService curriculumVitaeService;

    @Test
    void shouldReturn201WhenCreateCompetenceAreSuccessful() throws Exception {
        CompetenceRequestDto competenceRequestDto = CompetenceTestData.createCompetenceRequestDto();

        when(competenceService.save(competenceRequestDto, CV_UUID)).thenReturn(any());
        setupCommonMockBehavior();

        mockMvc.perform(post(CV_COMPETENCE_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(competenceRequestDto)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void shouldReturnExpectedLanguageResponseJson() throws Exception {
        CompetenceRequestDto request = CompetenceTestData.createCompetenceRequestDto();
        CompetenceResponseDto response = CompetenceTestData.createCompetenceResponseDto();

        when(competenceService.save(request, CV_UUID)).thenReturn(response);
        setupCommonMockBehavior();

        MvcResult mvcResult = mockMvc.perform(post(CV_COMPETENCE_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        String expectedJson = objectMapper.writeValueAsString(response);
        String resultJson = mvcResult.getResponse().getContentAsString();
        assertEquals(expectedJson, resultJson);
    }

    @Test
    void shouldInvokeCvLanguageBusinessLogicWhenCreateCompetenceAreSuccessful() throws Exception {
        CompetenceRequestDto competenceRequestDto = CompetenceTestData.createCompetenceRequestDto();
        List<Long> languageIds = competenceRequestDto.languages().stream().map(CvLanguageRequestDto::id).toList();

        when(competenceService.save(competenceRequestDto, CV_UUID)).thenReturn(any());
        setupCommonMockBehavior();

        mockMvc.perform(post(CV_COMPETENCE_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(competenceRequestDto)))
                .andDo(print())
                .andExpect(status().isCreated());

        verify(competenceService, times(1)).save(competenceRequestDto, CV_UUID);
        verify(skillService, times(2)).isSkillsExistByIds(competenceRequestDto.skills());
        verify(languageService, times(2)).isLanguagesExistByIds(languageIds);
    }

    @ParameterizedTest
    @EnumSource(LanguageProficiencyEnum.class)
    void shouldReturn201WhenLanguageProficiencyIsValid(LanguageProficiencyEnum proficiency) throws Exception {
        CompetenceRequestDto competenceRequestDto = CompetenceTestData.createCompetenceRequestDto();
        CvLanguageRequestDto cvLanguageRequestDto = CvLanguageTestData.createCvLanguageRequestDto(proficiency);
        competenceRequestDto.languages().add(cvLanguageRequestDto);

        when(competenceService.save(competenceRequestDto, CV_UUID)).thenReturn(any());
        setupCommonMockBehavior();

        mockMvc.perform(post(CV_COMPETENCE_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(competenceRequestDto)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void shouldReturn400WhenJsonIsInvalid() throws Exception {
        String invalidJsonWithInvalidProficiency = getInvalidJsonCompetencesRequestDto();
        setupCommonMockBehavior();

        mockMvc.perform(post(CV_COMPETENCE_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJsonWithInvalidProficiency))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Malformed JSON request")));
    }

    @Test
    void shouldReturn400WhenLanguageIdIsNull() throws Exception {
        CompetenceRequestDto competenceRequestDto = CompetenceTestData.createCompetenceRequestDto();
        competenceRequestDto.languages().add(CvLanguageTestData.createCvLanguageRequestDtoWithIdNull());
        String expectedContent = "Field must not be null";


        when(competenceService.save(competenceRequestDto, CV_UUID)).thenReturn(any());
        setupCommonMockBehavior();

        mockMvc.perform(post(CV_COMPETENCE_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(competenceRequestDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(expectedContent)));
    }

    @Test
    void shouldReturn400WhenSkillIdIsNull() throws Exception {
        CompetenceRequestDto competenceRequestDto = CompetenceTestData.createCompetenceRequestDtoWhichContainsNull();
        String expectedContent = "Skill id must not be null";


        when(competenceService.save(competenceRequestDto, CV_UUID)).thenReturn(any());
        setupCommonMockBehavior();

        mockMvc.perform(post(CV_COMPETENCE_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(competenceRequestDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(expectedContent)));
    }

    @Test
    void shouldReturn400WhenCreateCompetenceWithEmptyLanguageList() throws Exception {
        CompetenceRequestDto competenceRequestDto = CompetenceTestData.createCompetenceRequestDtoWithEmptyLanguageList();
        String expectedContent = "{\"languages\":\"List of languages must have at least 1 language\"}";

        when(competenceService.save(competenceRequestDto, CV_UUID)).thenReturn(any());
        setupCommonMockBehavior();

        mockMvc.perform(post(CV_COMPETENCE_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(competenceRequestDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(expectedContent));
    }

    @Test
    void shouldReturn400WhenCreateCompetenceWithEmptySkillList() throws Exception {
        CompetenceRequestDto competenceRequestDto = CompetenceTestData.createCompetenceRequestDtoWithEmptySkillList();
        String expectedContent = "{\"skills\":\"List of skills must have at least 1 skill\"}";

        when(competenceService.save(competenceRequestDto, CV_UUID)).thenReturn(any());
        setupCommonMockBehavior();

        mockMvc.perform(post(CV_COMPETENCE_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(competenceRequestDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(expectedContent));
    }

    @Test
    void shouldReturn400WhenSkillListSizeIsMoreThan15() throws Exception {
        Long skillListSize = 20L;
        Long languageListSize = 5L;
        CompetenceRequestDto competenceRequestDto = CompetenceTestData.createCompetenceRequestDto(skillListSize, languageListSize);
        String expectedContent = "{\"skills\":\"Amount of skills should not be more than 15\"}";

        when(competenceService.save(competenceRequestDto, CV_UUID)).thenReturn(any());
        setupCommonMockBehavior();

        mockMvc.perform(post(CV_COMPETENCE_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(competenceRequestDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(expectedContent));
    }

    @Test
    void shouldReturn400WhenLanguageListSizeIsMoreThan5() throws Exception {
        Long skillListSize = 15L;
        Long languageListSize = 10L;
        CompetenceRequestDto competenceRequestDto = CompetenceTestData.createCompetenceRequestDto(skillListSize, languageListSize);
        String expectedContent = "{\"languages\":\"Amount of languages should not be more than 6\"}";

        when(competenceService.save(competenceRequestDto, CV_UUID)).thenReturn(any());
        setupCommonMockBehavior();

        mockMvc.perform(post(CV_COMPETENCE_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(competenceRequestDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(expectedContent));
    }

    @Test
    void shouldReturn200AndContentTypeJsonWhenGetListOfCompetences() throws Exception {
        CompetenceResponseDto responseDto = CompetenceTestData.createCompetenceResponseDto();

        when(competenceService.getCompetenceByCvUuid(CV_UUID)).thenReturn(responseDto);
        setupCommonMockBehavior();

        mockMvc.perform(get(CV_COMPETENCE_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    @Test
    void shouldReturn200AndInvokeBusinessLogicWhenGetListOfCompetences() throws Exception {
        setupCommonMockBehavior();

        mockMvc.perform(get(CV_COMPETENCE_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(competenceService, times(1)).getCompetenceByCvUuid(CV_UUID);
        verify(curriculumVitaeService, times(1)).isCurriculumVitaeExists(CV_UUID);
    }

    @Test
    void shouldReturn200AndCorrectJsonWhenGetListOfCompetences() throws Exception {
        CompetenceResponseDto responseDto = CompetenceTestData.createCompetenceResponseDto();
        String expectedJson = objectMapper.writeValueAsString(responseDto);

        when(competenceService.getCompetenceByCvUuid(CV_UUID)).thenReturn(responseDto);
        setupCommonMockBehavior();

        MvcResult mvcResult = mockMvc.perform(get(CV_COMPETENCE_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String actualResult = mvcResult.getResponse().getContentAsString();

        assertEquals(expectedJson, actualResult);
    }

    @Test
    void shouldReturn404WhenGetListOfCompetencesWithInvalidUuid() throws Exception {
        String expectedContent = String.format("\"CV with UUID %s not found!!!\"", INVALID_CV_UUID);

        when(curriculumVitaeService.isCurriculumVitaeExists(INVALID_CV_UUID)).thenReturn(false);

        mockMvc.perform(get(INVALID_CV_COMPETENCE_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString(expectedContent)));
    }

    private void setupCommonMockBehavior() {
        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID)).thenReturn(true);
        when(skillService.isSkillsExistByIds(argThat(list -> !list.isEmpty()))).thenReturn(true);
        when(languageService.isLanguagesExistByIds(argThat(list -> !list.isEmpty()))).thenReturn(true);
    }
}