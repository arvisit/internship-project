package by.itacademy.profiler.api.controllers;

import by.itacademy.profiler.usecasses.AboutService;
import by.itacademy.profiler.usecasses.CurriculumVitaeService;
import by.itacademy.profiler.usecasses.dto.AboutDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = AboutApiController.class)
@AutoConfigureMockMvc(addFilters = false)
class AboutApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AboutService aboutService;

    @MockBean
    private CurriculumVitaeService curriculumVitaeService;

    @Test
    void shouldReturn200WhenUpdateAboutSectionByValidInput() throws Exception {
        String cvUUID = "a034aa4d-5c1e-4ea7-bbff-5661ae5c45db";
        AboutDto about = new AboutDto("test description", "https://test.com/1");
        when(curriculumVitaeService.isCurriculumVitaeExists(cvUUID)).thenReturn(true);
        mockMvc.perform(put("/api/v1/cvs/{uuid}/about", cvUUID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(about))
        ).andExpect(status().isOk());
    }

    @Test
    void shouldReturn404WhenUpdateAboutSectionWithInvalidUuidOfCvInput() throws Exception {
        String cvUUID = "a034aa4d-5c1e-4ea7-bbff-5661ae5c45db";
        AboutDto about = new AboutDto("test description", "https://test.com/1");
        when(curriculumVitaeService.isCurriculumVitaeExists(cvUUID)).thenReturn(false);
        mockMvc.perform(put("/api/v1/cvs/{uuid}/about", cvUUID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(about))
        ).andExpect(status().isNotFound());
    }

    @Test
    void shouldReturn400WhenUpdateAboutSectionByInvalidDescription() throws Exception {
        String cvUUID = "a034aa4d-5c1e-4ea7-bbff-5661ae5c45db";
        AboutDto about = new AboutDto("test description test description test description test description" +
                " test description test description test description test description test description test description" +
                " test description test description test description test description test description test description" +
                " test description test description test description test description test description test description" +
                " test description test description test description test description test description test description" +
                " test description test description test description test description test description test description" +
                " test description test description test description test description", "https://test.com/12334");
        mockMvc.perform(put("/api/v1/cvs/{uuid}/about", cvUUID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(about))
        ).andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn400WhenUpdateAboutSectionByInvalidSelfPresentation() throws Exception {
        String cvUUID = "a034aa4d-5c1e-4ea7-bbff-5661ae5c45db";
        AboutDto about = new AboutDto("test description", "");
        mockMvc.perform(put("/api/v1/cvs/{uuid}/about", cvUUID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(about))
        ).andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn200AndCallBusinessLogicWhenUpdateAboutSectionByValidInput() throws Exception {
        String cvUUID = "a034aa4d-5c1e-4ea7-bbff-5661ae5c45db";
        AboutDto about = new AboutDto("test description", "https://test.com/1");
        when(curriculumVitaeService.isCurriculumVitaeExists(cvUUID)).thenReturn(true);
        when(aboutService.update(eq(cvUUID), eq(about))).thenReturn(about);
        mockMvc.perform(put("/api/v1/cvs/{uuid}/about", cvUUID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(about))
        ).andExpect(status().isOk());
        verify(aboutService, times(1)).update(cvUUID, about);
    }

    @Test
    void shouldReturnUpdatedAboutSectionWhenUpdateByValidInput() throws Exception {
        String cvUUID = "20c3cb38-abb4-11ed-afa1-0242ac120002";
        AboutDto about = new AboutDto("test description", "https://test.com/1");
        when(curriculumVitaeService.isCurriculumVitaeExists(cvUUID)).thenReturn(true);
        when(aboutService.update(eq(cvUUID), eq(about))).thenReturn(about);
        MvcResult mvcResult = mockMvc.perform(put("/api/v1/cvs/{uuid}/about", cvUUID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(about))
                ).andExpect(status().isOk())
                .andReturn();
        String updatedAbout = mvcResult.getResponse().getContentAsString();
        assertThat(updatedAbout).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(about));
    }
}