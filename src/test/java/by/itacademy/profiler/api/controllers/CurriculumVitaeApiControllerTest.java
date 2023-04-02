package by.itacademy.profiler.api.controllers;

import by.itacademy.profiler.persistence.model.CvStatus;
import by.itacademy.profiler.usecasses.CountryService;
import by.itacademy.profiler.usecasses.CurriculumVitaeService;
import by.itacademy.profiler.usecasses.ImageValidationService;
import by.itacademy.profiler.usecasses.PositionService;
import by.itacademy.profiler.usecasses.dto.CurriculumVitaeRequestDto;
import by.itacademy.profiler.usecasses.dto.CurriculumVitaeResponseDto;
import by.itacademy.profiler.usecasses.util.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CurriculumVitaeApiController.class)
@AutoConfigureMockMvc(addFilters = false)
class CurriculumVitaeApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CurriculumVitaeService curriculumVitaeService;

    @MockBean
    private AuthService authService;

    @MockBean
    private ImageValidationService imageValidationService;

    @MockBean
    private CountryService countryService;

    @MockBean
    private PositionService positionService;

    private static final String API_V1_CVS_UUID = "/api/v1/cvs/{uuid}";
    private static final String API_V1_CVS = "/api/v1/cvs";
    private static final String CV_UUID = "0a5a28ca-e960-420c-af53-50e6f6e80bf2";
    private static final String IMAGE_UUID = "95f5f3cf-ac52-4638-aad1-12d1e836fdd1";
    private static final long COUNTRY_ID = 1L;
    private static final long POSITION_ID = 1L;
    private static final String NAME = "Name";
    private static final String INVALID_NAME = "Invalid Namenamenamenamenamenamenamenamenamenamename";
    private static final String SURNAME = "Surname";
    private static final String INVALID_SURNAME = "Invalid Surnamenamenamenamenamenamenamenamenamename";
    private static final String CITY = "City";
    private static final String INVALID_CITY = "Invalid City-citycitycitycitycitycitycitycitycitycity";
    private static final boolean IS_READY_TO_RELOCATE = true;
    private static final boolean IS_READY_FOR_REMOTE_WORK = true;
    private static final CurriculumVitaeRequestDto CURRICULUM_VITAE_REQUEST_DTO = new CurriculumVitaeRequestDto(IMAGE_UUID, NAME, SURNAME, POSITION_ID, COUNTRY_ID, CITY, IS_READY_TO_RELOCATE, IS_READY_FOR_REMOTE_WORK);
    private static final String POSITION = "Some position";
    private static final String COUNTRY = "Some-country";
    private static final boolean IS_CONTACTS_EXISTS = false;
    private static final boolean IS_ABOUT_EXISTS = false;
    private static final String STATUS = CvStatus.DRAFT.name();
    private static final CurriculumVitaeResponseDto CURRICULUM_VITAE_RESPONSE_DTO = new CurriculumVitaeResponseDto(CV_UUID, IMAGE_UUID, NAME, SURNAME, POSITION_ID, POSITION, COUNTRY_ID, COUNTRY, CITY, IS_READY_TO_RELOCATE, IS_READY_FOR_REMOTE_WORK, IS_CONTACTS_EXISTS, IS_ABOUT_EXISTS, STATUS);

    @Test
    void shouldReturn400WhenSavePersonalInfoSectionIfImageUuidNotBelongsToUser() throws Exception {

        when(imageValidationService.isImageBelongsToUser(IMAGE_UUID)).thenReturn(false);
        when(countryService.isCountryExist(COUNTRY_ID)).thenReturn(true);
        when(positionService.isPositionExist(POSITION_ID)).thenReturn(true);

        mockMvc.perform(
                        post(
                                API_V1_CVS
                        ).contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(CURRICULUM_VITAE_REQUEST_DTO))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("imageUuid").value("Image UUID is not valid!"));

        verify(imageValidationService, times(1)).isImageBelongsToUser(IMAGE_UUID);
    }

    @Test
    void shouldReturn400WhenSavePersonalInfoSectionIfImageUuidIsUsedInPersonalDetails() throws Exception {

        when(imageValidationService.isImageBelongsToUser(IMAGE_UUID)).thenReturn(true);
        when(imageValidationService.validateImageForCv(IMAGE_UUID)).thenReturn(false);
        when(countryService.isCountryExist(COUNTRY_ID)).thenReturn(true);
        when(positionService.isPositionExist(POSITION_ID)).thenReturn(true);


        mockMvc.perform(
                        post(
                                API_V1_CVS
                        ).contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(CURRICULUM_VITAE_REQUEST_DTO))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("imageUuid").value("Image UUID is not valid!"));

        verify(imageValidationService, times(1)).isImageBelongsToUser(IMAGE_UUID);
        verify(imageValidationService, times(1)).validateImageForCv(IMAGE_UUID);
    }

    @Test
    void shouldReturn400WhenSavePersonalInfoSectionIfCountryIdIsNotExist() throws Exception {

        when(imageValidationService.isImageBelongsToUser(IMAGE_UUID)).thenReturn(true);
        when(imageValidationService.validateImageForCv(IMAGE_UUID)).thenReturn(true);
        when(countryService.isCountryExist(COUNTRY_ID)).thenReturn(false);
        when(positionService.isPositionExist(POSITION_ID)).thenReturn(true);

        mockMvc.perform(
                        post(
                                API_V1_CVS
                        ).contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(CURRICULUM_VITAE_REQUEST_DTO))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("countryId").value("Invalid id: country not found"));

        verify(countryService, times(1)).isCountryExist(COUNTRY_ID);
    }

    @Test
    void shouldReturn400WhenSavePersonalInfoSectionIfPositionIdIsNotExist() throws Exception {

        when(imageValidationService.isImageBelongsToUser(IMAGE_UUID)).thenReturn(true);
        when(imageValidationService.validateImageForCv(IMAGE_UUID)).thenReturn(true);
        when(countryService.isCountryExist(COUNTRY_ID)).thenReturn(true);
        when(positionService.isPositionExist(POSITION_ID)).thenReturn(false);

        mockMvc.perform(
                        post(
                                API_V1_CVS
                        ).contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(CURRICULUM_VITAE_REQUEST_DTO))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("positionId").value("Invalid id: position not found"));

        verify(positionService, times(1)).isPositionExist(POSITION_ID);
    }

    @Test
    void shouldReturn400WhenSavePersonalInfoSectionIfCreationOfNewCvIsNotAvailable() throws Exception {

        when(imageValidationService.isImageBelongsToUser(IMAGE_UUID)).thenReturn(true);
        when(imageValidationService.validateImageForCv(IMAGE_UUID)).thenReturn(true);
        when(countryService.isCountryExist(COUNTRY_ID)).thenReturn(true);
        when(positionService.isPositionExist(POSITION_ID)).thenReturn(true);
        when(curriculumVitaeService.isCreationCvAvailable()).thenReturn(false);

        mockMvc.perform(
                        post(
                                API_V1_CVS
                        ).contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(CURRICULUM_VITAE_REQUEST_DTO))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("statusCode").value(400))
                .andExpect(jsonPath("message").value("Curriculum vitae creation limit exceeded"))
                .andExpect(jsonPath("timeStamp").exists());

        verify(curriculumVitaeService, times(1)).isCreationCvAvailable();
    }

    @Test
    void shouldReturn201WhenSaveValidInputPersonalInfoSection() throws Exception {

        when(imageValidationService.isImageBelongsToUser(IMAGE_UUID)).thenReturn(true);
        when(imageValidationService.validateImageForCv(IMAGE_UUID)).thenReturn(true);
        when(countryService.isCountryExist(COUNTRY_ID)).thenReturn(true);
        when(positionService.isPositionExist(POSITION_ID)).thenReturn(true);
        when(curriculumVitaeService.isCreationCvAvailable()).thenReturn(true);

        mockMvc.perform(
                post(
                        API_V1_CVS
                ).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(CURRICULUM_VITAE_REQUEST_DTO))
        ).andExpect(status().isCreated());
    }

    @Test
    void shouldReturnPersonalInfoSectionWhenSaveValidPersonalInfoSectionAndCallBusinessLogic() throws Exception {

        when(imageValidationService.isImageBelongsToUser(IMAGE_UUID)).thenReturn(true);
        when(imageValidationService.validateImageForCv(IMAGE_UUID)).thenReturn(true);
        when(countryService.isCountryExist(COUNTRY_ID)).thenReturn(true);
        when(positionService.isPositionExist(POSITION_ID)).thenReturn(true);
        when(curriculumVitaeService.isCreationCvAvailable()).thenReturn(true);
        when(curriculumVitaeService.save(CURRICULUM_VITAE_REQUEST_DTO)).thenReturn(CURRICULUM_VITAE_RESPONSE_DTO);

        mockMvc.perform(
                        post(
                                API_V1_CVS
                        ).contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(CURRICULUM_VITAE_REQUEST_DTO))
                ).andExpect(status().isCreated())
                .andExpect(jsonPath("imageUuid").value(CURRICULUM_VITAE_REQUEST_DTO.imageUuid()))
                .andExpect(jsonPath("name").value(CURRICULUM_VITAE_REQUEST_DTO.name()))
                .andExpect(jsonPath("surname").value(CURRICULUM_VITAE_REQUEST_DTO.surname()))
                .andExpect(jsonPath("positionId").value(CURRICULUM_VITAE_REQUEST_DTO.positionId()))
                .andExpect(jsonPath("countryId").value(CURRICULUM_VITAE_REQUEST_DTO.countryId()))
                .andExpect(jsonPath("city").value(CURRICULUM_VITAE_REQUEST_DTO.city()))
                .andExpect(jsonPath("isReadyToRelocate").value(CURRICULUM_VITAE_REQUEST_DTO.isReadyToRelocate()))
                .andExpect(jsonPath("isReadyForRemoteWork").value(CURRICULUM_VITAE_REQUEST_DTO.isReadyForRemoteWork()))
                .andExpect(jsonPath("uuid").exists());

        verify(curriculumVitaeService, times(1)).save(CURRICULUM_VITAE_REQUEST_DTO);
    }

    @Test
    void shouldReturn400WhenSaveCvRequestWithInvalidNameField() throws Exception {

        CurriculumVitaeRequestDto CvRequestDtoWithInvalidName =
                new CurriculumVitaeRequestDto(IMAGE_UUID, INVALID_NAME, SURNAME, POSITION_ID, COUNTRY_ID, CITY, true, true);

        when(imageValidationService.isImageBelongsToUser(IMAGE_UUID)).thenReturn(true);
        when(imageValidationService.validateImageForCv(IMAGE_UUID)).thenReturn(true);
        when(countryService.isCountryExist(COUNTRY_ID)).thenReturn(true);
        when(positionService.isPositionExist(POSITION_ID)).thenReturn(true);
        when(curriculumVitaeService.isCreationCvAvailable()).thenReturn(true);

        mockMvc.perform(
                        post(
                                API_V1_CVS
                        ).contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(CvRequestDtoWithInvalidName))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("name").value("Maximum length of name is 50 symbols"));
    }

    @Test
    void shouldReturn400WhenSaveCvRequestWithInvalidSurnameField() throws Exception {

        CurriculumVitaeRequestDto CvRequestDtoWithInvalidSurname =
                new CurriculumVitaeRequestDto(IMAGE_UUID, NAME, INVALID_SURNAME, POSITION_ID, COUNTRY_ID, CITY, true, true);

        when(imageValidationService.isImageBelongsToUser(IMAGE_UUID)).thenReturn(true);
        when(imageValidationService.validateImageForCv(IMAGE_UUID)).thenReturn(true);
        when(countryService.isCountryExist(COUNTRY_ID)).thenReturn(true);
        when(positionService.isPositionExist(POSITION_ID)).thenReturn(true);
        when(curriculumVitaeService.isCreationCvAvailable()).thenReturn(true);

        mockMvc.perform(
                        post(
                                API_V1_CVS
                        ).contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(CvRequestDtoWithInvalidSurname))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("surname").value("Maximum length of surname is 50 symbols"));
    }

    @Test
    void shouldReturn400WhenSaveCvRequestWithInvalidCityField() throws Exception {

        CurriculumVitaeRequestDto CvRequestDtoWithInvalidCity =
                new CurriculumVitaeRequestDto(IMAGE_UUID, NAME, SURNAME, POSITION_ID, COUNTRY_ID, INVALID_CITY, true, true);

        when(imageValidationService.isImageBelongsToUser(IMAGE_UUID)).thenReturn(true);
        when(imageValidationService.validateImageForCv(IMAGE_UUID)).thenReturn(true);
        when(countryService.isCountryExist(COUNTRY_ID)).thenReturn(true);
        when(positionService.isPositionExist(POSITION_ID)).thenReturn(true);
        when(curriculumVitaeService.isCreationCvAvailable()).thenReturn(true);

        mockMvc.perform(
                        post(
                                API_V1_CVS
                        ).contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(CvRequestDtoWithInvalidCity))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("city").value("Maximum length of city name is 50 symbols"));
    }

    @Test
    void shouldReturn200WhenGetPersonalInfoSection() throws Exception {

        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID)).thenReturn(true);

        mockMvc.perform(
                        get(
                                API_V1_CVS_UUID,
                                CV_UUID
                        ).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnCvResponseDtoWhenGetPersonalInfoSectionAndCallBusinessLogic() throws Exception {

        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID)).thenReturn(true);
        when(curriculumVitaeService.getCvOfUser(CV_UUID)).thenReturn(CURRICULUM_VITAE_RESPONSE_DTO);

        mockMvc.perform(
                        get(
                                API_V1_CVS_UUID,
                                CV_UUID
                        ).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("uuid").value(CURRICULUM_VITAE_RESPONSE_DTO.uuid()))
                .andExpect(jsonPath("imageUuid").value(CURRICULUM_VITAE_RESPONSE_DTO.imageUuid()))
                .andExpect(jsonPath("name").value(CURRICULUM_VITAE_RESPONSE_DTO.name()))
                .andExpect(jsonPath("surname").value(CURRICULUM_VITAE_RESPONSE_DTO.surname()))
                .andExpect(jsonPath("positionId").value(CURRICULUM_VITAE_RESPONSE_DTO.positionId()))
                .andExpect(jsonPath("position").value(CURRICULUM_VITAE_RESPONSE_DTO.position()))
                .andExpect(jsonPath("countryId").value(CURRICULUM_VITAE_RESPONSE_DTO.countryId()))
                .andExpect(jsonPath("country").value(CURRICULUM_VITAE_RESPONSE_DTO.country()))
                .andExpect(jsonPath("city").value(CURRICULUM_VITAE_RESPONSE_DTO.city()))
                .andExpect(jsonPath("isReadyToRelocate").value(CURRICULUM_VITAE_RESPONSE_DTO.isReadyToRelocate()))
                .andExpect(jsonPath("isReadyForRemoteWork").value(CURRICULUM_VITAE_RESPONSE_DTO.isReadyForRemoteWork()))
                .andExpect(jsonPath("isContactsExists").value(CURRICULUM_VITAE_RESPONSE_DTO.isContactsExists()))
                .andExpect(jsonPath("isAboutExists").value(CURRICULUM_VITAE_RESPONSE_DTO.isAboutExists()))
                .andExpect(jsonPath("status").value(CURRICULUM_VITAE_RESPONSE_DTO.status()));

        verify(curriculumVitaeService, times(1)).getCvOfUser(CV_UUID);
    }

    @Test
    void shouldReturn404WhenGetPersonalInfoSectionIfCvUuidIsNotExist() throws Exception {

        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID)).thenReturn(false);

        mockMvc.perform(
                        get(
                                API_V1_CVS_UUID,
                                CV_UUID
                        ).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturn200WhenGetAllCvsOfUserIfAtLeastOneCvExist() throws Exception {

        when(curriculumVitaeService.getAllCvOfUser()).thenReturn(List.of(CURRICULUM_VITAE_RESPONSE_DTO));

        mockMvc.perform(
                        get(
                                API_V1_CVS
                        ).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnListOfCvResponseDtoWhenGetAllCvsOfUserIfAtLeastOneCvExistAndCallBusinessLogic() throws Exception {

        when(curriculumVitaeService.getAllCvOfUser()).thenReturn(List.of(CURRICULUM_VITAE_RESPONSE_DTO));

        mockMvc.perform(
                        get(
                                API_V1_CVS
                        ).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").exists())
                .andExpect(jsonPath("$[0].uuid").value(CURRICULUM_VITAE_RESPONSE_DTO.uuid()))
                .andExpect(jsonPath("$[0].imageUuid").value(CURRICULUM_VITAE_RESPONSE_DTO.imageUuid()))
                .andExpect(jsonPath("$[0].name").value(CURRICULUM_VITAE_RESPONSE_DTO.name()))
                .andExpect(jsonPath("$[0].surname").value(CURRICULUM_VITAE_RESPONSE_DTO.surname()))
                .andExpect(jsonPath("$[0].positionId").value(CURRICULUM_VITAE_RESPONSE_DTO.positionId()))
                .andExpect(jsonPath("$[0].position").value(CURRICULUM_VITAE_RESPONSE_DTO.position()))
                .andExpect(jsonPath("$[0].countryId").value(CURRICULUM_VITAE_RESPONSE_DTO.countryId()))
                .andExpect(jsonPath("$[0].country").value(CURRICULUM_VITAE_RESPONSE_DTO.country()))
                .andExpect(jsonPath("$[0].city").value(CURRICULUM_VITAE_RESPONSE_DTO.city()))
                .andExpect(jsonPath("$[0].isReadyToRelocate").value(CURRICULUM_VITAE_RESPONSE_DTO.isReadyToRelocate()))
                .andExpect(jsonPath("$[0].isReadyForRemoteWork").value(CURRICULUM_VITAE_RESPONSE_DTO.isReadyForRemoteWork()))
                .andExpect(jsonPath("$[0].isContactsExists").value(CURRICULUM_VITAE_RESPONSE_DTO.isContactsExists()))
                .andExpect(jsonPath("$[0].isAboutExists").value(CURRICULUM_VITAE_RESPONSE_DTO.isAboutExists()))
                .andExpect(jsonPath("$[0].status").value(CURRICULUM_VITAE_RESPONSE_DTO.status()));

        verify(curriculumVitaeService, times(1)).getAllCvOfUser();
        verify(authService, times(1)).getUsername();
    }

    @Test
    void shouldReturn204WhenGetAllCvsOfUserIfDontHaveCv() throws Exception {

        when(curriculumVitaeService.getAllCvOfUser()).thenReturn(List.of());

        mockMvc.perform(
                        get(
                                API_V1_CVS
                        ).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturn400WhenPutPersonalInfoSectionIfImageUuidNotBelongsToUser() throws Exception {

        when(imageValidationService.isImageBelongsToUser(IMAGE_UUID)).thenReturn(false);
        when(countryService.isCountryExist(COUNTRY_ID)).thenReturn(true);
        when(positionService.isPositionExist(POSITION_ID)).thenReturn(true);

        mockMvc.perform(
                        put(
                                API_V1_CVS_UUID,
                                CV_UUID
                        ).contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(CURRICULUM_VITAE_REQUEST_DTO))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("imageUuid").value("Image UUID is not valid!"));

        verify(imageValidationService, times(1)).isImageBelongsToUser(IMAGE_UUID);
    }

    @Test
    void shouldReturn400WhenPutPersonalInfoSectionIfImageUuidIsUsedInPersonalDetails() throws Exception {

        when(imageValidationService.isImageBelongsToUser(IMAGE_UUID)).thenReturn(true);
        when(imageValidationService.validateImageForCv(IMAGE_UUID)).thenReturn(false);
        when(countryService.isCountryExist(COUNTRY_ID)).thenReturn(true);
        when(positionService.isPositionExist(POSITION_ID)).thenReturn(true);


        mockMvc.perform(
                        put(
                                API_V1_CVS_UUID,
                                CV_UUID
                        ).contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(CURRICULUM_VITAE_REQUEST_DTO))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("imageUuid").value("Image UUID is not valid!"));

        verify(imageValidationService, times(1)).isImageBelongsToUser(IMAGE_UUID);
        verify(imageValidationService, times(1)).validateImageForCv(IMAGE_UUID);
    }

    @Test
    void shouldReturn400WhenPutPersonalInfoSectionIfCountryIdIsNotExist() throws Exception {

        when(imageValidationService.isImageBelongsToUser(IMAGE_UUID)).thenReturn(true);
        when(imageValidationService.validateImageForCv(IMAGE_UUID)).thenReturn(true);
        when(countryService.isCountryExist(COUNTRY_ID)).thenReturn(false);
        when(positionService.isPositionExist(POSITION_ID)).thenReturn(true);

        mockMvc.perform(
                        put(
                                API_V1_CVS_UUID,
                                CV_UUID
                        ).contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(CURRICULUM_VITAE_REQUEST_DTO))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("countryId").value("Invalid id: country not found"));

        verify(countryService, times(1)).isCountryExist(COUNTRY_ID);
    }

    @Test
    void shouldReturn400WhenPutPersonalInfoSectionIfPositionIdIsNotExist() throws Exception {

        when(imageValidationService.isImageBelongsToUser(IMAGE_UUID)).thenReturn(true);
        when(imageValidationService.validateImageForCv(IMAGE_UUID)).thenReturn(true);
        when(countryService.isCountryExist(COUNTRY_ID)).thenReturn(true);
        when(positionService.isPositionExist(POSITION_ID)).thenReturn(false);

        mockMvc.perform(
                        put(
                                API_V1_CVS_UUID,
                                CV_UUID
                        ).contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(CURRICULUM_VITAE_REQUEST_DTO))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("positionId").value("Invalid id: position not found"));

        verify(positionService, times(1)).isPositionExist(POSITION_ID);
    }

    @Test
    void shouldReturn404WhenPutPersonalInfoSectionIfCvUuidIsNotExist() throws Exception {

        when(imageValidationService.isImageBelongsToUser(IMAGE_UUID)).thenReturn(true);
        when(imageValidationService.validateImageForCv(IMAGE_UUID)).thenReturn(true);
        when(countryService.isCountryExist(COUNTRY_ID)).thenReturn(true);
        when(positionService.isPositionExist(POSITION_ID)).thenReturn(true);
        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID)).thenReturn(false);

        mockMvc.perform(
                        put(
                                API_V1_CVS_UUID,
                                CV_UUID
                        ).contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(CURRICULUM_VITAE_REQUEST_DTO))
                ).andExpect(status().isNotFound())
                .andExpect(jsonPath("statusCode").value(404))
                .andExpect(jsonPath("message").value(String.format("CV with UUID %s not found!!!", CV_UUID)))
                .andExpect(jsonPath("timeStamp").exists());

        verify(curriculumVitaeService, times(1)).isCurriculumVitaeExists(CV_UUID);
    }

    @Test
    void shouldReturn200WhenUpdateValidInputPersonalInfoSection() throws Exception {

        when(imageValidationService.isImageBelongsToUser(IMAGE_UUID)).thenReturn(true);
        when(imageValidationService.validateImageForCv(IMAGE_UUID)).thenReturn(true);
        when(countryService.isCountryExist(COUNTRY_ID)).thenReturn(true);
        when(positionService.isPositionExist(POSITION_ID)).thenReturn(true);
        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID)).thenReturn(true);

        mockMvc.perform(
                put(
                        API_V1_CVS_UUID,
                        CV_UUID
                ).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(CURRICULUM_VITAE_REQUEST_DTO))
        ).andExpect(status().isOk());
    }

    @Test
    void shouldReturnCvResponseDtoWhenUpdateValidInputPersonalInfoSection() throws Exception {

        when(imageValidationService.isImageBelongsToUser(IMAGE_UUID)).thenReturn(true);
        when(imageValidationService.validateImageForCv(IMAGE_UUID)).thenReturn(true);
        when(countryService.isCountryExist(COUNTRY_ID)).thenReturn(true);
        when(positionService.isPositionExist(POSITION_ID)).thenReturn(true);
        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID)).thenReturn(true);
        when(curriculumVitaeService.update(CV_UUID, CURRICULUM_VITAE_REQUEST_DTO)).thenReturn(CURRICULUM_VITAE_RESPONSE_DTO);

        mockMvc.perform(
                        put(
                                API_V1_CVS_UUID,
                                CV_UUID
                        ).contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(CURRICULUM_VITAE_REQUEST_DTO))
                ).andExpect(status().isOk())
                .andExpect(jsonPath("uuid").value(CURRICULUM_VITAE_RESPONSE_DTO.uuid()))
                .andExpect(jsonPath("imageUuid").value(CURRICULUM_VITAE_RESPONSE_DTO.imageUuid()))
                .andExpect(jsonPath("name").value(CURRICULUM_VITAE_RESPONSE_DTO.name()))
                .andExpect(jsonPath("surname").value(CURRICULUM_VITAE_RESPONSE_DTO.surname()))
                .andExpect(jsonPath("positionId").value(CURRICULUM_VITAE_RESPONSE_DTO.positionId()))
                .andExpect(jsonPath("position").value(CURRICULUM_VITAE_RESPONSE_DTO.position()))
                .andExpect(jsonPath("countryId").value(CURRICULUM_VITAE_RESPONSE_DTO.countryId()))
                .andExpect(jsonPath("country").value(CURRICULUM_VITAE_RESPONSE_DTO.country()))
                .andExpect(jsonPath("city").value(CURRICULUM_VITAE_RESPONSE_DTO.city()))
                .andExpect(jsonPath("isReadyToRelocate").value(CURRICULUM_VITAE_RESPONSE_DTO.isReadyToRelocate()))
                .andExpect(jsonPath("isReadyForRemoteWork").value(CURRICULUM_VITAE_RESPONSE_DTO.isReadyForRemoteWork()))
                .andExpect(jsonPath("isContactsExists").value(CURRICULUM_VITAE_RESPONSE_DTO.isContactsExists()))
                .andExpect(jsonPath("isAboutExists").value(CURRICULUM_VITAE_RESPONSE_DTO.isAboutExists()))
                .andExpect(jsonPath("status").value(CURRICULUM_VITAE_RESPONSE_DTO.status()));

        verify(curriculumVitaeService, times(1)).update(CV_UUID, CURRICULUM_VITAE_REQUEST_DTO);
    }
}