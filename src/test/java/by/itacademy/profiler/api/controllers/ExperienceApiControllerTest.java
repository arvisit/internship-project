package by.itacademy.profiler.api.controllers;

import by.itacademy.profiler.usecasses.CurriculumVitaeService;
import by.itacademy.profiler.usecasses.ExperienceService;
import by.itacademy.profiler.usecasses.SphereService;
import by.itacademy.profiler.usecasses.dto.ExperienceRequestDto;
import by.itacademy.profiler.usecasses.dto.ExperienceResponseDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.YearMonth;
import java.util.List;

import static by.itacademy.profiler.util.CompetenceTestData.INVALID_CV_UUID;
import static by.itacademy.profiler.util.ExperienceTestData.CV_EXPERIENCE_URL_TEMPLATE;
import static by.itacademy.profiler.util.ExperienceTestData.CV_UUID_FOR_EXPERIENCE;
import static by.itacademy.profiler.util.ExperienceTestData.INVALID_CV_EXPERIENCE_URL_TEMPLATE;
import static by.itacademy.profiler.util.ExperienceTestData.createExperienceRequestDto;
import static by.itacademy.profiler.util.ExperienceTestData.createListExperienceResponseDto;
import static by.itacademy.profiler.util.ExperienceTestData.createListOfExperienceRequestDto;
import static by.itacademy.profiler.util.ExperienceTestData.getInvalidExperienceRequestJsonWithInvalidPeriod;
import static by.itacademy.profiler.util.ExperienceTestData.getValidExperienceRequestJson;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ExperienceApiController.class)
@AutoConfigureMockMvc(addFilters = false)
class ExperienceApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CurriculumVitaeService curriculumVitaeService;

    @MockBean
    private ExperienceService experienceService;

    @MockBean
    private SphereService sphereService;

    @Test
    void shouldReturn201WhenCreateExperienceIsSuccessful() throws Exception {
        List<ExperienceRequestDto> request = createListOfExperienceRequestDto();

        setupCommonMockBehaviorWithUuidAndSphereAndExperience(request);

        mockMvc.perform(post(CV_EXPERIENCE_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void shouldReturnExpectedExperienceResponseJson() throws Exception {
        List<ExperienceRequestDto> request = createListOfExperienceRequestDto();
        List<ExperienceResponseDto> response = createListExperienceResponseDto();

        setupCommonMockBehaviorWithUuidAndSphereAndExperience(request);
        when(experienceService.save(request, CV_UUID_FOR_EXPERIENCE)).thenReturn(response);

        MvcResult mvcResult = mockMvc.perform(post(CV_EXPERIENCE_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        String expectedJson = objectMapper.writeValueAsString(response);
        System.out.println(expectedJson);
        String resultJson = mvcResult.getResponse().getContentAsString();
        assertEquals(expectedJson, resultJson);
    }

    @Test
    void shouldInvokeExperienceBusinessLogicWhenCreateExperienceIsSuccessful() throws Exception {
        List<ExperienceRequestDto> request = createListOfExperienceRequestDto();

        setupCommonMockBehaviorWithUuidAndSphereAndExperience(request);

        mockMvc.perform(post(CV_EXPERIENCE_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated());

        verify(experienceService, times(1)).save(request, CV_UUID_FOR_EXPERIENCE);
    }

    @Test
    void shouldReturn201WhenJsonIsValid() throws Exception {
        String validJson = getValidExperienceRequestJson();
        List<ExperienceRequestDto> request = objectMapper.readValue(validJson, new TypeReference<>() {});

        setupCommonMockBehaviorWithUuidAndSphereAndExperience(request);

        mockMvc.perform(post(CV_EXPERIENCE_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validJson))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @ParameterizedTest
    @ValueSource(strings = {"2020-10", "2020-01"})
    void shouldReturn201WhenPeriodFromIsValid(String periodFrom) throws Exception {
        List<ExperienceRequestDto> request =
                List.of(createExperienceRequestDto().withPeriodFrom(YearMonth.parse(periodFrom)).build());

        setupCommonMockBehaviorWithUuidAndSphereAndExperience(request);

        mockMvc.perform(post(CV_EXPERIENCE_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void shouldReturn400WhenJsonIsInvalidWithInvalidPeriod() throws Exception {
        String invalidJson = getInvalidExperienceRequestJsonWithInvalidPeriod();

        mockMvc.perform(post(CV_EXPERIENCE_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Malformed JSON request")));
    }

    @Test
    void shouldReturn201WhenPeriodFromAndPeriodToAreValid() throws Exception {
        List<ExperienceRequestDto> request =
                List.of(createExperienceRequestDto()
                        .withPeriodFrom(YearMonth.parse("2020-10"))
                        .withPeriodTo(YearMonth.parse("2021-02"))
                        .build());

        setupCommonMockBehaviorWithUuidAndSphereAndExperience(request);

        mockMvc.perform(post(CV_EXPERIENCE_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void shouldReturn400WhenPeriodFromAndPeriodToAreInvalid() throws Exception {
        List<ExperienceRequestDto> request =
                List.of(createExperienceRequestDto()
                        .withPeriodFrom(YearMonth.parse("2020-10"))
                        .withPeriodTo(YearMonth.parse("2019-02"))
                        .build());
        String expectedContent = "Field `periodTo` should be later than `periodFrom";

        setupCommonMockBehaviorWithUuidAndSphere(request);

        mockMvc.perform(post(CV_EXPERIENCE_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(expectedContent)));
    }

    @Test
    void shouldReturn400WhenSequenceNumberIsNull() throws Exception {
        List<ExperienceRequestDto> request =
                List.of(createExperienceRequestDto().withSequenceNumber(null).build());
        String expectedContent = "Sequence number must not be null";

        setupCommonMockBehaviorWithUuidAndSphere(request);

        mockMvc.perform(post(CV_EXPERIENCE_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(expectedContent)));
    }

    @Test
    void shouldReturn400WhenSequenceNumbersAreNotUnique() throws Exception {
        List<ExperienceRequestDto> request =
                List.of(createExperienceRequestDto().withSequenceNumber(1).build(),
                        createExperienceRequestDto().withSequenceNumber(1).build());
        String expectedContent = "Invalid sequence number: must be unique";

        setupCommonMockBehaviorWithUuidAndSphere(request);

        mockMvc.perform(post(CV_EXPERIENCE_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(expectedContent)));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5})
    void shouldReturn201WhenSequenceNumberIsValid(Integer sequenceNumber) throws Exception {
        List<ExperienceRequestDto> request =
                List.of(createExperienceRequestDto().withSequenceNumber(sequenceNumber).build());

        setupCommonMockBehaviorWithUuidAndSphereAndExperience(request);

        mockMvc.perform(post(CV_EXPERIENCE_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1})
    void shouldReturn400WhenSequenceNumberLessThanOne(Integer sequenceNumber) throws Exception {
        List<ExperienceRequestDto> request =
                List.of(createExperienceRequestDto().withSequenceNumber(sequenceNumber).build());
        String expectedContent = "Sequence number must not be less than 1";

        setupCommonMockBehaviorWithUuidAndSphere(request);

        mockMvc.perform(post(CV_EXPERIENCE_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(expectedContent)));
    }

    @ParameterizedTest
    @ValueSource(ints = {6, 8, 888, Integer.MAX_VALUE})
    void shouldReturn400WhenSequenceNumberMoreThanFive(Integer sequenceNumber) throws Exception {
        List<ExperienceRequestDto> request =
                List.of(createExperienceRequestDto().withSequenceNumber(sequenceNumber).build());
        String expectedContent = "Sequence number must not be more than 5";

        setupCommonMockBehaviorWithUuidAndSphere(request);

        mockMvc.perform(post(CV_EXPERIENCE_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(expectedContent)));
    }

    @Test
    void shouldReturn400WhenPresentTimeIsNull() throws Exception {
        List<ExperienceRequestDto> request =
                List.of(createExperienceRequestDto().withPresentTime(null).build());
        String expectedContent = "Must be specified";

        setupCommonMockBehaviorWithUuidAndSphere(request);

        mockMvc.perform(post(CV_EXPERIENCE_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(expectedContent)));
    }

    @ParameterizedTest
    @CsvSource(value = {"2022-10, false"})
    void shouldReturn200WhenPresentTimeIsFalseAndPeriodToIsNotNull(YearMonth date, boolean presentTime) throws Exception {
        List<ExperienceRequestDto> request =
                List.of(createExperienceRequestDto().withPeriodTo(date).withPresentTime(presentTime).build());

        setupCommonMockBehaviorWithUuidAndSphereAndExperience(request);

        mockMvc.perform(post(CV_EXPERIENCE_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @ParameterizedTest
    @CsvSource(value = {"2022-10, true"})
    void shouldReturn400WhenPresentTimeIsTrueAndPeriodToIsNull(YearMonth date, boolean presentTime) throws Exception {
        List<ExperienceRequestDto> request =
                List.of(createExperienceRequestDto().withPeriodTo(date).withPresentTime(presentTime).build());
        String expectedContent = "If field `presentTime` is true, then field `periodTo` should be null, otherwise should not be";

        setupCommonMockBehaviorWithUuidAndSphere(request);

        mockMvc.perform(post(CV_EXPERIENCE_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(expectedContent)));
    }

    @Test
    void shouldReturn200WhenPresentTimeIsTrueAndPeriodToIsNull() throws Exception {
        List<ExperienceRequestDto> request =
                List.of(createExperienceRequestDto().withPeriodTo(null).withPresentTime(true).build());

        setupCommonMockBehaviorWithUuidAndSphereAndExperience(request);

        mockMvc.perform(post(CV_EXPERIENCE_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void shouldReturn400WhenPresentTimeIsTrueAndPeriodToIsNotNull() throws Exception {
        List<ExperienceRequestDto> request =
                List.of(createExperienceRequestDto().withPeriodTo(YearMonth.parse("2022-10")).withPresentTime(true).build());
        String expectedContent = "If field `presentTime` is true, then field `periodTo` should be null, otherwise should not be";

        setupCommonMockBehaviorWithUuidAndSphere(request);

        mockMvc.perform(post(CV_EXPERIENCE_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(expectedContent)));
    }

    @Test
    void shouldReturn400WhenInvalidSphereId() throws Exception {
        List<ExperienceRequestDto> request =
                List.of(createExperienceRequestDto().withSphereId(5464L).build());

        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID_FOR_EXPERIENCE)).thenReturn(true);
        when(sphereService.isSphereExist(request.get(0).sphereId())).thenReturn(false);

        mockMvc.perform(post(CV_EXPERIENCE_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn400WhenSphereIdIsNull() throws Exception {
        List<ExperienceRequestDto> request =
                List.of(createExperienceRequestDto().withSphereId(null).build());
        String expectedContent = "Sphere id must not be null";

        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID_FOR_EXPERIENCE)).thenReturn(true);
        when(sphereService.isSphereExist(request.get(0).sphereId())).thenReturn(false);

        mockMvc.perform(post(CV_EXPERIENCE_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(expectedContent)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"неправильно написано", "Invalid Инвалид"})
    void shouldReturn400WhenCompanyNameInvalid(String company) throws Exception {
        List<ExperienceRequestDto> request =
                List.of(createExperienceRequestDto().withCompany(company).build());
        String expectedContent = "Invalid company name";

        setupCommonMockBehaviorWithUuidAndSphere(request);

        mockMvc.perform(post(CV_EXPERIENCE_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(expectedContent)));
    }

    @Test
    void shouldReturn400WhenCompanyNameMoreThan40Chars() throws Exception {
        List<ExperienceRequestDto> request =
                List.of(createExperienceRequestDto().withCompany("Invalid Invalid Invalid Invalid Invalid Invalid Invalid").build());
        String expectedContent = "Company name is too long, the max number of symbols is 40";

        setupCommonMockBehaviorWithUuidAndSphere(request);

        mockMvc.perform(post(CV_EXPERIENCE_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(expectedContent)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"неправильно написано", "valid 11 &^$"})
    void shouldReturn400WhenPositionInvalid(String position) throws Exception {
        List<ExperienceRequestDto> request =
                List.of(createExperienceRequestDto().withPosition(position).build());
        String expectedContent = "Invalid position name";

        setupCommonMockBehaviorWithUuidAndSphere(request);

        mockMvc.perform(post(CV_EXPERIENCE_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(expectedContent)));
    }

    @Test
    void shouldReturn400WhenPositionMoreThan40Chars() throws Exception {
        List<ExperienceRequestDto> request =
                List.of(createExperienceRequestDto().withPosition("Invalid Invalid Invalid Invalid Invalid Invalid Invalid").build());
        String expectedContent = "Position name is too long, the max number of symbols is 40";

        setupCommonMockBehaviorWithUuidAndSphere(request);

        mockMvc.perform(post(CV_EXPERIENCE_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(expectedContent)));
    }

    @Test
    void shouldReturn400WhenDutiesIsEmpty() throws Exception {
        List<ExperienceRequestDto> request =
                List.of(createExperienceRequestDto().withDuties(List.of()).build());
        String expectedContent = "Duties must not be empty";

        setupCommonMockBehaviorWithUuidAndSphere(request);

        mockMvc.perform(post(CV_EXPERIENCE_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(expectedContent)));
    }

    @Test
    void shouldReturn400WhenMoreThanFiveDuties() throws Exception {
        List<ExperienceRequestDto> request =
                List.of(createExperienceRequestDto().withDuties(List.of("1", "2", "3", "4", "5", "6")).build());
        String expectedContent = "Amount of duties should not be more than 5";

        setupCommonMockBehaviorWithUuidAndSphere(request);

        mockMvc.perform(post(CV_EXPERIENCE_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(expectedContent)));
    }

    @ParameterizedTest
    @ValueSource(ints = {3, 4, 5})
    void shouldReturn400WhenMoreThanTwoDutiesForThirdFurthFifthSequenceNumber(Integer sequenceNumber) throws Exception {
        List<ExperienceRequestDto> request =
                List.of(createExperienceRequestDto().withDuties(List.of("1", "2", "3")).withSequenceNumber(sequenceNumber).build());
        String expectedContent = "Amount of duties should not be more than 2 for 3-5 job`s positions in the list";

        setupCommonMockBehaviorWithUuidAndSphere(request);

        mockMvc.perform(post(CV_EXPERIENCE_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(expectedContent)));
    }

    @Test
    void shouldReturn400WhenDutyIsNull() throws Exception {
        List<ExperienceRequestDto> request =
                List.of(createExperienceRequestDto().withDuties(List.of("Invalid duty ^%$*~")).build());
        String expectedContent = "Invalid duty";

        setupCommonMockBehaviorWithUuidAndSphere(request);

        mockMvc.perform(post(CV_EXPERIENCE_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(expectedContent)));
    }

    @Test
    void shouldReturn400WhenAchievementsIsTooLong() throws Exception {
        List<ExperienceRequestDto> request =
                List.of(createExperienceRequestDto().withAchievements(new StringBuilder().append("Very long achievements ")
                        .append("Very long achievementsVery long achievementsVery long achievementsVery ")
                        .append("Very long achievementsVery long achievementsVery long achievementsVery ")
                        .append("Very long achievementsVery long achievementsVery long achievementsVery ")
                        .append("Very long achievementsVery long achievementsVery long achievementsVery ")
                        .append("Very long achievementsVery long achievementsVery long achievementsVery ")
                        .append("Very long achievementsVery long achievementsVery long achievementsVery ")
                        .append("long achievementsVery long achievementsVery long achievementsVery long ")
                        .append("long achievementsVery long achievementsVery long achievementsVery long ")
                        .append("long achievementsVery long achievementsVery long achievementsVery long ")
                        .toString()).build());
        String expectedContent = "Achievements name is too long, the max number of symbols is 200";

        setupCommonMockBehaviorWithUuidAndSphere(request);

        mockMvc.perform(post(CV_EXPERIENCE_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(expectedContent)));
    }

    @Test
    void shouldReturn400WhenInvalidAchievements() throws Exception {
        List<ExperienceRequestDto> request =
                List.of(createExperienceRequestDto().withAchievements("achievements $%&").build());
        String expectedContent = "Invalid achievements";

        setupCommonMockBehaviorWithUuidAndSphere(request);

        mockMvc.perform(post(CV_EXPERIENCE_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(expectedContent)));
    }

    @Test
    void shouldReturn404WhenGetListOfExperienceWithInvalidUuid() throws Exception {
        when(curriculumVitaeService.isCurriculumVitaeExists(INVALID_CV_UUID)).thenReturn(false);

        mockMvc.perform(get(INVALID_CV_EXPERIENCE_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    private void setupCommonMockBehaviorWithUuidAndSphereAndExperience(List<ExperienceRequestDto> request) {
        when(experienceService.save(request, CV_UUID_FOR_EXPERIENCE)).thenReturn(any());
        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID_FOR_EXPERIENCE)).thenReturn(true);
        when(sphereService.isSphereExist(request.get(0).sphereId())).thenReturn(true);
    }

    private void setupCommonMockBehaviorWithUuidAndSphere(List<ExperienceRequestDto> request) {
        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID_FOR_EXPERIENCE)).thenReturn(true);
        when(sphereService.isSphereExist(request.get(0).sphereId())).thenReturn(true);
    }
}
