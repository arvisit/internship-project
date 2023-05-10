package by.itacademy.profiler.api.controllers;

import by.itacademy.profiler.usecasses.LanguageService;
import by.itacademy.profiler.usecasses.dto.LanguageResponseDto;
import by.itacademy.profiler.util.LanguageTestData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static by.itacademy.profiler.util.LanguageTestData.LANGUAGE_URL_TEMPLATE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = LanguageApiController.class)
@AutoConfigureMockMvc(addFilters = false)
class LanguageApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LanguageService languageService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturn200WhenGettingLanguageList() throws Exception {
        List<LanguageResponseDto> languages = LanguageTestData.createTestLanguageResponseDtoList();
        when(languageService.getLanguages()).thenReturn(languages);

        mockMvc.perform(get(LANGUAGE_URL_TEMPLATE))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnExpectedLanguageResponse() throws Exception {
        List<LanguageResponseDto> languages = LanguageTestData.createTestLanguageResponseDtoList();
        LanguageResponseDto expectedLanguage = languages.get(0);
        when(languageService.getLanguages()).thenReturn(languages);

        mockMvc.perform(get(LANGUAGE_URL_TEMPLATE))
                .andDo(print())
                .andExpect(jsonPath("$[0].name").value(expectedLanguage.name()))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnExpectedJsonResponseForLanguageList() throws Exception {
        List<LanguageResponseDto> languageResponseDtoList = LanguageTestData.createTestLanguageResponseDtoList();
        String expectedJson = objectMapper.writeValueAsString(languageResponseDtoList);
        when(languageService.getLanguages()).thenReturn(languageResponseDtoList);

        MvcResult mvcResult = mockMvc.perform(get(LANGUAGE_URL_TEMPLATE))
                .andDo(print())
                .andReturn();

        String resultJson = mvcResult.getResponse().getContentAsString();
        assertEquals(resultJson, expectedJson);
    }

    @Test
    void shouldInvokeLanguageServiceWhenGettingLanguageList() throws Exception {
        List<LanguageResponseDto> languageResponseDtoList = LanguageTestData.createTestLanguageResponseDtoList();
        when(languageService.getLanguages()).thenReturn(languageResponseDtoList);

        mockMvc.perform(get(LANGUAGE_URL_TEMPLATE));

        verify(languageService,times(1)).getLanguages();
    }
}