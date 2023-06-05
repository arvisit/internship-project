package by.itacademy.profiler.api.controllers;

import by.itacademy.profiler.usecasses.InstitutionService;
import by.itacademy.profiler.usecasses.dto.InstitutionResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static by.itacademy.profiler.util.InstitutionsTestData.INSTITUTION_URL_TEMPLATE;
import static by.itacademy.profiler.util.InstitutionsTestData.createInstitutionResponseDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = InstitutionApiController.class)
@AutoConfigureMockMvc(addFilters = false)
class InstitutionApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InstitutionService institutionService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturn200WhenGettingInstitutionsList() throws Exception {
        List<InstitutionResponseDto> institutions = List.of(createInstitutionResponseDto().build());
        when(institutionService.getInstitutions()).thenReturn(institutions);

        mockMvc.perform(get(INSTITUTION_URL_TEMPLATE))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnExpectedInstitutionResponse() throws Exception {
        List<InstitutionResponseDto> institutions = List.of(createInstitutionResponseDto().build());
        InstitutionResponseDto expectedInstitution = institutions.get(0);

        when(institutionService.getInstitutions()).thenReturn(institutions);

        mockMvc.perform(get(INSTITUTION_URL_TEMPLATE))
                .andDo(print())
                .andExpect(jsonPath("$[0].name").value(expectedInstitution.name()))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnExpectedJsonResponseForInstitutionsList() throws Exception {
        List<InstitutionResponseDto> institutionResponseDtoList = List.of(createInstitutionResponseDto().build());
        String expectedJson = objectMapper.writeValueAsString(institutionResponseDtoList);

        when(institutionService.getInstitutions()).thenReturn(institutionResponseDtoList);

        MvcResult mvcResult = mockMvc.perform(get(INSTITUTION_URL_TEMPLATE))
                .andDo(print())
                .andReturn();

        String resultJson = mvcResult.getResponse().getContentAsString();
        assertEquals(resultJson, expectedJson);
    }

    @Test
    void shouldInvokeInstitutionServiceWhenGettingInstitutionsList() throws Exception {
        List<InstitutionResponseDto> institutionResponseDtoList = List.of(createInstitutionResponseDto().build());
        when(institutionService.getInstitutions()).thenReturn(institutionResponseDtoList);

        mockMvc.perform(get(INSTITUTION_URL_TEMPLATE));

        verify(institutionService,times(1)).getInstitutions();
    }
}