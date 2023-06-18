package by.itacademy.profiler.api.controllers;

import by.itacademy.profiler.usecasses.CurriculumVitaeService;
import by.itacademy.profiler.usecasses.EducationService;
import by.itacademy.profiler.usecasses.dto.CourseRequestDto;
import by.itacademy.profiler.usecasses.dto.EducationRequestDto;
import by.itacademy.profiler.usecasses.dto.EducationResponseDto;
import by.itacademy.profiler.usecasses.dto.MainEducationRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.Year;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static by.itacademy.profiler.util.CompetenceTestData.INVALID_CV_UUID;
import static by.itacademy.profiler.util.CourseTestData.createCourseRequestDto;
import static by.itacademy.profiler.util.EducationTestData.CV_EDUCATIONS_URL_TEMPLATE;
import static by.itacademy.profiler.util.EducationTestData.CV_UUID_FOR_EDUCATIONS;
import static by.itacademy.profiler.util.EducationTestData.createEducationRequestDto;
import static by.itacademy.profiler.util.EducationTestData.createEducationResponseDto;
import static by.itacademy.profiler.util.MainEducationTestData.createMainEducationRequestDto;
import static by.itacademy.profiler.util.StringTestData.createLowerCaseASCIIString;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = EducationApiController.class)
@AutoConfigureMockMvc(addFilters = false)
class EducationApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CurriculumVitaeService curriculumVitaeService;

    @MockBean
    private EducationService educationService;

    private static final long UPPER_LIMIT_INCREMENT_FOR_COURSE_PERIOD_TO = 2L;
    private static final long UPPER_LIMIT_INCREMENT_FOR_MAIN_EDUCATION_PERIOD_TO = 6L;
    private static final int BOTTOM_LIMIT_YEAR_FOR_PERIOD_FROM = 1970;
    private static final String BOTTOM_LIMIT_YEAR_MONTH_FOR_PERIOD_FROM = "1970-01";
    private static final int TWO_WHEN_SIZE_IS_ONE = 2;
    private static final int GREATER_THAN_ALLOWED_LIST_SIZE = 10;
    private static final int GREATER_THAN_ALLOWED_FIELD_LENGTH = 260;
    private static final String PRESENT_TIME_PERIOD_TO_VALIDATION_MESSAGE = "If field `presentTime` is true, then field `periodTo` should be null, otherwise should not be";
    private static final String PRESENT_TIME_NULL_MESSAGE = "Must be specified";
    private static final String PERIOD_TO_AFTER_PERIOD_FROM_VALIDATION_MESSAGE = "Field `periodTo` should be later than `periodFrom`";
    private static final String PERIOD_TO_AFTER_OR_EQUAL_TO_PERIOD_FROM_VALIDATION_MESSAGE = "Field `periodTo` should be later than or equal to `periodFrom`";
    private static final String DATE_IS_IN_THE_FUTURE_MESSAGE = "Date is in the future";
    private static final String SEQUENCE_NUMBER_MUST_NOT_BE_NULL_MESSAGE = "Sequence number must not be null";
    private static final String INVALID_SEQUENCE_NUMBER_MESSAGE = "Invalid sequence number: must be unique and fit list size";
    private static final String DATE_IS_BEFORE_BOTTOM_LIMIT_MESSAGE_TEMPLATE = "Date should not be earlier than ";
    private static final String DATE_IS_AFTER_UPPER_LIMIT_MESSAGE_TEMPLATE = "Date should not be later than current year + ";

    @Test
    void shouldReturn201WhenSaveEducationInfoSuccessfully() throws Exception {
        EducationRequestDto request = createEducationRequestDto().build();

        when(educationService.save(any(EducationRequestDto.class), anyString())).thenReturn(any());
        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID_FOR_EDUCATIONS)).thenReturn(true);

        mockMvc.perform(post(CV_EDUCATIONS_URL_TEMPLATE, CV_UUID_FOR_EDUCATIONS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void shouldReturnExpectedResponseJsonWhenSaveEducationInfoSuccessfully() throws Exception {
        EducationResponseDto response = createEducationResponseDto().build();
        EducationRequestDto request = createEducationRequestDto().build();

        when(educationService.save(any(EducationRequestDto.class), anyString())).thenReturn(response);
        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID_FOR_EDUCATIONS)).thenReturn(true);

        mockMvc.perform(post(CV_EDUCATIONS_URL_TEMPLATE, CV_UUID_FOR_EDUCATIONS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string(objectMapper.writeValueAsString(response)));
    }

    @Test
    void shouldInvokeBusinessLogicWhenSaveEducationInfoSuccessfully() throws Exception {
        EducationRequestDto request = createEducationRequestDto().build();

        when(educationService.save(any(EducationRequestDto.class), anyString())).thenReturn(any());
        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID_FOR_EDUCATIONS)).thenReturn(true);

        mockMvc.perform(post(CV_EDUCATIONS_URL_TEMPLATE, CV_UUID_FOR_EDUCATIONS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated());

        verify(educationService, times(1)).save(any(EducationRequestDto.class), anyString());
    }

    @Test
    void shouldReturn404WhenSaveEducationInfoWithCvUuidNotFound() throws Exception {
        EducationRequestDto request = createEducationRequestDto().build();

        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID_FOR_EDUCATIONS)).thenReturn(false);

        mockMvc.perform(post(CV_EDUCATIONS_URL_TEMPLATE, CV_UUID_FOR_EDUCATIONS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(educationService, times(0)).save(any(EducationRequestDto.class), anyString());
    }

    @ParameterizedTest
    @ValueSource(ints = { 1, 2 })
    void shouldReturn201WhenSaveEducationInfoWithMainEducationsValidCountAndSequenceNumbers(Integer sequenceNumberLimit)
            throws Exception {
        List<MainEducationRequestDto> mainEducations = IntStream.rangeClosed(1, sequenceNumberLimit)
                .mapToObj(n -> createMainEducationRequestDto().withSequenceNumber(n).build())
                .toList();
        EducationRequestDto request = createEducationRequestDto()
                .withMainEducations(mainEducations)
                .build();

        when(educationService.save(any(EducationRequestDto.class), anyString())).thenReturn(any());
        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID_FOR_EDUCATIONS)).thenReturn(true);

        mockMvc.perform(post(CV_EDUCATIONS_URL_TEMPLATE, CV_UUID_FOR_EDUCATIONS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void shouldReturn400WhenSaveEducationInfoWithTooManyMainEducations() throws Exception {
        List<MainEducationRequestDto> mainEducations = IntStream.rangeClosed(1, GREATER_THAN_ALLOWED_LIST_SIZE)
                .mapToObj(n -> createMainEducationRequestDto().withSequenceNumber(n).build())
                .toList();
        EducationRequestDto request = createEducationRequestDto()
                .withMainEducations(mainEducations)
                .build();

        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID_FOR_EDUCATIONS)).thenReturn(true);

        String expectedContent = "Amount of main educations should not be more than 2";
        mockMvc.perform(post(CV_EDUCATIONS_URL_TEMPLATE, CV_UUID_FOR_EDUCATIONS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(expectedContent)));
    }

    @Test
    void shouldReturn201WhenSaveEducationInfoWithEmptyMainEducationsAndValidCourses() throws Exception {
        List<MainEducationRequestDto> mainEducations = Collections.emptyList();
        EducationRequestDto request = createEducationRequestDto()
                .withMainEducations(mainEducations)
                .build();

        when(educationService.save(any(EducationRequestDto.class), anyString())).thenReturn(any());
        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID_FOR_EDUCATIONS)).thenReturn(true);

        mockMvc.perform(post(CV_EDUCATIONS_URL_TEMPLATE, CV_UUID_FOR_EDUCATIONS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @ParameterizedTest
    @ValueSource(ints = { -1, 0 })
    void shouldReturn400WhenSaveEducationInfoWithMainEducationsSequenceNumbersLessThanOne(Integer sequenceNumber)
            throws Exception {
        List<MainEducationRequestDto> mainEducations = List
                .of(createMainEducationRequestDto().withSequenceNumber(sequenceNumber).build());
        EducationRequestDto request = createEducationRequestDto()
                .withMainEducations(mainEducations)
                .build();

        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID_FOR_EDUCATIONS)).thenReturn(true);

        String expectedContent = "Sequence number must not be less than 1";
        mockMvc.perform(post(CV_EDUCATIONS_URL_TEMPLATE, CV_UUID_FOR_EDUCATIONS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(expectedContent)));
    }

    @ParameterizedTest
    @ValueSource(ints = { 10, 5 })
    void shouldReturn400WhenSaveEducationInfoWithMainEducationsSequenceNumbersGreaterThanTwo(Integer sequenceNumber)
            throws Exception {
        List<MainEducationRequestDto> mainEducations = List
                .of(createMainEducationRequestDto().withSequenceNumber(sequenceNumber).build());
        EducationRequestDto request = createEducationRequestDto()
                .withMainEducations(mainEducations)
                .build();

        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID_FOR_EDUCATIONS)).thenReturn(true);

        String expectedContent = "Sequence number must not be more than 2";
        mockMvc.perform(post(CV_EDUCATIONS_URL_TEMPLATE, CV_UUID_FOR_EDUCATIONS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(expectedContent)));
    }

    @Test
    void shouldReturn400WhenSaveEducationInfoWithNotUniqueMainEducationSequenceNumbers() throws Exception {
        List<MainEducationRequestDto> mainEducations = List
                .of(createMainEducationRequestDto().withSequenceNumber(1).build(),
                        createMainEducationRequestDto().withSequenceNumber(1).build());
        EducationRequestDto request = createEducationRequestDto()
                .withMainEducations(mainEducations)
                .build();

        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID_FOR_EDUCATIONS)).thenReturn(true);

        String expectedContent = INVALID_SEQUENCE_NUMBER_MESSAGE;
        mockMvc.perform(post(CV_EDUCATIONS_URL_TEMPLATE, CV_UUID_FOR_EDUCATIONS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(expectedContent)));
    }

    @Test
    void shouldReturn400WhenSaveEducationInfoWithNullMainEducationSequenceNumber() throws Exception {
        List<MainEducationRequestDto> mainEducations = List
                .of(createMainEducationRequestDto().withSequenceNumber(null).build());
        EducationRequestDto request = createEducationRequestDto()
                .withMainEducations(mainEducations)
                .build();

        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID_FOR_EDUCATIONS)).thenReturn(true);

        String expectedContent = SEQUENCE_NUMBER_MUST_NOT_BE_NULL_MESSAGE;
        mockMvc.perform(post(CV_EDUCATIONS_URL_TEMPLATE, CV_UUID_FOR_EDUCATIONS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(expectedContent)));
    }

    @Test
    void shouldReturn400WhenSaveEducationInfoWithMainEducationSequenceNumberGreaterThanListSize() throws Exception {
        List<MainEducationRequestDto> mainEducations = List
                .of(createMainEducationRequestDto().withSequenceNumber(TWO_WHEN_SIZE_IS_ONE).build());
        EducationRequestDto request = createEducationRequestDto()
                .withMainEducations(mainEducations)
                .build();

        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID_FOR_EDUCATIONS)).thenReturn(true);

        String expectedContent = INVALID_SEQUENCE_NUMBER_MESSAGE;
        mockMvc.perform(post(CV_EDUCATIONS_URL_TEMPLATE, CV_UUID_FOR_EDUCATIONS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(expectedContent)));
    }

    @Test
    void shouldReturn400WhenSaveEducationInfoWithNotEmptyMainEducationListButHasNull() throws Exception {
        List<MainEducationRequestDto> mainEducations = new ArrayList<>();
        mainEducations.add(null);
        EducationRequestDto request = createEducationRequestDto()
                .withMainEducations(mainEducations)
                .build();

        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID_FOR_EDUCATIONS)).thenReturn(true);

        String expectedContent = "Main education must not be null";
        mockMvc.perform(post(CV_EDUCATIONS_URL_TEMPLATE, CV_UUID_FOR_EDUCATIONS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(expectedContent)));
    }

    @ParameterizedTest
    @ValueSource(ints = { 1, 2, 3, 4, 5 })
    void shouldReturn201WhenSaveEducationInfoWithCoursesValidCountAndSequenceNumbers(Integer sequenceNumberLimit)
            throws Exception {
        List<CourseRequestDto> courses = IntStream.rangeClosed(1, sequenceNumberLimit)
                .mapToObj(n -> createCourseRequestDto().withSequenceNumber(n).build())
                .toList();
        EducationRequestDto request = createEducationRequestDto()
                .withCourses(courses)
                .build();

        when(educationService.save(any(EducationRequestDto.class), anyString())).thenReturn(any());
        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID_FOR_EDUCATIONS)).thenReturn(true);

        mockMvc.perform(post(CV_EDUCATIONS_URL_TEMPLATE, CV_UUID_FOR_EDUCATIONS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @ParameterizedTest
    @ValueSource(ints = { -1, 0 })
    void shouldReturn400WhenSaveEducationInfoWithCoursesSequenceNumbersLessThanOne(Integer sequenceNumber)
            throws Exception {
        List<CourseRequestDto> courses = List.of(createCourseRequestDto().withSequenceNumber(sequenceNumber).build());
        EducationRequestDto request = createEducationRequestDto()
                .withCourses(courses)
                .build();

        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID_FOR_EDUCATIONS)).thenReturn(true);

        String expectedContent = "Sequence number must not be less than 1";
        mockMvc.perform(post(CV_EDUCATIONS_URL_TEMPLATE, CV_UUID_FOR_EDUCATIONS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(expectedContent)));
    }

    @ParameterizedTest
    @ValueSource(ints = { 10, 7 })
    void shouldReturn400WhenSaveEducationInfoWithCoursesSequenceNumbersGreaterThanFive(Integer sequenceNumber)
            throws Exception {
        List<CourseRequestDto> courses = List.of(createCourseRequestDto().withSequenceNumber(sequenceNumber).build());
        EducationRequestDto request = createEducationRequestDto()
                .withCourses(courses)
                .build();

        String expectedContent = "Sequence number must not be more than 5";

        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID_FOR_EDUCATIONS)).thenReturn(true);

        mockMvc.perform(post(CV_EDUCATIONS_URL_TEMPLATE, CV_UUID_FOR_EDUCATIONS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(expectedContent)));
    }

    @Test
    void shouldReturn400WhenSaveEducationInfoWithTooManyCourses() throws Exception {
        List<CourseRequestDto> courses = IntStream.rangeClosed(1, GREATER_THAN_ALLOWED_LIST_SIZE)
                .mapToObj(n -> createCourseRequestDto().withSequenceNumber(n).build())
                .toList();
        EducationRequestDto request = createEducationRequestDto()
                .withCourses(courses)
                .build();

        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID_FOR_EDUCATIONS)).thenReturn(true);

        String expectedContent = "Amount of courses should not be more than 5";
        mockMvc.perform(post(CV_EDUCATIONS_URL_TEMPLATE, CV_UUID_FOR_EDUCATIONS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(expectedContent)));
    }

    @Test
    void shouldReturn400WhenSaveEducationInfoWithNotUniqueCourseSequenceNumbers() throws Exception {
        List<CourseRequestDto> courses = List
                .of(createCourseRequestDto().withSequenceNumber(1).build(),
                        createCourseRequestDto().withSequenceNumber(1).build());
        EducationRequestDto request = createEducationRequestDto()
                .withCourses(courses)
                .build();

        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID_FOR_EDUCATIONS)).thenReturn(true);

        String expectedContent = INVALID_SEQUENCE_NUMBER_MESSAGE;
        mockMvc.perform(post(CV_EDUCATIONS_URL_TEMPLATE, CV_UUID_FOR_EDUCATIONS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(expectedContent)));
    }

    @Test
    void shouldReturn400WhenSaveEducationInfoWithNullCourseSequenceNumber() throws Exception {
        List<CourseRequestDto> courses = List.of(createCourseRequestDto().withSequenceNumber(null).build());
        EducationRequestDto request = createEducationRequestDto()
                .withCourses(courses)
                .build();

        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID_FOR_EDUCATIONS)).thenReturn(true);

        String expectedContent = SEQUENCE_NUMBER_MUST_NOT_BE_NULL_MESSAGE;
        mockMvc.perform(post(CV_EDUCATIONS_URL_TEMPLATE, CV_UUID_FOR_EDUCATIONS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(expectedContent)));
    }

    @Test
    void shouldReturn400WhenSaveEducationInfoWithCourseSequenceNumberGreaterThanListSize() throws Exception {
        List<CourseRequestDto> courses = List
                .of(createCourseRequestDto().withSequenceNumber(TWO_WHEN_SIZE_IS_ONE).build());
        EducationRequestDto request = createEducationRequestDto()
                .withCourses(courses)
                .build();

        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID_FOR_EDUCATIONS)).thenReturn(true);

        String expectedContent = INVALID_SEQUENCE_NUMBER_MESSAGE;
        mockMvc.perform(post(CV_EDUCATIONS_URL_TEMPLATE, CV_UUID_FOR_EDUCATIONS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(expectedContent)));
    }

    @Test
    void shouldReturn400WhenSaveEducationInfoWithNotEmptyCoursesListButHasNull() throws Exception {
        List<CourseRequestDto> courses = new ArrayList<>();
        courses.add(null);
        EducationRequestDto request = createEducationRequestDto()
                .withCourses(courses)
                .build();

        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID_FOR_EDUCATIONS)).thenReturn(true);

        String expectedContent = "Course must not be null";
        mockMvc.perform(post(CV_EDUCATIONS_URL_TEMPLATE, CV_UUID_FOR_EDUCATIONS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(expectedContent)));
    }

    @Test
    void shouldReturn400WhenSaveEducationInfoWithMainEducationNullPeriodFrom() throws Exception {
        List<MainEducationRequestDto> mainEducations = List
                .of(createMainEducationRequestDto().withPeriodFrom(null).build());
        EducationRequestDto request = createEducationRequestDto()
                .withMainEducations(mainEducations)
                .build();

        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID_FOR_EDUCATIONS)).thenReturn(true);

        String expectedContent = "Main education period start must not be null";
        mockMvc.perform(post(CV_EDUCATIONS_URL_TEMPLATE, CV_UUID_FOR_EDUCATIONS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(expectedContent)));
    }

    @Test
    void shouldReturn400WhenSaveEducationInfoWithMainEducationPeriodFromIsBeforeBottomLimit() throws Exception {
        Year beforeBottomLimit = Year.of(BOTTOM_LIMIT_YEAR_FOR_PERIOD_FROM).minusYears(1L);
        List<MainEducationRequestDto> mainEducations = List
                .of(createMainEducationRequestDto().withPeriodFrom(beforeBottomLimit)
                        .withPeriodTo(beforeBottomLimit.plusYears(1L))
                        .build());
        EducationRequestDto request = createEducationRequestDto()
                .withMainEducations(mainEducations)
                .build();
        
        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID_FOR_EDUCATIONS)).thenReturn(true);
        
        String expectedContent = DATE_IS_BEFORE_BOTTOM_LIMIT_MESSAGE_TEMPLATE + BOTTOM_LIMIT_YEAR_FOR_PERIOD_FROM;
        mockMvc.perform(post(CV_EDUCATIONS_URL_TEMPLATE, CV_UUID_FOR_EDUCATIONS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(expectedContent)));
        
    }
    
    @Test
    void shouldReturn400WhenSaveEducationInfoWithMainEducationPeriodFromInTheFuture() throws Exception {
        Year inTheFuture = Year.now().plusYears(1L);
        List<MainEducationRequestDto> mainEducations = List
                .of(createMainEducationRequestDto().withPeriodFrom(inTheFuture).withPeriodTo(inTheFuture.plusYears(1L))
                        .build());
        EducationRequestDto request = createEducationRequestDto()
                .withMainEducations(mainEducations)
                .build();

        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID_FOR_EDUCATIONS)).thenReturn(true);

        String expectedContent = DATE_IS_IN_THE_FUTURE_MESSAGE;
        mockMvc.perform(post(CV_EDUCATIONS_URL_TEMPLATE, CV_UUID_FOR_EDUCATIONS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(expectedContent)));
    }

    @Test
    void shouldReturn400WhenSaveEducationInfoWithMainEducationPeriodToIsBeforePeriodFrom() throws Exception {
        Year currentYear = Year.now();
        Year inTheFuture = currentYear.plusYears(1L);
        List<MainEducationRequestDto> mainEducations = List
                .of(createMainEducationRequestDto().withPeriodFrom(inTheFuture).withPeriodTo(currentYear).build());
        EducationRequestDto request = createEducationRequestDto()
                .withMainEducations(mainEducations)
                .build();

        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID_FOR_EDUCATIONS)).thenReturn(true);

        String expectedContent = PERIOD_TO_AFTER_OR_EQUAL_TO_PERIOD_FROM_VALIDATION_MESSAGE;
        mockMvc.perform(post(CV_EDUCATIONS_URL_TEMPLATE, CV_UUID_FOR_EDUCATIONS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(expectedContent)));
    }

    @Test
    void shouldReturn201WhenSaveEducationInfoWithMainEducationPeriodToIsEqualToPeriodFrom() throws Exception {
        Year currentYear = Year.now();
        List<MainEducationRequestDto> mainEducations = List
                .of(createMainEducationRequestDto().withPeriodFrom(currentYear).withPeriodTo(currentYear).build());
        EducationRequestDto request = createEducationRequestDto()
                .withMainEducations(mainEducations)
                .build();
        
        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID_FOR_EDUCATIONS)).thenReturn(true);
        
        mockMvc.perform(post(CV_EDUCATIONS_URL_TEMPLATE, CV_UUID_FOR_EDUCATIONS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void shouldReturn400WhenSaveEducationInfoWithMainEducationPeriodToIsAfterUpperLimit() throws Exception {
        Year currentYear = Year.now();
        Year afterUpperLimit = currentYear.plusYears(UPPER_LIMIT_INCREMENT_FOR_MAIN_EDUCATION_PERIOD_TO).plusYears(1L);
        List<MainEducationRequestDto> mainEducations = List
                .of(createMainEducationRequestDto().withPeriodFrom(currentYear).withPeriodTo(afterUpperLimit).build());
        EducationRequestDto request = createEducationRequestDto()
                .withMainEducations(mainEducations)
                .build();

        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID_FOR_EDUCATIONS)).thenReturn(true);

        String expectedContent = DATE_IS_AFTER_UPPER_LIMIT_MESSAGE_TEMPLATE + UPPER_LIMIT_INCREMENT_FOR_MAIN_EDUCATION_PERIOD_TO;
        mockMvc.perform(post(CV_EDUCATIONS_URL_TEMPLATE, CV_UUID_FOR_EDUCATIONS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(expectedContent)));
    }

    @Test
    void shouldReturn400WhenSaveEducationInfoWithMainEducationNullPresentTime() throws Exception {
        List<MainEducationRequestDto> mainEducations = List
                .of(createMainEducationRequestDto().withPresentTime(null).build());
        EducationRequestDto request = createEducationRequestDto()
                .withMainEducations(mainEducations)
                .build();

        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID_FOR_EDUCATIONS)).thenReturn(true);

        String expectedContent = PRESENT_TIME_NULL_MESSAGE;
        mockMvc.perform(post(CV_EDUCATIONS_URL_TEMPLATE, CV_UUID_FOR_EDUCATIONS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(expectedContent)));
    }

    @Test
    void shouldReturn400WhenSaveEducationInfoWithMainEducationFalsePresentTimeButNullPeriodTo() throws Exception {
        List<MainEducationRequestDto> mainEducations = List
                .of(createMainEducationRequestDto().withPeriodTo(null).withPresentTime(false).build());
        EducationRequestDto request = createEducationRequestDto()
                .withMainEducations(mainEducations)
                .build();

        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID_FOR_EDUCATIONS)).thenReturn(true);

        String expectedContent = PRESENT_TIME_PERIOD_TO_VALIDATION_MESSAGE;
        mockMvc.perform(post(CV_EDUCATIONS_URL_TEMPLATE, CV_UUID_FOR_EDUCATIONS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(expectedContent)));
    }

    @Test
    void shouldReturn400WhenSaveEducationInfoWithMainEducationTruePresentTimeButNotNullPeriodTo() throws Exception {
        List<MainEducationRequestDto> mainEducations = List
                .of(createMainEducationRequestDto().withPresentTime(true).build());
        EducationRequestDto request = createEducationRequestDto()
                .withMainEducations(mainEducations)
                .build();

        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID_FOR_EDUCATIONS)).thenReturn(true);

        String expectedContent = PRESENT_TIME_PERIOD_TO_VALIDATION_MESSAGE;
        mockMvc.perform(post(CV_EDUCATIONS_URL_TEMPLATE, CV_UUID_FOR_EDUCATIONS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(expectedContent)));
    }

    @Test
    void shouldReturn400WhenSaveEducationInfoWithMainEducationNullInstitution() throws Exception {
        List<MainEducationRequestDto> mainEducations = List
                .of(createMainEducationRequestDto().withInstitution(null).build());
        EducationRequestDto request = createEducationRequestDto()
                .withMainEducations(mainEducations)
                .build();

        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID_FOR_EDUCATIONS)).thenReturn(true);

        String expectedContent = "Institution name must not be null";
        mockMvc.perform(post(CV_EDUCATIONS_URL_TEMPLATE, CV_UUID_FOR_EDUCATIONS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(expectedContent)));
    }

    @Test
    void shouldReturn400WhenSaveEducationInfoWithMainEducationInstitutionNameLengthGreaterThanLimit() throws Exception {
        String institutionName = createLowerCaseASCIIString(GREATER_THAN_ALLOWED_FIELD_LENGTH);
        List<MainEducationRequestDto> mainEducations = List
                .of(createMainEducationRequestDto().withInstitution(institutionName).build());
        EducationRequestDto request = createEducationRequestDto()
                .withMainEducations(mainEducations)
                .build();

        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID_FOR_EDUCATIONS)).thenReturn(true);

        String expectedContent = "Institution name is too long, the max number of symbols is 100";
        mockMvc.perform(post(CV_EDUCATIONS_URL_TEMPLATE, CV_UUID_FOR_EDUCATIONS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(expectedContent)));
    }

    @ParameterizedTest
    @MethodSource("validStringsForInstitutionRegexp")
    void shouldReturn201WhenSaveEducationInfoWithMainEducationInstitutionNameFitRegexp(String institutionName)
            throws Exception {
        List<MainEducationRequestDto> mainEducations = List
                .of(createMainEducationRequestDto().withInstitution(institutionName).build());
        EducationRequestDto request = createEducationRequestDto()
                .withMainEducations(mainEducations)
                .build();

        when(educationService.save(any(EducationRequestDto.class), anyString())).thenReturn(any());
        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID_FOR_EDUCATIONS)).thenReturn(true);

        mockMvc.perform(post(CV_EDUCATIONS_URL_TEMPLATE, CV_UUID_FOR_EDUCATIONS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @ParameterizedTest
    @MethodSource("invalidStringsForInstitutionRegexp")
    void shouldReturn400WhenSaveEducationInfoWithMainEducationInstitutionNameNotFitRegexp(String institutionName)
            throws Exception {
        List<MainEducationRequestDto> mainEducations = List
                .of(createMainEducationRequestDto().withInstitution(institutionName).build());
        EducationRequestDto request = createEducationRequestDto()
                .withMainEducations(mainEducations)
                .build();

        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID_FOR_EDUCATIONS)).thenReturn(true);

        String expectedContent = "Invalid institution name";
        mockMvc.perform(post(CV_EDUCATIONS_URL_TEMPLATE, CV_UUID_FOR_EDUCATIONS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(expectedContent)));
    }

    @Test
    void shouldReturn400WhenSaveEducationInfoWithMainEducationFacultyNameLengthGreaterThanLimit() throws Exception {
        String facultyName = createLowerCaseASCIIString(GREATER_THAN_ALLOWED_FIELD_LENGTH);
        List<MainEducationRequestDto> mainEducations = List
                .of(createMainEducationRequestDto().withFaculty(facultyName).build());
        EducationRequestDto request = createEducationRequestDto()
                .withMainEducations(mainEducations)
                .build();

        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID_FOR_EDUCATIONS)).thenReturn(true);

        String expectedContent = "Faculty name is too long, the max number of symbols is 40";
        mockMvc.perform(post(CV_EDUCATIONS_URL_TEMPLATE, CV_UUID_FOR_EDUCATIONS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(expectedContent)));
    }

    @ParameterizedTest
    @MethodSource("validStringsForSixthPageTextFieldsRegexp")
    void shouldReturn201WhenSaveEducationInfoWithMainEducationFacultyNameFitRegexp(String facultyName)
            throws Exception {
        List<MainEducationRequestDto> mainEducations = List
                .of(createMainEducationRequestDto().withFaculty(facultyName).build());
        EducationRequestDto request = createEducationRequestDto()
                .withMainEducations(mainEducations)
                .build();

        when(educationService.save(any(EducationRequestDto.class), anyString())).thenReturn(any());
        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID_FOR_EDUCATIONS)).thenReturn(true);

        mockMvc.perform(post(CV_EDUCATIONS_URL_TEMPLATE, CV_UUID_FOR_EDUCATIONS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @ParameterizedTest
    @MethodSource("invalidStringsForSixthPageTextFieldsRegexp")
    void shouldReturn400WhenSaveEducationInfoWithMainEducationFacultyNameNotFitRegexp(String facultyName)
            throws Exception {
        List<MainEducationRequestDto> mainEducations = List
                .of(createMainEducationRequestDto().withFaculty(facultyName).build());
        EducationRequestDto request = createEducationRequestDto()
                .withMainEducations(mainEducations)
                .build();

        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID_FOR_EDUCATIONS)).thenReturn(true);

        String expectedContent = "Invalid faculty name";
        mockMvc.perform(post(CV_EDUCATIONS_URL_TEMPLATE, CV_UUID_FOR_EDUCATIONS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(expectedContent)));
    }

    @Test
    void shouldReturn400WhenSaveEducationInfoWithMainEducationNullSpecialty() throws Exception {
        List<MainEducationRequestDto> mainEducations = List
                .of(createMainEducationRequestDto().withSpecialty(null).build());
        EducationRequestDto request = createEducationRequestDto()
                .withMainEducations(mainEducations)
                .build();

        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID_FOR_EDUCATIONS)).thenReturn(true);

        String expectedContent = "Specialty name must not be null";
        mockMvc.perform(post(CV_EDUCATIONS_URL_TEMPLATE, CV_UUID_FOR_EDUCATIONS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(expectedContent)));
    }

    @Test
    void shouldReturn400WhenSaveEducationInfoWithMainEducationSpecialtyNameLengthGreaterThanLimit() throws Exception {
        String specialtyName = createLowerCaseASCIIString(GREATER_THAN_ALLOWED_FIELD_LENGTH);
        List<MainEducationRequestDto> mainEducations = List
                .of(createMainEducationRequestDto().withSpecialty(specialtyName).build());
        EducationRequestDto request = createEducationRequestDto()
                .withMainEducations(mainEducations)
                .build();

        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID_FOR_EDUCATIONS)).thenReturn(true);

        String expectedContent = "Specialty name is too long, the max number of symbols is 100";
        mockMvc.perform(post(CV_EDUCATIONS_URL_TEMPLATE, CV_UUID_FOR_EDUCATIONS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(expectedContent)));
    }

    @ParameterizedTest
    @MethodSource("validStringsForSixthPageTextFieldsRegexp")
    void shouldReturn201WhenSaveEducationInfoWithMainEducationSpecialtyNameFitRegexp(String specialtyName)
            throws Exception {
        List<MainEducationRequestDto> mainEducations = List
                .of(createMainEducationRequestDto().withSpecialty(specialtyName).build());
        EducationRequestDto request = createEducationRequestDto()
                .withMainEducations(mainEducations)
                .build();

        when(educationService.save(any(EducationRequestDto.class), anyString())).thenReturn(any());
        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID_FOR_EDUCATIONS)).thenReturn(true);

        mockMvc.perform(post(CV_EDUCATIONS_URL_TEMPLATE, CV_UUID_FOR_EDUCATIONS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @ParameterizedTest
    @MethodSource("invalidStringsForSixthPageTextFieldsRegexp")
    void shouldReturn400WhenSaveEducationInfoWithMainEducationSpecialtyNameNotFitRegexp(String specialtyName)
            throws Exception {
        List<MainEducationRequestDto> mainEducations = List
                .of(createMainEducationRequestDto().withSpecialty(specialtyName).build());
        EducationRequestDto request = createEducationRequestDto()
                .withMainEducations(mainEducations)
                .build();

        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID_FOR_EDUCATIONS)).thenReturn(true);

        String expectedContent = "Invalid specialty name";
        mockMvc.perform(post(CV_EDUCATIONS_URL_TEMPLATE, CV_UUID_FOR_EDUCATIONS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(expectedContent)));
    }

    @Test
    void shouldReturn400WhenSaveEducationInfoWithCourseNullPeriodFrom() throws Exception {
        List<CourseRequestDto> courses = List.of(createCourseRequestDto().withPeriodFrom(null).build());
        EducationRequestDto request = createEducationRequestDto()
                .withCourses(courses)
                .build();

        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID_FOR_EDUCATIONS)).thenReturn(true);

        String expectedContent = "Course period start must not be null";
        mockMvc.perform(post(CV_EDUCATIONS_URL_TEMPLATE, CV_UUID_FOR_EDUCATIONS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(expectedContent)));
    }

    @Test
    void shouldReturn400WhenSaveEducationInfoWithCoursePeriodFromInTheFuture() throws Exception {
        YearMonth inTheFuture = YearMonth.now().plusYears(1L);
        List<CourseRequestDto> courses = List.of(
                createCourseRequestDto().withPeriodFrom(inTheFuture).withPeriodTo(inTheFuture.plusYears(1L)).build());
        EducationRequestDto request = createEducationRequestDto()
                .withCourses(courses)
                .build();

        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID_FOR_EDUCATIONS)).thenReturn(true);

        String expectedContent = DATE_IS_IN_THE_FUTURE_MESSAGE;
        mockMvc.perform(post(CV_EDUCATIONS_URL_TEMPLATE, CV_UUID_FOR_EDUCATIONS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(expectedContent)));
    }

    @Test
    void shouldReturn400WhenSaveEducationInfoWithCoursePeriodFromIsBeforeBottomLimit() throws Exception {
        YearMonth beforeBottomLimit = YearMonth.parse(BOTTOM_LIMIT_YEAR_MONTH_FOR_PERIOD_FROM).minusYears(1L);
        List<CourseRequestDto> courses = List.of(
                createCourseRequestDto().withPeriodFrom(beforeBottomLimit)
                .withPeriodTo(beforeBottomLimit.plusYears(1L)).build());
        EducationRequestDto request = createEducationRequestDto()
                .withCourses(courses)
                .build();
        
        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID_FOR_EDUCATIONS)).thenReturn(true);
        
        String expectedContent = DATE_IS_BEFORE_BOTTOM_LIMIT_MESSAGE_TEMPLATE + BOTTOM_LIMIT_YEAR_MONTH_FOR_PERIOD_FROM;
        mockMvc.perform(post(CV_EDUCATIONS_URL_TEMPLATE, CV_UUID_FOR_EDUCATIONS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(expectedContent)));
    }

    @Test
    void shouldReturn400WhenSaveEducationInfoWithCoursePeriodToIsBeforePeriodFrom() throws Exception {
        YearMonth currentYear = YearMonth.now();
        YearMonth inTheFuture = currentYear.plusYears(1L);
        List<CourseRequestDto> courses = List
                .of(createCourseRequestDto().withPeriodFrom(inTheFuture).withPeriodTo(currentYear).build());
        EducationRequestDto request = createEducationRequestDto()
                .withCourses(courses)
                .build();

        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID_FOR_EDUCATIONS)).thenReturn(true);

        String expectedContent = PERIOD_TO_AFTER_PERIOD_FROM_VALIDATION_MESSAGE;
        mockMvc.perform(post(CV_EDUCATIONS_URL_TEMPLATE, CV_UUID_FOR_EDUCATIONS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(expectedContent)));
    }

    @Test
    void shouldReturn400WhenSaveEducationInfoWithCoursePeriodToIsEqualToPeriodFrom() throws Exception {
        YearMonth currentYear = YearMonth.now();
        List<CourseRequestDto> courses = List
                .of(createCourseRequestDto().withPeriodFrom(currentYear).withPeriodTo(currentYear).build());
        EducationRequestDto request = createEducationRequestDto()
                .withCourses(courses)
                .build();

        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID_FOR_EDUCATIONS)).thenReturn(true);

        String expectedContent = PERIOD_TO_AFTER_PERIOD_FROM_VALIDATION_MESSAGE;
        mockMvc.perform(post(CV_EDUCATIONS_URL_TEMPLATE, CV_UUID_FOR_EDUCATIONS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(expectedContent)));
    }

    @Test
    void shouldReturn400WhenSaveEducationInfoWithCoursePeriodToIsAfterUpperLimit() throws Exception {
        YearMonth currentYear = YearMonth.now();
        YearMonth afterUpperLimit = currentYear.plusYears(UPPER_LIMIT_INCREMENT_FOR_COURSE_PERIOD_TO).plusYears(1L);
        List<CourseRequestDto> courses = List
                .of(createCourseRequestDto().withPeriodFrom(currentYear).withPeriodTo(afterUpperLimit).build());
        EducationRequestDto request = createEducationRequestDto()
                .withCourses(courses)
                .build();

        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID_FOR_EDUCATIONS)).thenReturn(true);

        String expectedContent = DATE_IS_AFTER_UPPER_LIMIT_MESSAGE_TEMPLATE + UPPER_LIMIT_INCREMENT_FOR_COURSE_PERIOD_TO;
        mockMvc.perform(post(CV_EDUCATIONS_URL_TEMPLATE, CV_UUID_FOR_EDUCATIONS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(expectedContent)));
    }

    @Test
    void shouldReturn400WhenSaveEducationInfoWithCourseNullPresentTime() throws Exception {
        List<CourseRequestDto> courses = List.of(createCourseRequestDto().withPresentTime(null).build());
        EducationRequestDto request = createEducationRequestDto()
                .withCourses(courses)
                .build();

        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID_FOR_EDUCATIONS)).thenReturn(true);

        String expectedContent = PRESENT_TIME_NULL_MESSAGE;
        mockMvc.perform(post(CV_EDUCATIONS_URL_TEMPLATE, CV_UUID_FOR_EDUCATIONS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(expectedContent)));
    }

    @Test
    void shouldReturn400WhenSaveEducationInfoWithCourseFalsePresentTimeButNullPeriodTo() throws Exception {
        List<CourseRequestDto> courses = List
                .of(createCourseRequestDto().withPeriodTo(null).withPresentTime(false).build());
        EducationRequestDto request = createEducationRequestDto()
                .withCourses(courses)
                .build();

        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID_FOR_EDUCATIONS)).thenReturn(true);

        String expectedContent = PRESENT_TIME_PERIOD_TO_VALIDATION_MESSAGE;
        mockMvc.perform(post(CV_EDUCATIONS_URL_TEMPLATE, CV_UUID_FOR_EDUCATIONS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(expectedContent)));
    }

    @Test
    void shouldReturn400WhenSaveEducationInfoWithCourseTruePresentTimeButNotNullPeriodTo() throws Exception {
        List<CourseRequestDto> courses = List.of(createCourseRequestDto().withPresentTime(true).build());
        EducationRequestDto request = createEducationRequestDto()
                .withCourses(courses)
                .build();

        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID_FOR_EDUCATIONS)).thenReturn(true);

        String expectedContent = PRESENT_TIME_PERIOD_TO_VALIDATION_MESSAGE;
        mockMvc.perform(post(CV_EDUCATIONS_URL_TEMPLATE, CV_UUID_FOR_EDUCATIONS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(expectedContent)));
    }

    @Test
    void shouldReturn400WhenSaveEducationInfoWithCourseNullSchool() throws Exception {
        List<CourseRequestDto> courses = List.of(createCourseRequestDto().withSchool(null).build());
        EducationRequestDto request = createEducationRequestDto()
                .withCourses(courses)
                .build();

        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID_FOR_EDUCATIONS)).thenReturn(true);

        String expectedContent = "School name must not be null";
        mockMvc.perform(post(CV_EDUCATIONS_URL_TEMPLATE, CV_UUID_FOR_EDUCATIONS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(expectedContent)));
    }

    @Test
    void shouldReturn400WhenSaveEducationInfoWithCourseSchoolNameLengthGreaterThanLimit() throws Exception {
        String schoolName = createLowerCaseASCIIString(GREATER_THAN_ALLOWED_FIELD_LENGTH);
        List<CourseRequestDto> courses = List.of(createCourseRequestDto().withSchool(schoolName).build());
        EducationRequestDto request = createEducationRequestDto()
                .withCourses(courses)
                .build();

        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID_FOR_EDUCATIONS)).thenReturn(true);

        String expectedContent = "School name is too long, the max number of symbols is 40";
        mockMvc.perform(post(CV_EDUCATIONS_URL_TEMPLATE, CV_UUID_FOR_EDUCATIONS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(expectedContent)));
    }

    @ParameterizedTest
    @MethodSource("validStringsForSixthPageTextFieldsRegexp")
    void shouldReturn201WhenSaveEducationInfoWithCourseSchoolNameFitRegexp(String schoolName)
            throws Exception {
        List<CourseRequestDto> courses = List.of(createCourseRequestDto().withSchool(schoolName).build());
        EducationRequestDto request = createEducationRequestDto()
                .withCourses(courses)
                .build();

        when(educationService.save(any(EducationRequestDto.class), anyString())).thenReturn(any());
        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID_FOR_EDUCATIONS)).thenReturn(true);

        mockMvc.perform(post(CV_EDUCATIONS_URL_TEMPLATE, CV_UUID_FOR_EDUCATIONS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @ParameterizedTest
    @MethodSource("invalidStringsForSixthPageTextFieldsRegexp")
    void shouldReturn400WhenSaveEducationInfoWithCourseSchoolNameNotFitRegexp(String schoolName)
            throws Exception {
        List<CourseRequestDto> courses = List.of(createCourseRequestDto().withSchool(schoolName).build());
        EducationRequestDto request = createEducationRequestDto()
                .withCourses(courses)
                .build();

        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID_FOR_EDUCATIONS)).thenReturn(true);

        String expectedContent = "Invalid school name";
        mockMvc.perform(post(CV_EDUCATIONS_URL_TEMPLATE, CV_UUID_FOR_EDUCATIONS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(expectedContent)));
    }

    @Test
    void shouldReturn400WhenSaveEducationInfoWithCourseNullCourseName() throws Exception {
        List<CourseRequestDto> courses = List.of(createCourseRequestDto().withCourseName(null).build());
        EducationRequestDto request = createEducationRequestDto()
                .withCourses(courses)
                .build();

        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID_FOR_EDUCATIONS)).thenReturn(true);

        String expectedContent = "Course name must not be null";
        mockMvc.perform(post(CV_EDUCATIONS_URL_TEMPLATE, CV_UUID_FOR_EDUCATIONS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(expectedContent)));
    }

    @Test
    void shouldReturn400WhenSaveEducationInfoWithCourseCourseNameLengthGreaterThanLimit() throws Exception {
        String courseName = createLowerCaseASCIIString(GREATER_THAN_ALLOWED_FIELD_LENGTH);
        List<CourseRequestDto> courses = List.of(createCourseRequestDto().withCourseName(courseName).build());
        EducationRequestDto request = createEducationRequestDto()
                .withCourses(courses)
                .build();

        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID_FOR_EDUCATIONS)).thenReturn(true);

        String expectedContent = "Course name is too long, the max number of symbols is 40";
        mockMvc.perform(post(CV_EDUCATIONS_URL_TEMPLATE, CV_UUID_FOR_EDUCATIONS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(expectedContent)));
    }

    @ParameterizedTest
    @MethodSource("validStringsForSixthPageTextFieldsRegexp")
    void shouldReturn201WhenSaveEducationInfoWithCourseCourseNameFitRegexp(String courseName)
            throws Exception {
        List<CourseRequestDto> courses = List.of(createCourseRequestDto().withCourseName(courseName).build());
        EducationRequestDto request = createEducationRequestDto()
                .withCourses(courses)
                .build();

        when(educationService.save(any(EducationRequestDto.class), anyString())).thenReturn(any());
        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID_FOR_EDUCATIONS)).thenReturn(true);

        mockMvc.perform(post(CV_EDUCATIONS_URL_TEMPLATE, CV_UUID_FOR_EDUCATIONS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @ParameterizedTest
    @MethodSource("invalidStringsForSixthPageTextFieldsRegexp")
    void shouldReturn400WhenSaveEducationInfoWithCourseCourseNameNotFitRegexp(String courseName)
            throws Exception {
        List<CourseRequestDto> courses = List.of(createCourseRequestDto().withCourseName(courseName).build());
        EducationRequestDto request = createEducationRequestDto()
                .withCourses(courses)
                .build();

        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID_FOR_EDUCATIONS)).thenReturn(true);

        String expectedContent = "Invalid course name";
        mockMvc.perform(post(CV_EDUCATIONS_URL_TEMPLATE, CV_UUID_FOR_EDUCATIONS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(expectedContent)));
    }

    @Test
    void shouldReturn400WhenSaveEducationInfoWithCourseDescriptionLengthGreaterThanLimit() throws Exception {
        String description = createLowerCaseASCIIString(GREATER_THAN_ALLOWED_FIELD_LENGTH);
        List<CourseRequestDto> courses = List.of(createCourseRequestDto().withDescription(description).build());
        EducationRequestDto request = createEducationRequestDto()
                .withCourses(courses)
                .build();

        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID_FOR_EDUCATIONS)).thenReturn(true);

        String expectedContent = "Description is too long, the max number of symbols is 130";
        mockMvc.perform(post(CV_EDUCATIONS_URL_TEMPLATE, CV_UUID_FOR_EDUCATIONS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(expectedContent)));
    }

    @ParameterizedTest
    @MethodSource("validStringsForSixthPageTextFieldsRegexp")
    void shouldReturn201WhenSaveEducationInfoWithCourseDescriptionFitRegexp(String description)
            throws Exception {
        List<CourseRequestDto> courses = List.of(createCourseRequestDto().withDescription(description).build());
        EducationRequestDto request = createEducationRequestDto()
                .withCourses(courses)
                .build();

        when(educationService.save(any(EducationRequestDto.class), anyString())).thenReturn(any());
        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID_FOR_EDUCATIONS)).thenReturn(true);

        mockMvc.perform(post(CV_EDUCATIONS_URL_TEMPLATE, CV_UUID_FOR_EDUCATIONS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @ParameterizedTest
    @MethodSource("invalidStringsForSixthPageTextFieldsRegexp")
    void shouldReturn400WhenSaveEducationInfoWithCourseDescriptionNotFitRegexp(String description)
            throws Exception {
        List<CourseRequestDto> courses = List.of(createCourseRequestDto().withDescription(description).build());
        EducationRequestDto request = createEducationRequestDto()
                .withCourses(courses)
                .build();

        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID_FOR_EDUCATIONS)).thenReturn(true);

        String expectedContent = "Invalid description";
        mockMvc.perform(post(CV_EDUCATIONS_URL_TEMPLATE, CV_UUID_FOR_EDUCATIONS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(expectedContent)));
    }

    @Test
    void shouldReturn400WhenSaveEducationInfoWithCourseCertificateUrlLengthGreaterThanLimit() throws Exception {
        String certificateUrl = createLowerCaseASCIIString(GREATER_THAN_ALLOWED_FIELD_LENGTH);
        List<CourseRequestDto> courses = List.of(createCourseRequestDto().withCertificateUrl(certificateUrl).build());
        EducationRequestDto request = createEducationRequestDto()
                .withCourses(courses)
                .build();

        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID_FOR_EDUCATIONS)).thenReturn(true);

        String expectedContent = "Certificate URL is too long, the max number of symbols is 255";
        mockMvc.perform(post(CV_EDUCATIONS_URL_TEMPLATE, CV_UUID_FOR_EDUCATIONS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(expectedContent)));
    }

    @Test
    void shouldReturn200AndContentTypeJsonWhenGetEducation() throws Exception {
        EducationResponseDto responseDto = createEducationResponseDto().build();

        when(educationService.getEducationByCvUuid(CV_UUID_FOR_EDUCATIONS)).thenReturn(responseDto);
        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID_FOR_EDUCATIONS)).thenReturn(true);

        mockMvc.perform(get(CV_EDUCATIONS_URL_TEMPLATE, CV_UUID_FOR_EDUCATIONS)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void shouldReturn200AndInvokeBusinessLogicWhenGetEducation() throws Exception {
        when(educationService.getEducationByCvUuid(CV_UUID_FOR_EDUCATIONS)).thenReturn(any());
        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID_FOR_EDUCATIONS)).thenReturn(true);

        mockMvc.perform(get(CV_EDUCATIONS_URL_TEMPLATE, CV_UUID_FOR_EDUCATIONS)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(educationService, times(1)).getEducationByCvUuid(CV_UUID_FOR_EDUCATIONS);
        verify(curriculumVitaeService, times(1)).isCurriculumVitaeExists(CV_UUID_FOR_EDUCATIONS);
    }

    @Test
    void shouldReturn200AndCorrectJsonWhenGetEducation() throws Exception {
        EducationResponseDto responseDto = createEducationResponseDto().build();
        String expectedJson = objectMapper.writeValueAsString(responseDto);

        when(educationService.getEducationByCvUuid(CV_UUID_FOR_EDUCATIONS)).thenReturn(responseDto);
        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID_FOR_EDUCATIONS)).thenReturn(true);

        MvcResult mvcResult = mockMvc.perform(get(CV_EDUCATIONS_URL_TEMPLATE, CV_UUID_FOR_EDUCATIONS)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String actualResult = mvcResult.getResponse().getContentAsString();

        assertEquals(expectedJson, actualResult);
    }

    @Test
    void shouldReturn404WhenGetEducationWithInvalidUuid() throws Exception {
        String expectedContent = String.format("\"CV with UUID %s not found!!!\"", INVALID_CV_UUID);

        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID_FOR_EDUCATIONS)).thenReturn(true);

        mockMvc.perform(get(CV_EDUCATIONS_URL_TEMPLATE, INVALID_CV_UUID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(Matchers.containsString(expectedContent)));

    }

    static Stream<String> validStringsForSixthPageTextFieldsRegexp() {
        return Stream.of("abc", "ABC", "abc123", "\u0100\u011a\u013d\u011b", "abc,123", "abc.123", "abc-123",
                "abc/123", "abc:123", "abc;123", "abc'123", "abc 123");
    }

    static Stream<String> invalidStringsForSixthPageTextFieldsRegexp() {
        return Stream.of("\u0d85\u0da0", "abc!", "abc&", "abc?", "\u16a0\u16c4", "\u0410\u0420", "abc\n123",
                "abc\t123", "(abc)", "abc\"123\"");
    }

    static Stream<String> validStringsForInstitutionRegexp() {
        return Stream.of("abc", "ABC", "abc123", "\u0100\u011a\u013d\u011b", "abc,123", "abc.123", "abc-123",
                "abc/123", "abc:123", "abc;123", "abc'123", "abc\"123", "abc 123");
    }

    static Stream<String> invalidStringsForInstitutionRegexp() {
        return Stream.of("\u0d85\u0da0", "abc!", "abc&", "abc?", "\u16a0\u16c4", "\u0410\u0420", "abc\n123",
                "abc\t123", "(abc)");
    }
}
