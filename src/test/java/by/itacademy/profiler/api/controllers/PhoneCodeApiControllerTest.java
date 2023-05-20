package by.itacademy.profiler.api.controllers;

import by.itacademy.profiler.usecasses.PhoneCodeService;
import by.itacademy.profiler.usecasses.dto.PhoneCodeDto;
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

import static by.itacademy.profiler.util.PhoneCodeTestData.PHONE_CODE_URL_TEMPLATE;
import static by.itacademy.profiler.util.PhoneCodeTestData.createPhoneCodeDtoList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PhoneCodeApiController.class)
@AutoConfigureMockMvc(addFilters = false)
class PhoneCodeApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PhoneCodeService phoneCodeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturn200AndContentTypeJsonWhenGettingPhoneCodeList() throws Exception {
        List<PhoneCodeDto> PhoneCodeDtoList = createPhoneCodeDtoList();
        String expectedJson = objectMapper.writeValueAsString(PhoneCodeDtoList);

        when(phoneCodeService.getPhoneCodes()).thenReturn(PhoneCodeDtoList);

        MvcResult mvcResult = mockMvc.perform(get(PHONE_CODE_URL_TEMPLATE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String resultJson = mvcResult.getResponse().getContentAsString();
        assertEquals(expectedJson, resultJson);
    }

    @Test
    void shouldReturnExpectedPhoneCodeResponse() throws Exception {
        List<PhoneCodeDto> PhoneCodeDtoList = createPhoneCodeDtoList();
        PhoneCodeDto expectedPhoneCodeDto = PhoneCodeDtoList.get(0);

        when(phoneCodeService.getPhoneCodes()).thenReturn(PhoneCodeDtoList);

        mockMvc.perform(get(PHONE_CODE_URL_TEMPLATE))
                .andDo(print())
                .andExpect(jsonPath("$[0].id").value(expectedPhoneCodeDto.id()))
                .andExpect(status().isOk());
    }

    @Test
    void shouldInvokePhoneCodeServiceAndReturn200WhenGettingPhoneCodeList() throws Exception {
        List<PhoneCodeDto> PhoneCodeDtoList = createPhoneCodeDtoList();

        when(phoneCodeService.getPhoneCodes()).thenReturn(PhoneCodeDtoList);

        mockMvc.perform(get(PHONE_CODE_URL_TEMPLATE));

        verify(phoneCodeService, times(1)).getPhoneCodes();
    }
}