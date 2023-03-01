package by.itacademy.profiler.api.controllers;

import by.itacademy.profiler.usecasses.AboutService;
import by.itacademy.profiler.usecasses.CurriculumVitaeService;
import by.itacademy.profiler.usecasses.dto.AboutDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    public static final String CORRECT_CV_UUID = "20c3cb38-abb4-11ed-afa1-0242ac120002";
    public static final String NOT_EXISTS_CV_UUID = "387bb8b3-4da9-40dd-a2b2-63970f16f6dd";
    public static final String ABOUT_URL_TEMPLATE = "/api/v1/cvs/{uuid}/about";
    public static final AboutDto CORRECT_ABOUT = new AboutDto("test description", "https://test.com/12334");
    public static final AboutDto ABOUT_WITH_INCORRECT_DESCRIPTION = new AboutDto("test descriptiontest descriptiontest descriptiontest descriptiontest descriptiontest descriptiontest descriptiontest descriptiontest descriptiontest descriptiontest descriptiontest descriptiontest descriptiontest descriptiontest descriptiontest descriptiontest descriptiontest descriptiontest descriptiontest descriptiontest descriptiontest descriptiontest descriptiontest descriptiontest descriptiontest descriptiontest descriptiontest descriptiontest descriptiontest descriptiontest descriptiontest descriptiontest descriptiontest descriptiontest descriptiontest descriptiontest descriptiontest description", "https://test.com/12334");
    public static final AboutDto ABOUT_WITH_INCORRECT_SELF_PRESENTATION = new AboutDto("test description", "");

    @BeforeEach
    void setUp() {
        when(curriculumVitaeService.isCurriculumVitaeExists(CORRECT_CV_UUID))
                .thenReturn(true);
        when(aboutService.save(any(String.class), eq(CORRECT_ABOUT)))
                .thenReturn(CORRECT_ABOUT);
        when(aboutService.update(any(String.class), eq(CORRECT_ABOUT)))
                .thenReturn(CORRECT_ABOUT);
        when(aboutService.getAbout(CORRECT_CV_UUID))
                .thenReturn(CORRECT_ABOUT);
    }

    @Test
    void shouldReturn201WhenSaveValidInputAboutSection() throws Exception {
        mockMvc.perform(
                post(
                        ABOUT_URL_TEMPLATE,
                        CORRECT_CV_UUID
                ).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(CORRECT_ABOUT))
        ).andExpect(status().isCreated());
    }

    @Test
    void shouldReturn404WhenNotExistsUuid() throws Exception {
        mockMvc.perform(
                post(
                        ABOUT_URL_TEMPLATE,
                        NOT_EXISTS_CV_UUID
                ).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(CORRECT_ABOUT))
        ).andExpect(status().isNotFound());
    }

    @Test
    void shouldReturn400WhenSaveInvalidDescriptionAboutSection() throws Exception {
        mockMvc.perform(
                post(
                        ABOUT_URL_TEMPLATE,
                        CORRECT_CV_UUID
                ).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(ABOUT_WITH_INCORRECT_DESCRIPTION))
        ).andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn400WhenSaveInvalidSelfPresentationAboutSection() throws Exception {
        mockMvc.perform(
                post(
                        ABOUT_URL_TEMPLATE,
                        CORRECT_CV_UUID
                ).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(ABOUT_WITH_INCORRECT_SELF_PRESENTATION))
        ).andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn201AndCallBusinessLogicWhenSaveValidAboutSection() throws Exception {
        mockMvc.perform(
                post(
                        ABOUT_URL_TEMPLATE,
                        CORRECT_CV_UUID
                ).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(CORRECT_ABOUT))
        ).andExpect(status().isCreated());

        verify(aboutService, times(1)).save(CORRECT_CV_UUID, CORRECT_ABOUT);
    }

    @Test
    void shouldReturnAddedAboutSectionWhenSaveValidAboutSection() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                post(
                        ABOUT_URL_TEMPLATE,
                        CORRECT_CV_UUID
                ).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(CORRECT_ABOUT))
        ).andExpect(status().isCreated()).andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        assertThat(contentAsString).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(CORRECT_ABOUT));
    }

    @Test
    void shouldReturn200WhenUpdateAboutSectionByValidInput() throws Exception {
        mockMvc.perform(
                put(
                        ABOUT_URL_TEMPLATE,
                        CORRECT_CV_UUID
                ).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(CORRECT_ABOUT))
        ).andExpect(status().isOk());
    }

    @Test
    void shouldReturn404WhenUpdateAboutSectionWithInvalidUuidOfCvInput() throws Exception {
        mockMvc.perform(
                put(
                        ABOUT_URL_TEMPLATE,
                        NOT_EXISTS_CV_UUID
                ).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(CORRECT_ABOUT))
        ).andExpect(status().isNotFound());
    }

    @Test
    void shouldReturn400WhenUpdateAboutSectionByInvalidDescription() throws Exception {
        mockMvc.perform(
                put(
                        ABOUT_URL_TEMPLATE,
                        CORRECT_CV_UUID
                ).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(ABOUT_WITH_INCORRECT_DESCRIPTION))
        ).andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn400WhenUpdateAboutSectionByInvalidSelfPresentation() throws Exception {
        mockMvc.perform(
                put(
                        ABOUT_URL_TEMPLATE,
                        CORRECT_CV_UUID
                ).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(ABOUT_WITH_INCORRECT_SELF_PRESENTATION))
        ).andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn200AndCallBusinessLogicWhenUpdateAboutSectionByValidInput() throws Exception {
        mockMvc.perform(
                put(
                        ABOUT_URL_TEMPLATE,
                        CORRECT_CV_UUID
                ).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(CORRECT_ABOUT))
        ).andExpect(status().isOk());

        verify(aboutService, times(1)).update(CORRECT_CV_UUID, CORRECT_ABOUT);
    }

    @Test
    void shouldReturnUpdatedAboutSectionWhenUpdateByValidInput() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                put(
                        ABOUT_URL_TEMPLATE,
                        CORRECT_CV_UUID
                ).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(CORRECT_ABOUT))
        ).andExpect(status().isOk()).andReturn();

        String updatedAbout = mvcResult.getResponse().getContentAsString();
        assertThat(updatedAbout).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(CORRECT_ABOUT));
    }

    @Test
    void shouldReturn200WhenGetAboutSectionByValidInput() throws Exception {
        mockMvc.perform(
                        get(
                                ABOUT_URL_TEMPLATE,
                                CORRECT_CV_UUID
                        ).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturn404WhenGetAboutSectionByInvalidInputUuid() throws Exception {
        mockMvc.perform(
                        get(
                                ABOUT_URL_TEMPLATE,
                                NOT_EXISTS_CV_UUID
                        ).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturn200AndCallBusinessLogicWhenGetAboutSectionByValidInput() throws Exception {
        mockMvc.perform(
                        get(
                                ABOUT_URL_TEMPLATE,
                                CORRECT_CV_UUID
                        ).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(aboutService, times(1)).getAbout(CORRECT_CV_UUID);
    }
}