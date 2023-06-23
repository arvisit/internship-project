package by.itacademy.profiler.api.controllers;

import by.itacademy.profiler.api.exception.AdditionalInformationNotFoundException;
import by.itacademy.profiler.usecasses.AdditionalInformationService;
import by.itacademy.profiler.usecasses.CurriculumVitaeService;
import by.itacademy.profiler.usecasses.dto.AdditionalInformationRequestDto;
import by.itacademy.profiler.usecasses.dto.AdditionalInformationResponseDto;
import by.itacademy.profiler.usecasses.dto.AwardDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.YearMonth;
import java.util.List;
import java.util.stream.Stream;

import static by.itacademy.profiler.util.AdditionalInfoTestData.ADDITIONAL_INFORMATION_CV_UUID;
import static by.itacademy.profiler.util.AdditionalInfoTestData.ADDITIONAL_INFORMATION_NO_CONTENT_ERROR;
import static by.itacademy.profiler.util.AdditionalInfoTestData.CV_ADDITIONAL_INFORMATION_URL_TEMPLATE;
import static by.itacademy.profiler.util.AdditionalInfoTestData.CV_NOT_FOUND_ERROR;
import static by.itacademy.profiler.util.AdditionalInfoTestData.FIELD_ADDITIONAL_INFORMATION_NOT_NULL_ERROR;
import static by.itacademy.profiler.util.AdditionalInfoTestData.FIELD_TITLE_NOT_NULL_ERROR;
import static by.itacademy.profiler.util.AdditionalInfoTestData.MAXLENGTH_ADDITIONAL_INFORMATION_ERROR;
import static by.itacademy.profiler.util.AdditionalInfoTestData.MAXLENGTH_VALIDATE_DESCRIPTION_ERROR;
import static by.itacademy.profiler.util.AdditionalInfoTestData.MAXLENGTH_VALIDATE_HOBBY_ERROR;
import static by.itacademy.profiler.util.AdditionalInfoTestData.MAXLENGTH_VALIDATE_ISSUER_ERROR;
import static by.itacademy.profiler.util.AdditionalInfoTestData.MAXLENGTH_VALIDATE_TITLE_ERROR;
import static by.itacademy.profiler.util.AdditionalInfoTestData.REGEXP_VALIDATE_ADDITIONAL_INFORMATION_ERROR;
import static by.itacademy.profiler.util.AdditionalInfoTestData.REGEXP_VALIDATE_DESCRIPTION_ERROR;
import static by.itacademy.profiler.util.AdditionalInfoTestData.REGEXP_VALIDATE_HOBBY_ERROR;
import static by.itacademy.profiler.util.AdditionalInfoTestData.REGEXP_VALIDATE_ISSUER_ERROR;
import static by.itacademy.profiler.util.AdditionalInfoTestData.REGEXP_VALIDATE_TITLE_ERROR;
import static by.itacademy.profiler.util.AdditionalInfoTestData.createAdditionalInformationRequestDto;
import static by.itacademy.profiler.util.AdditionalInfoTestData.createAdditionalInformationResponseDto;
import static by.itacademy.profiler.util.AdditionalInfoTestData.createAwardDto;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AdditionalInformationApiController.class)
@AutoConfigureMockMvc(addFilters = false)
class AdditionalInformationApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AdditionalInformationService additionalInformationService;

    @MockBean
    private CurriculumVitaeService curriculumVitaeService;

    @Nested
    class Post {
        @Test
        void shouldReturn201WhenSaveAdditionalInformationIsSuccessful() throws Exception {
            AdditionalInformationRequestDto requestDto = createAdditionalInformationRequestDto().build();

            when(curriculumVitaeService.isCurriculumVitaeExists(ADDITIONAL_INFORMATION_CV_UUID)).thenReturn(true);

            mockMvc.perform(post(CV_ADDITIONAL_INFORMATION_URL_TEMPLATE)
                            .contentType(APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(requestDto)))
                    .andDo(print())
                    .andExpect(status().isCreated());
        }

        @Test
        void shouldReturn201AndCorrectAdditionalInformationJsonWhenSaveAdditionalInformationIsSuccessful() throws Exception {
            AdditionalInformationRequestDto requestDto = createAdditionalInformationRequestDto().build();
            AdditionalInformationResponseDto responseDto = createAdditionalInformationResponseDto().build();

            when(curriculumVitaeService.isCurriculumVitaeExists(ADDITIONAL_INFORMATION_CV_UUID)).thenReturn(true);
            when(additionalInformationService.save(requestDto, ADDITIONAL_INFORMATION_CV_UUID)).thenReturn(responseDto);

            MvcResult mvcResult = mockMvc.perform(post(CV_ADDITIONAL_INFORMATION_URL_TEMPLATE)
                            .contentType(APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(requestDto)))
                    .andDo(print())
                    .andExpect(status().isCreated())
                    .andReturn();

            String expectedJson = objectMapper.writeValueAsString(responseDto);
            String resultJson = mvcResult.getResponse().getContentAsString();
            assertEquals(expectedJson, resultJson);
        }

        @Test
        void shouldInvokeAdditionalInformationBusinessLogicWhenSaveAdditionalInformationIsSuccessful() throws Exception {
            AdditionalInformationRequestDto requestDto = createAdditionalInformationRequestDto().build();
            AdditionalInformationResponseDto responseDto = createAdditionalInformationResponseDto().build();

            when(curriculumVitaeService.isCurriculumVitaeExists(ADDITIONAL_INFORMATION_CV_UUID)).thenReturn(true);
            when(additionalInformationService.save(requestDto, ADDITIONAL_INFORMATION_CV_UUID)).thenReturn(responseDto);

            mockMvc.perform(post(CV_ADDITIONAL_INFORMATION_URL_TEMPLATE)
                            .contentType(APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(requestDto)))
                    .andDo(print())
                    .andExpect(status().isCreated());

            verify(additionalInformationService, times(1)).save(requestDto, ADDITIONAL_INFORMATION_CV_UUID);
            verify(curriculumVitaeService, times(1)).isCurriculumVitaeExists(ADDITIONAL_INFORMATION_CV_UUID);
        }
    }


    @Nested
    class ValidationPost {

        @ParameterizedTest
        @DisplayName("Validation should return 400 when field additional information is invalid")
        @MethodSource("by.itacademy.profiler.api.controllers.AdditionalInformationApiControllerTest#getInvalidArgumentsForAdditionalInformationValidation")
        void shouldReturn400WhenAdditionalInformationIsInvalid(String additionalInformation, String error) throws Exception {
            AdditionalInformationRequestDto requestDto = createAdditionalInformationRequestDto()
                    .withAdditionalInfo(additionalInformation).build();
            when(curriculumVitaeService.isCurriculumVitaeExists(ADDITIONAL_INFORMATION_CV_UUID)).thenReturn(true);

            mockMvc.perform(post(CV_ADDITIONAL_INFORMATION_URL_TEMPLATE)
                            .contentType(APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(requestDto)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string(containsString(error)));
        }


        @ParameterizedTest
        @DisplayName("Validation should return 400 when field hobby is invalid")
        @MethodSource("by.itacademy.profiler.api.controllers.AdditionalInformationApiControllerTest#getInvalidArgumentsForHobbyValidation")
        void shouldReturn400WhenHobbyIsInvalid(String hobby, String error) throws Exception {
            AdditionalInformationRequestDto requestDto = createAdditionalInformationRequestDto()
                    .withHobby(hobby).build();
            when(curriculumVitaeService.isCurriculumVitaeExists(ADDITIONAL_INFORMATION_CV_UUID)).thenReturn(true);

            mockMvc.perform(post(CV_ADDITIONAL_INFORMATION_URL_TEMPLATE)
                            .contentType(APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(requestDto)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string(containsString(error)));
        }

        @Test
        @DisplayName("Validation should return 400 when list of awards has size more than 3")
        void shouldReturn400WhenListOfAwardsHasSizeMoreThan3() throws Exception {
            List<AwardDto> invalidListOfAwardDto = List.of(createAwardDto().build(), createAwardDto().build(), createAwardDto().build(), createAwardDto().build());
            AdditionalInformationRequestDto requestDto = createAdditionalInformationRequestDto()
                    .withAwards(invalidListOfAwardDto).build();

            when(curriculumVitaeService.isCurriculumVitaeExists(ADDITIONAL_INFORMATION_CV_UUID)).thenReturn(true);

            mockMvc.perform(post(CV_ADDITIONAL_INFORMATION_URL_TEMPLATE)
                            .contentType(APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(requestDto)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string(containsString("Amount of awards should not be more than 3")));
        }

        @ParameterizedTest
        @DisplayName("Validation should return 400 when field title is invalid")
        @MethodSource("by.itacademy.profiler.api.controllers.AdditionalInformationApiControllerTest#getInvalidArgumentsForTitleValidation")
        void shouldReturn400WhenTitleIsInvalid(String title, String error) throws Exception {
            AwardDto awardDto = createAwardDto()
                    .withTitle(title).build();

            AdditionalInformationRequestDto requestDto = createAdditionalInformationRequestDto()
                    .withAwards(List.of(awardDto)).build();

            when(curriculumVitaeService.isCurriculumVitaeExists(ADDITIONAL_INFORMATION_CV_UUID)).thenReturn(true);

            mockMvc.perform(post(CV_ADDITIONAL_INFORMATION_URL_TEMPLATE)
                            .contentType(APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(requestDto)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string(containsString(error)));
        }

        @ParameterizedTest
        @DisplayName("Validation should return 400 when field issuer is invalid")
        @MethodSource("by.itacademy.profiler.api.controllers.AdditionalInformationApiControllerTest#getInvalidArgumentsForIssuer")
        void shouldReturn400WhenIssuerIsInvalid(String issuer, String error) throws Exception {
            AwardDto awardDto = createAwardDto()
                    .withIssuer(issuer).build();

            AdditionalInformationRequestDto requestDto = createAdditionalInformationRequestDto()
                    .withAwards(List.of(awardDto)).build();

            when(curriculumVitaeService.isCurriculumVitaeExists(ADDITIONAL_INFORMATION_CV_UUID)).thenReturn(true);

            mockMvc.perform(post(CV_ADDITIONAL_INFORMATION_URL_TEMPLATE)
                            .contentType(APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(requestDto)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string(containsString(error)));
        }

        @ParameterizedTest
        @DisplayName("Validation should return 400 when field description is invalid")
        @MethodSource("by.itacademy.profiler.api.controllers.AdditionalInformationApiControllerTest#getInvalidArgumentsForDescription")
        void shouldReturn400WhenDescriptionIsInvalid(String description, String error) throws Exception {
            AwardDto awardDto = createAwardDto()
                    .withDescription(description).build();

            AdditionalInformationRequestDto requestDto = createAdditionalInformationRequestDto()
                    .withAwards(List.of(awardDto)).build();

            when(curriculumVitaeService.isCurriculumVitaeExists(ADDITIONAL_INFORMATION_CV_UUID)).thenReturn(true);

            mockMvc.perform(post(CV_ADDITIONAL_INFORMATION_URL_TEMPLATE)
                            .contentType(APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(requestDto)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string(containsString(error)));
        }

        @Test
        @DisplayName("Validation should return 400 when field date is invalid")
        void shouldReturn400WhenDateIsInvalid() throws Exception {
            AwardDto awardDto = createAwardDto()
                    .withDate(YearMonth.of(1969, 1)).build();

            AdditionalInformationRequestDto requestDto = createAdditionalInformationRequestDto()
                    .withAwards(List.of(awardDto)).build();

            when(curriculumVitaeService.isCurriculumVitaeExists(ADDITIONAL_INFORMATION_CV_UUID)).thenReturn(true);

            mockMvc.perform(post(CV_ADDITIONAL_INFORMATION_URL_TEMPLATE)
                            .contentType(APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(requestDto)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string(containsString("Date should not be earlier than 1970-01")));
        }

        @Test
        @DisplayName("Validation should return 400 when field date is in the future")
        void shouldReturn400WhenDataInFuture() throws Exception {
            AwardDto awardDto = createAwardDto()
                    .withDate(YearMonth.of(2100, 1)).build();

            AdditionalInformationRequestDto requestDto = createAdditionalInformationRequestDto()
                    .withAwards(List.of(awardDto)).build();

            when(curriculumVitaeService.isCurriculumVitaeExists(ADDITIONAL_INFORMATION_CV_UUID)).thenReturn(true);

            mockMvc.perform(post(CV_ADDITIONAL_INFORMATION_URL_TEMPLATE)
                            .contentType(APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(requestDto)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string(containsString("Date is in the future")));
        }

        @ParameterizedTest
        @ValueSource(strings = {"abc", "ABC", "abc123", "ĀĚěĽ", "abc,123", "abc.123", "abc-123",
                "abc/123", "abc:123", "abc;123", "abc 123"})
        @DisplayName("Should return 201 when title is valid")
        void shouldReturn201WhenAwardTitleIsValid(String title) throws Exception {
            AwardDto awardDto = createAwardDto()
                    .withTitle(title)
                    .build();

            AdditionalInformationRequestDto requestDto = createAdditionalInformationRequestDto()
                    .withAwards(List.of(awardDto)).build();

            when(curriculumVitaeService.isCurriculumVitaeExists(ADDITIONAL_INFORMATION_CV_UUID)).thenReturn(true);

            mockMvc.perform(post(CV_ADDITIONAL_INFORMATION_URL_TEMPLATE)
                            .contentType(APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(requestDto)))
                    .andDo(print())
                    .andExpect(status().isCreated());
        }


        @ParameterizedTest
        @MethodSource("validStringsForSevenPageTextFieldsRegexp")
        @DisplayName("Should return 201 when issuer is valid")
        void shouldReturn201WhenAwardIssuerIsValid(String issuer) throws Exception {
            AwardDto awardDto = createAwardDto()
                    .withIssuer(issuer)
                    .build();

            AdditionalInformationRequestDto requestDto = createAdditionalInformationRequestDto()
                    .withAwards(List.of(awardDto)).build();

            when(curriculumVitaeService.isCurriculumVitaeExists(ADDITIONAL_INFORMATION_CV_UUID)).thenReturn(true);

            mockMvc.perform(post(CV_ADDITIONAL_INFORMATION_URL_TEMPLATE)
                            .contentType(APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(requestDto)))
                    .andDo(print())
                    .andExpect(status().isCreated());
        }

        @ParameterizedTest
        @MethodSource("validStringsForSevenPageTextFieldsRegexp")
        @DisplayName("Should return 201 when description is valid")
        void shouldReturn201WhenAwardDescriptionIsValid(String description) throws Exception {
            AwardDto awardDto = createAwardDto()
                    .withDescription(description)
                    .build();

            AdditionalInformationRequestDto requestDto = createAdditionalInformationRequestDto()
                    .withAwards(List.of(awardDto)).build();

            when(curriculumVitaeService.isCurriculumVitaeExists(ADDITIONAL_INFORMATION_CV_UUID)).thenReturn(true);

            mockMvc.perform(post(CV_ADDITIONAL_INFORMATION_URL_TEMPLATE)
                            .contentType(APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(requestDto)))
                    .andDo(print())
                    .andExpect(status().isCreated());
        }

        @ParameterizedTest
        @MethodSource("validStringsForSevenPageTextFieldsRegexp")
        @DisplayName("Should return 201 when additional information is valid")
        void shouldReturn201WhenAdditionalInformationIsValid(String addInfo) throws Exception {
            AdditionalInformationRequestDto requestDto = createAdditionalInformationRequestDto()
                    .withAdditionalInfo(addInfo)
                    .build();

            when(curriculumVitaeService.isCurriculumVitaeExists(ADDITIONAL_INFORMATION_CV_UUID)).thenReturn(true);

            mockMvc.perform(post(CV_ADDITIONAL_INFORMATION_URL_TEMPLATE)
                            .contentType(APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(requestDto)))
                    .andDo(print())
                    .andExpect(status().isCreated());
        }


        @ParameterizedTest
        @MethodSource("validStringsForSevenPageTextFieldsRegexp")
        @DisplayName("Should return 201 when hobby is valid")
        void shouldReturn201WhenHobbyIsValid(String hobby) throws Exception {
            AdditionalInformationRequestDto requestDto = createAdditionalInformationRequestDto()
                    .withAdditionalInfo(hobby)
                    .build();

            when(curriculumVitaeService.isCurriculumVitaeExists(ADDITIONAL_INFORMATION_CV_UUID)).thenReturn(true);

            mockMvc.perform(post(CV_ADDITIONAL_INFORMATION_URL_TEMPLATE)
                            .contentType(APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(requestDto)))
                    .andDo(print())
                    .andExpect(status().isCreated());
        }

        static Stream<String> validStringsForSevenPageTextFieldsRegexp() {
            return Stream.of("abc", "ABC", "abc0123456789", "abc,123", "abc.123", "abc-123",
                    "abc/123", "abc:123", "abc;123", "abc 123");
        }
    }

    @Nested
    class Get {

        @Test
        void shouldReturn200AndContentTypeJsonWhenGetAdditionalInformationIsSuccessful() throws Exception {
            AdditionalInformationResponseDto responseDto = createAdditionalInformationResponseDto().build();
            
            when(additionalInformationService.getAdditionalInformationByCvUuid(ADDITIONAL_INFORMATION_CV_UUID))
                    .thenReturn(responseDto);
            when(curriculumVitaeService.isCurriculumVitaeExists(ADDITIONAL_INFORMATION_CV_UUID)).thenReturn(true);
            
            mockMvc.perform(get(CV_ADDITIONAL_INFORMATION_URL_TEMPLATE)
                            .contentType(APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(APPLICATION_JSON));
        }

        @Test
        void shouldReturn200AndInvokeBusinessLogicWhenGetAdditionalInformationIsSuccessful() throws Exception {
            when(additionalInformationService.getAdditionalInformationByCvUuid(ADDITIONAL_INFORMATION_CV_UUID))
                    .thenReturn(any());
            when(curriculumVitaeService.isCurriculumVitaeExists(ADDITIONAL_INFORMATION_CV_UUID)).thenReturn(true);

            mockMvc.perform(get(CV_ADDITIONAL_INFORMATION_URL_TEMPLATE)
                            .contentType(APPLICATION_JSON))
                    .andExpect(status().isOk());

            verify(additionalInformationService, times(1)).getAdditionalInformationByCvUuid(ADDITIONAL_INFORMATION_CV_UUID);
            verify(curriculumVitaeService, times(1)).isCurriculumVitaeExists(ADDITIONAL_INFORMATION_CV_UUID);
        }

        @Test
        void shouldReturn200AndCorrectJsonWhenGetAdditionalInformationIsSuccessful() throws Exception {
            AdditionalInformationResponseDto responseDto = createAdditionalInformationResponseDto().build();
            String expectedJson = objectMapper.writeValueAsString(responseDto);

            when(additionalInformationService.getAdditionalInformationByCvUuid(ADDITIONAL_INFORMATION_CV_UUID))
                    .thenReturn(responseDto);
            when(curriculumVitaeService.isCurriculumVitaeExists(ADDITIONAL_INFORMATION_CV_UUID)).thenReturn(true);

            MvcResult mvcResult = mockMvc.perform(get(CV_ADDITIONAL_INFORMATION_URL_TEMPLATE)
                            .contentType(APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andReturn();

            String actualResult = mvcResult.getResponse().getContentAsString();

            assertEquals(expectedJson, actualResult);
        }

        @Test
        void shouldReturn404WhenGetAdditionalInformationWithInvalidUuid() throws Exception {
            String expectedContent = CV_NOT_FOUND_ERROR;

            when(curriculumVitaeService.isCurriculumVitaeExists(ADDITIONAL_INFORMATION_CV_UUID)).thenReturn(false);

            mockMvc.perform(get(CV_ADDITIONAL_INFORMATION_URL_TEMPLATE)
                            .contentType(APPLICATION_JSON))
                    .andExpect(status().isNotFound())
                    .andExpect(content().string(containsString(expectedContent)));
        }

        @Test
        void shouldReturn204WhenGetAdditionalInformationWhichIsNotExist() throws Exception {
            String expectedContent = ADDITIONAL_INFORMATION_NO_CONTENT_ERROR;

            when(additionalInformationService.getAdditionalInformationByCvUuid(ADDITIONAL_INFORMATION_CV_UUID))
                    .thenThrow(new AdditionalInformationNotFoundException(expectedContent));
            when(curriculumVitaeService.isCurriculumVitaeExists(ADDITIONAL_INFORMATION_CV_UUID)).thenReturn(true);

            mockMvc.perform(get(CV_ADDITIONAL_INFORMATION_URL_TEMPLATE)
                            .contentType(APPLICATION_JSON))
                    .andExpect(status().isNoContent())
                    .andExpect(content().string(containsString(expectedContent)));
        }
    }

    static Stream<Arguments> getInvalidArgumentsForAdditionalInformationValidation() {
        return Stream.of(Arguments.of("", REGEXP_VALIDATE_ADDITIONAL_INFORMATION_ERROR),
                Arguments.of(" ", REGEXP_VALIDATE_ADDITIONAL_INFORMATION_ERROR),
                Arguments.of("Русский текст", REGEXP_VALIDATE_ADDITIONAL_INFORMATION_ERROR),
                Arguments.of("symbol $", REGEXP_VALIDATE_ADDITIONAL_INFORMATION_ERROR),
                Arguments.of("symbol #", REGEXP_VALIDATE_ADDITIONAL_INFORMATION_ERROR),
                Arguments.of("symbol @", REGEXP_VALIDATE_ADDITIONAL_INFORMATION_ERROR),
                Arguments.of("symbol &", REGEXP_VALIDATE_ADDITIONAL_INFORMATION_ERROR),
                Arguments.of("symbol ^", REGEXP_VALIDATE_ADDITIONAL_INFORMATION_ERROR),
                Arguments.of("This string has a length of more than 150 aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", MAXLENGTH_ADDITIONAL_INFORMATION_ERROR),
                Arguments.of(null, FIELD_ADDITIONAL_INFORMATION_NOT_NULL_ERROR));
    }


    static Stream<Arguments> getInvalidArgumentsForHobbyValidation() {
        return Stream.of(Arguments.of("Русский текст", REGEXP_VALIDATE_HOBBY_ERROR),
                Arguments.of("symbol $", REGEXP_VALIDATE_HOBBY_ERROR),
                Arguments.of("symbol #", REGEXP_VALIDATE_HOBBY_ERROR),
                Arguments.of("symbol @", REGEXP_VALIDATE_HOBBY_ERROR),
                Arguments.of("symbol &", REGEXP_VALIDATE_HOBBY_ERROR),
                Arguments.of("symbol ^", REGEXP_VALIDATE_HOBBY_ERROR),
                Arguments.of("This string has a length of more than 100 aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", MAXLENGTH_VALIDATE_HOBBY_ERROR));
    }

    static Stream<Arguments> getInvalidArgumentsForTitleValidation() {
        return Stream.of(Arguments.of("", REGEXP_VALIDATE_TITLE_ERROR),
                Arguments.of(" ", REGEXP_VALIDATE_TITLE_ERROR),
                Arguments.of("Русский текст", REGEXP_VALIDATE_TITLE_ERROR),
                Arguments.of("symbol $", REGEXP_VALIDATE_TITLE_ERROR),
                Arguments.of("symbol #", REGEXP_VALIDATE_TITLE_ERROR),
                Arguments.of("symbol @", REGEXP_VALIDATE_TITLE_ERROR),
                Arguments.of("symbol &", REGEXP_VALIDATE_TITLE_ERROR),
                Arguments.of("symbol ^", REGEXP_VALIDATE_TITLE_ERROR),
                Arguments.of("This string has a length of more than 30", MAXLENGTH_VALIDATE_TITLE_ERROR),
                Arguments.of(null, FIELD_TITLE_NOT_NULL_ERROR));
    }

    static Stream<Arguments> getInvalidArgumentsForIssuer() {
        return Stream.of(Arguments.of("Русский текст", REGEXP_VALIDATE_ISSUER_ERROR),
                Arguments.of("symbol $", REGEXP_VALIDATE_ISSUER_ERROR),
                Arguments.of("symbol #", REGEXP_VALIDATE_ISSUER_ERROR),
                Arguments.of("symbol @", REGEXP_VALIDATE_ISSUER_ERROR),
                Arguments.of("symbol &", REGEXP_VALIDATE_ISSUER_ERROR),
                Arguments.of("symbol ^", REGEXP_VALIDATE_ISSUER_ERROR),
                Arguments.of("This string has a length of more than 25", MAXLENGTH_VALIDATE_ISSUER_ERROR));
    }

    static Stream<Arguments> getInvalidArgumentsForDescription() {
        return Stream.of(Arguments.of("Русский текст", REGEXP_VALIDATE_DESCRIPTION_ERROR),
                Arguments.of("symbol $", REGEXP_VALIDATE_DESCRIPTION_ERROR),
                Arguments.of("symbol #", REGEXP_VALIDATE_DESCRIPTION_ERROR),
                Arguments.of("symbol @", REGEXP_VALIDATE_DESCRIPTION_ERROR),
                Arguments.of("symbol &", REGEXP_VALIDATE_DESCRIPTION_ERROR),
                Arguments.of("symbol ^", REGEXP_VALIDATE_DESCRIPTION_ERROR),
                Arguments.of("This string has a length of more than 70 aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", MAXLENGTH_VALIDATE_DESCRIPTION_ERROR));
    }


}
