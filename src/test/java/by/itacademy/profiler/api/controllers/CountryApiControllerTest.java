package by.itacademy.profiler.api.controllers;

import by.itacademy.profiler.usecasses.CountryService;
import by.itacademy.profiler.usecasses.dto.CountryDto;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static by.itacademy.profiler.util.CountryTestData.COUNTRIES_URL_TEMPLATE;
import static by.itacademy.profiler.util.CountryTestData.DEFAULT_COUNTRY_NAME;
import static by.itacademy.profiler.util.CountryTestData.createCountryDto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CountryApiController.class)
@AutoConfigureMockMvc(addFilters = false)
class CountryApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CountryService countryService;

    @Test
    void shouldReturn200AndJsonResponseWhenGetListOfCountries() throws Exception {
        mockMvc.perform(get(COUNTRIES_URL_TEMPLATE)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void shouldInvokeCountryServiceWhenGetListOfCountries() throws Exception {
        List<CountryDto> countries = List.of(createCountryDto().withCountryName(DEFAULT_COUNTRY_NAME).build());
        when(countryService.getCountries()).thenReturn(countries);

        MvcResult mvcResult = mockMvc.perform(get(COUNTRIES_URL_TEMPLATE)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        verify(countryService, times(1)).getCountries();
        String responseBody = mvcResult.getResponse().getContentAsString();
        List<CountryDto> result = objectMapper.readValue(responseBody, new TypeReference<>() {});

        assertEquals(DEFAULT_COUNTRY_NAME, result.get(0).countryName());
    }

    @Test
    void shouldReturnListOfCountryDtosWhenGetListOfCountries() throws Exception {
        List<CountryDto> countries = List.of(createCountryDto().build());
        when(countryService.getCountries()).thenReturn(countries);

        MvcResult mvcResult = mockMvc.perform(get(COUNTRIES_URL_TEMPLATE)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();

        assertThat(responseBody).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(countries));
    }
}
