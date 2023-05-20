package by.itacademy.profiler.api.controllers;

import by.itacademy.profiler.usecasses.SphereService;
import by.itacademy.profiler.usecasses.dto.SphereResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static by.itacademy.profiler.util.SphereTestData.SPHERE_URL_TEMPLATE;
import static by.itacademy.profiler.util.SphereTestData.createSphereResponseDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SphereApiController.class)
@AutoConfigureMockMvc(addFilters = false)
class SphereApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SphereService sphereService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturn200WhenGettingSpheresList() throws Exception {
        List<SphereResponseDto> sphereResponseDtos = List.of(createSphereResponseDto().build());

        when(sphereService.getSpheres()).thenReturn(sphereResponseDtos);

        mockMvc.perform(get(SPHERE_URL_TEMPLATE))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnExpectedSphereResponse() throws Exception {
        SphereResponseDto sportResponseDto = createSphereResponseDto().withName("Sport").build();
        List<SphereResponseDto> sphereResponseDtos = List.of(createSphereResponseDto().build(), sportResponseDto);
        SphereResponseDto sphereResponseDto = sphereResponseDtos.get(0);

        when(sphereService.getSpheres()).thenReturn(sphereResponseDtos);

        mockMvc.perform(get(SPHERE_URL_TEMPLATE))
                .andDo(print())
                .andExpect(jsonPath("$[0].name").value(sphereResponseDto.name()))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnExpectedJsonResponseForSphereList() throws Exception {
        SphereResponseDto sportResponseDto = createSphereResponseDto().withName("Sport").build();
        List<SphereResponseDto> sphereResponseDtos = List.of(createSphereResponseDto().build(), sportResponseDto);
        String expectedJson = objectMapper.writeValueAsString(sphereResponseDtos);

        when(sphereService.getSpheres()).thenReturn(sphereResponseDtos);

        MvcResult mvcResult = mockMvc.perform(get(SPHERE_URL_TEMPLATE))
                .andDo(print())
                .andReturn();

        String resultJson = mvcResult.getResponse().getContentAsString();
        assertEquals(resultJson, expectedJson);
    }

    @Test
    void shouldInvokeLanguageServiceWhenGettingSphereList() throws Exception {
        SphereResponseDto sportResponseDto = createSphereResponseDto().withName("Sport").build();
        List<SphereResponseDto> sphereResponseDtos = List.of(createSphereResponseDto().build(), sportResponseDto);

        when(sphereService.getSpheres()).thenReturn(sphereResponseDtos);

        mockMvc.perform(get(SPHERE_URL_TEMPLATE));
        verify(sphereService, times(1)).getSpheres();
    }

}
