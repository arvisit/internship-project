package by.itacademy.profiler.api.controllers;

import by.itacademy.profiler.usecasses.CurriculumVitaeService;
import by.itacademy.profiler.usecasses.PhoneCodeService;
import by.itacademy.profiler.usecasses.RecommendationService;
import by.itacademy.profiler.usecasses.dto.RecommendationRequestDto;
import by.itacademy.profiler.usecasses.dto.RecommendationResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static by.itacademy.profiler.util.RecommendationTestData.CV_NOT_FOUND_ERROR;
import static by.itacademy.profiler.util.RecommendationTestData.CV_RECOMMENDATION_URL_TEMPLATE;
import static by.itacademy.profiler.util.RecommendationTestData.CV_UUID_FOR_RECOMMENDATION;
import static by.itacademy.profiler.util.RecommendationTestData.createListOfRecommendationRequestDto;
import static by.itacademy.profiler.util.RecommendationTestData.createListRecommendationResponseDto;
import static by.itacademy.profiler.util.RecommendationTestData.createRecommendationRequestDto;
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

@WebMvcTest(controllers = RecommendationApiController.class)
@AutoConfigureMockMvc(addFilters = false)
class RecommendationApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CurriculumVitaeService curriculumVitaeService;

    @MockBean
    private RecommendationService recommendationService;

    @MockBean
    private PhoneCodeService phoneCodeService;

    public static final String MESSAGE_FOR_CONDITIONALLY_REQUIRED_FIELDS = "Field `fullName`, `company`, `position` and one of contact`s fields should not be empty when at least one of the above fields was filled";
    public static final String MESSAGE_FOR_LINK_LIMIT = "The link is too long, the max number of symbols is 50";

    @Test
    void shouldReturn201WhenCreateRecommendationsIsSuccessful() throws Exception {
        List<RecommendationRequestDto> request = createListOfRecommendationRequestDto();

        setupCommonMockBehaviorWithUuidAndPhoneCodeAndRecommendations(request);

        mockMvc.perform(post(CV_RECOMMENDATION_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void shouldReturnExpectedRecommendationsResponseJsonWhenCreateRecommendationsIsSuccessful() throws Exception {
        List<RecommendationRequestDto> request = createListOfRecommendationRequestDto();
        List<RecommendationResponseDto> response = createListRecommendationResponseDto();

        setupCommonMockBehaviorWithUuidAndPhoneCodeAndRecommendations(request);
        when(recommendationService.save(request, CV_UUID_FOR_RECOMMENDATION)).thenReturn(response);

        MvcResult mvcResult = mockMvc.perform(post(CV_RECOMMENDATION_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        String expectedJson = objectMapper.writeValueAsString(response);
        String resultJson = mvcResult.getResponse().getContentAsString();
        assertEquals(expectedJson, resultJson);
    }

    @Test
    void shouldInvokeRecommendationsBusinessLogicWhenCreateRecommendationsIsSuccessful() throws Exception {
        List<RecommendationRequestDto> request = createListOfRecommendationRequestDto();

        setupCommonMockBehaviorWithUuidAndPhoneCodeAndRecommendations(request);

        mockMvc.perform(post(CV_RECOMMENDATION_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated());

        verify(recommendationService, times(1)).save(request, CV_UUID_FOR_RECOMMENDATION);
    }

    @ParameterizedTest
    @ValueSource(strings = {"FullNAME", "Full Name", "Full-name", "Full Nāme", "Full Ńame"})
    void shouldReturn201WhenFullNameIsValid(String fullName) throws Exception {
        List<RecommendationRequestDto> request =
                List.of(createRecommendationRequestDto().withFullName(fullName).build());

        setupCommonMockBehaviorWithUuidAndPhoneCodeAndRecommendations(request);

        mockMvc.perform(post(CV_RECOMMENDATION_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Full Name/", "(Full Name)", "Full Name'", "Full Name%", "Полное имя"})
    void shouldReturn400WhenFullNameIsInvalidWithWrongChars(String fullName) throws Exception {
        List<RecommendationRequestDto> request =
                List.of(createRecommendationRequestDto().withFullName(fullName).build());

        setupCommonMockBehaviorWithUuidAndPhoneCodeAndRecommendations(request);

        String expectedContent = "Invalid full name";
        mockMvc.perform(post(CV_RECOMMENDATION_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(expectedContent)));
    }

    @Test
    void shouldReturn400WhenFullNameIsInvalidWithLongSize() throws Exception {
        List<RecommendationRequestDto> request =
                List.of(createRecommendationRequestDto().withFullName("Full Nameeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee").build());

        setupCommonMockBehaviorWithUuidAndPhoneCodeAndRecommendations(request);

        String expectedContent = "Name is too long, the max number of symbols is 40";
        mockMvc.perform(post(CV_RECOMMENDATION_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(expectedContent)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"CompanyNAME", "Company Name", "Company-name", "Company Name/", "Company Name'", "Company Name: Name;", "Company Nāme", "Company Ńame", "Company.Name", "Company, Name", "Company \"Name\""})
    void shouldReturn201WhenCompanyIsValid(String company) throws Exception {
        List<RecommendationRequestDto> request =
                List.of(createRecommendationRequestDto().withCompany(company).build());

        setupCommonMockBehaviorWithUuidAndPhoneCodeAndRecommendations(request);

        mockMvc.perform(post(CV_RECOMMENDATION_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @ParameterizedTest
    @ValueSource(strings = {"(Company Name)", "Company Name$", "Company Name%", "Название компании"})
    void shouldReturn400WhenCompanyIsInvalidWithWrongChars(String company) throws Exception {
        List<RecommendationRequestDto> request =
                List.of(createRecommendationRequestDto().withCompany(company).build());

        setupCommonMockBehaviorWithUuidAndPhoneCodeAndRecommendations(request);

        String expectedContent = "Invalid company";
        mockMvc.perform(post(CV_RECOMMENDATION_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(expectedContent)));
    }

    @Test
    void shouldReturn400WhenCompanyIsInvalidWithLongSize() throws Exception {
        List<RecommendationRequestDto> request =
                List.of(createRecommendationRequestDto().withCompany("Company Nameeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee").build());

        setupCommonMockBehaviorWithUuidAndPhoneCodeAndRecommendations(request);

        String expectedContent = "Company name is too long, the max number of symbols is 40";
        mockMvc.perform(post(CV_RECOMMENDATION_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(expectedContent)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"PositionNAME", "Position Name", "Position-name", "Position Name/", "Position Name'", "Position Name: Name;", "Position Nāme", "Position Ńame", "Position.Name", "Position, Name", "Position \"Name\""})
    void shouldReturn201WhenPositionIsValid(String position) throws Exception {
        List<RecommendationRequestDto> request =
                List.of(createRecommendationRequestDto().withPosition(position).build());

        setupCommonMockBehaviorWithUuidAndPhoneCodeAndRecommendations(request);

        mockMvc.perform(post(CV_RECOMMENDATION_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @ParameterizedTest
    @ValueSource(strings = {"(Position Name)", "Position Name$", "Position Name%", "Название позиции"})
    void shouldReturn400WhenPositionIsInvalidWithWrongChars(String position) throws Exception {
        List<RecommendationRequestDto> request =
                List.of(createRecommendationRequestDto().withPosition(position).build());

        setupCommonMockBehaviorWithUuidAndPhoneCodeAndRecommendations(request);

        String expectedContent = "Invalid position";
        mockMvc.perform(post(CV_RECOMMENDATION_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(expectedContent)));
    }

    @Test
    void shouldReturn400WhenPositionIsInvalidWithLongSize() throws Exception {
        List<RecommendationRequestDto> request =
                List.of(createRecommendationRequestDto().withPosition("Position Nameeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee").build());

        setupCommonMockBehaviorWithUuidAndPhoneCodeAndRecommendations(request);

        String expectedContent = "Position name is too long, the max number of symbols is 40";
        mockMvc.perform(post(CV_RECOMMENDATION_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(expectedContent)));
    }

    @Test
    void shouldReturn400WhenInvalidPhoneCodeId() throws Exception {
        List<RecommendationRequestDto> request =
                List.of(createRecommendationRequestDto().withPhoneCodeId(66666L).build());

        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID_FOR_RECOMMENDATION)).thenReturn(true);
        when(phoneCodeService.isPhoneCodeExist(request.get(0).phoneCodeId())).thenReturn(false);

        mockMvc.perform(post(CV_RECOMMENDATION_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn201WhenPhoneNumberIsValid() throws Exception {
        List<RecommendationRequestDto> request =
                List.of(createRecommendationRequestDto().withPhoneNumber("291112233").build());

        setupCommonMockBehaviorWithUuidAndPhoneCodeAndRecommendations(request);

        mockMvc.perform(post(CV_RECOMMENDATION_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @ParameterizedTest
    @ValueSource(strings = {"PhoneNumber", "111,56", "56544%", "66666.", "6666.0", "66666.6666", "666 666"})
    void shouldReturn400WhenPhoneNumberIsInvalid(String phoneNumber) throws Exception {
        List<RecommendationRequestDto> request =
                List.of(createRecommendationRequestDto().withPhoneNumber(phoneNumber).build());

        setupCommonMockBehaviorWithUuidAndPhoneCodeAndRecommendations(request);

        String expectedContent = "Invalid phone number";
        mockMvc.perform(post(CV_RECOMMENDATION_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(expectedContent)));
    }

    @Test
    void shouldReturn400WhenPhoneNumberIsInvalidWithLongSize() throws Exception {
        List<RecommendationRequestDto> request =
                List.of(createRecommendationRequestDto().withPhoneNumber("111111111111111111111111111111").build());

        setupCommonMockBehaviorWithUuidAndPhoneCodeAndRecommendations(request);

        String expectedContent = "Phone number is too long, the max number of symbols is 25";
        mockMvc.perform(post(CV_RECOMMENDATION_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(expectedContent)));
    }

    @Test
    void shouldReturn201WhenEmailIsValid() throws Exception {
        List<RecommendationRequestDto> request =
                List.of(createRecommendationRequestDto().withEmail("user@gmail.com").build());

        setupCommonMockBehaviorWithUuidAndPhoneCodeAndRecommendations(request);

        mockMvc.perform(post(CV_RECOMMENDATION_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @ParameterizedTest
    @ValueSource(strings = {"email", "email@", "@com"})
    void shouldReturn400WhenEmailIsInvalid(String email) throws Exception {
        List<RecommendationRequestDto> request =
                List.of(createRecommendationRequestDto().withEmail(email).build());

        setupCommonMockBehaviorWithUuidAndPhoneCodeAndRecommendations(request);

        String expectedContent = "Invalid email";
        mockMvc.perform(post(CV_RECOMMENDATION_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(expectedContent)));
    }

    @Test
    void shouldReturn400WhenEmailIsInvalidWithLongSize() throws Exception {
        List<RecommendationRequestDto> request =
                List.of(createRecommendationRequestDto().withEmail("11111111111111111111111@11111111111111111111111111111").build());

        setupCommonMockBehaviorWithUuidAndPhoneCodeAndRecommendations(request);

        String expectedContent = "The email is too long, the max number of symbols is 50";
        mockMvc.perform(post(CV_RECOMMENDATION_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(expectedContent)));
    }

    @Test
    void shouldReturn201WhenLinkedInIsValid() throws Exception {
        List<RecommendationRequestDto> request =
                List.of(createRecommendationRequestDto().withLinkedIn("http://example.com/link").build());

        setupCommonMockBehaviorWithUuidAndPhoneCodeAndRecommendations(request);

        mockMvc.perform(post(CV_RECOMMENDATION_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void shouldReturn201WhenTelegramIsValid() throws Exception {
        List<RecommendationRequestDto> request =
                List.of(createRecommendationRequestDto().withTelegram("telegram").build());

        setupCommonMockBehaviorWithUuidAndPhoneCodeAndRecommendations(request);

        mockMvc.perform(post(CV_RECOMMENDATION_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void shouldReturn400WhenTelegramIsInvalidWithLongSize() throws Exception {
        List<RecommendationRequestDto> request =
                List.of(createRecommendationRequestDto().withTelegram("Telegrammmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm").build());

        setupCommonMockBehaviorWithUuidAndPhoneCodeAndRecommendations(request);

        mockMvc.perform(post(CV_RECOMMENDATION_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(MESSAGE_FOR_LINK_LIMIT)));
    }

    @Test
    void shouldReturn201WhenViberIsValid() throws Exception {
        List<RecommendationRequestDto> request =
                List.of(createRecommendationRequestDto().withViber("viber").build());

        setupCommonMockBehaviorWithUuidAndPhoneCodeAndRecommendations(request);

        mockMvc.perform(post(CV_RECOMMENDATION_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void shouldReturn400WhenViberIsInvalidWithLongSize() throws Exception {
        List<RecommendationRequestDto> request =
                List.of(createRecommendationRequestDto().withViber("Viiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiber").build());

        setupCommonMockBehaviorWithUuidAndPhoneCodeAndRecommendations(request);

        mockMvc.perform(post(CV_RECOMMENDATION_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(MESSAGE_FOR_LINK_LIMIT)));
    }

    @Test
    void shouldReturn201WhenWhatsAppIsValid() throws Exception {
        List<RecommendationRequestDto> request =
                List.of(createRecommendationRequestDto().withWhatsApp("whatsApp").build());

        setupCommonMockBehaviorWithUuidAndPhoneCodeAndRecommendations(request);

        mockMvc.perform(post(CV_RECOMMENDATION_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void shouldReturn400WhenWhatsAppIsInvalidWithLongSize() throws Exception {
        List<RecommendationRequestDto> request =
                List.of(createRecommendationRequestDto().withWhatsApp("whatsAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAApp").build());

        setupCommonMockBehaviorWithUuidAndPhoneCodeAndRecommendations(request);

        mockMvc.perform(post(CV_RECOMMENDATION_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(MESSAGE_FOR_LINK_LIMIT)));
    }

    @Test
    void shouldReturn201WhenRecommendationsIsValid() throws Exception {
        List<RecommendationRequestDto> request =
                List.of(createRecommendationRequestDto().withRecommendations("http://example.com/link").build());

        setupCommonMockBehaviorWithUuidAndPhoneCodeAndRecommendations(request);

        mockMvc.perform(post(CV_RECOMMENDATION_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void shouldReturn400WhenFullNameNotNullButOtherFieldsIsNull() throws Exception {
        List<RecommendationRequestDto> request =
                List.of(createRecommendationRequestDto()
                        .withFullName("Not Null")
                        .withCompany(null)
                        .withPosition(null)
                        .withPhoneCodeId(null)
                        .withPhoneNumber(null)
                        .withEmail(null)
                        .withLinkedIn(null)
                        .withTelegram(null)
                        .withViber(null)
                        .withWhatsApp(null)
                        .withRecommendations(null)
                        .build());

        when(recommendationService.save(request, CV_UUID_FOR_RECOMMENDATION)).thenReturn(any());
        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID_FOR_RECOMMENDATION)).thenReturn(true);

        mockMvc.perform(post(CV_RECOMMENDATION_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(MESSAGE_FOR_CONDITIONALLY_REQUIRED_FIELDS)));
    }

    @Test
    void shouldReturn400WhenCompanyNotNullButOtherFieldsIsNull() throws Exception {
        List<RecommendationRequestDto> request =
                List.of(createRecommendationRequestDto()
                        .withFullName(null)
                        .withCompany("Not Null")
                        .withPosition(null)
                        .withPhoneCodeId(null)
                        .withPhoneNumber(null)
                        .withEmail(null)
                        .withLinkedIn(null)
                        .withTelegram(null)
                        .withViber(null)
                        .withWhatsApp(null)
                        .withRecommendations(null)
                        .build());

        when(recommendationService.save(request, CV_UUID_FOR_RECOMMENDATION)).thenReturn(any());
        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID_FOR_RECOMMENDATION)).thenReturn(true);

        mockMvc.perform(post(CV_RECOMMENDATION_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(MESSAGE_FOR_CONDITIONALLY_REQUIRED_FIELDS)));
    }

    @Test
    void shouldReturn400WhenFullNameCompanyPositionNotNullButOtherFieldsIsNull() throws Exception {
        List<RecommendationRequestDto> request =
                List.of(createRecommendationRequestDto()
                        .withFullName("Not Null")
                        .withCompany("Not Null")
                        .withPosition("Not Null")
                        .withPhoneCodeId(null)
                        .withPhoneNumber(null)
                        .withEmail(null)
                        .withLinkedIn(null)
                        .withTelegram(null)
                        .withViber(null)
                        .withWhatsApp(null)
                        .withRecommendations(null)
                        .build());

        when(recommendationService.save(request, CV_UUID_FOR_RECOMMENDATION)).thenReturn(any());
        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID_FOR_RECOMMENDATION)).thenReturn(true);

        mockMvc.perform(post(CV_RECOMMENDATION_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(MESSAGE_FOR_CONDITIONALLY_REQUIRED_FIELDS)));
    }

    @Test
    void shouldReturn400WhenEmailNotNullButOtherFieldsIsNull() throws Exception {
        List<RecommendationRequestDto> request =
                List.of(createRecommendationRequestDto()
                        .withFullName(null)
                        .withCompany(null)
                        .withPosition(null)
                        .withPhoneCodeId(null)
                        .withPhoneNumber(null)
                        .withEmail("Not Null")
                        .withLinkedIn(null)
                        .withTelegram(null)
                        .withViber(null)
                        .withWhatsApp(null)
                        .withRecommendations(null)
                        .build());

        when(recommendationService.save(request, CV_UUID_FOR_RECOMMENDATION)).thenReturn(any());
        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID_FOR_RECOMMENDATION)).thenReturn(true);

        mockMvc.perform(post(CV_RECOMMENDATION_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(MESSAGE_FOR_CONDITIONALLY_REQUIRED_FIELDS)));
    }

    @Test
    void shouldReturn400WhenRecommendationsNotNullButCompanyIsNull() throws Exception {
        List<RecommendationRequestDto> request =
                List.of(createRecommendationRequestDto()
                        .withFullName(null)
                        .withCompany(null)
                        .withPosition(null)
                        .withPhoneCodeId(null)
                        .withPhoneNumber(null)
                        .withEmail(null)
                        .withLinkedIn(null)
                        .withTelegram(null)
                        .withViber(null)
                        .withWhatsApp(null)
                        .withRecommendations("Not Null")
                        .build());

        when(recommendationService.save(request, CV_UUID_FOR_RECOMMENDATION)).thenReturn(any());
        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID_FOR_RECOMMENDATION)).thenReturn(true);

        String expectedContent = "Field `company` should not be empty if field `recommendations` is not empty";
        mockMvc.perform(post(CV_RECOMMENDATION_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(expectedContent)));
    }

    @Test
    void shouldReturn200AndContentTypeJsonWhenGetRecommendationsIsSuccessful() throws Exception {
        List<RecommendationResponseDto> responseDtos = createListRecommendationResponseDto();

        when(recommendationService.getRecommendationsByCvUuid(CV_UUID_FOR_RECOMMENDATION)).thenReturn(responseDtos);
        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID_FOR_RECOMMENDATION)).thenReturn(true);

        mockMvc.perform(get(CV_RECOMMENDATION_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void shouldReturn200AndInvokeBusinessLogicWhenGetRecommendationsIsSuccessful() throws Exception {
        List<RecommendationResponseDto> responseDtos = createListRecommendationResponseDto();

        when(recommendationService.getRecommendationsByCvUuid(CV_UUID_FOR_RECOMMENDATION)).thenReturn(responseDtos);
        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID_FOR_RECOMMENDATION)).thenReturn(true);

        mockMvc.perform(get(CV_RECOMMENDATION_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        verify(recommendationService, times(1)).getRecommendationsByCvUuid(CV_UUID_FOR_RECOMMENDATION);
        verify(curriculumVitaeService, times(1)).isCurriculumVitaeExists(CV_UUID_FOR_RECOMMENDATION);
    }

    @Test
    void shouldReturn200AndCorrectJsonWhenGetRecommendationsIsSuccessful() throws Exception {
        List<RecommendationResponseDto> responseDtos = createListRecommendationResponseDto();
        String expectedJson = objectMapper.writeValueAsString(responseDtos);

        when(recommendationService.getRecommendationsByCvUuid(CV_UUID_FOR_RECOMMENDATION)).thenReturn(responseDtos);
        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID_FOR_RECOMMENDATION)).thenReturn(true);

        MvcResult mvcResult = mockMvc.perform(get(CV_RECOMMENDATION_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String actualResult = mvcResult.getResponse().getContentAsString();

        assertEquals(expectedJson, actualResult);
    }

    @Test
    void shouldReturn404WhenGetRecommendationsWithInvalidUuid() throws Exception {
        String expectedContent = CV_NOT_FOUND_ERROR;

        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID_FOR_RECOMMENDATION)).thenReturn(false);

        mockMvc.perform(get(CV_RECOMMENDATION_URL_TEMPLATE)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString(expectedContent)));
    }

    private void setupCommonMockBehaviorWithUuidAndPhoneCodeAndRecommendations(List<RecommendationRequestDto> request) {
        when(recommendationService.save(request, CV_UUID_FOR_RECOMMENDATION)).thenReturn(any());
        when(curriculumVitaeService.isCurriculumVitaeExists(CV_UUID_FOR_RECOMMENDATION)).thenReturn(true);
        when(phoneCodeService.isPhoneCodeExist(request.get(0).phoneCodeId())).thenReturn(true);
    }

}
