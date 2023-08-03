package by.itacademy.profiler.usecasses.mapper;

import by.itacademy.profiler.persistence.model.PhoneCode;
import by.itacademy.profiler.persistence.model.Recommendation;
import by.itacademy.profiler.usecasses.PhoneCodeService;
import by.itacademy.profiler.usecasses.dto.RecommendationRequestDto;
import by.itacademy.profiler.usecasses.dto.RecommendationResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static by.itacademy.profiler.util.PhoneCodeTestData.createPhoneCode;
import static by.itacademy.profiler.util.RecommendationTestData.createRecommendation;
import static by.itacademy.profiler.util.RecommendationTestData.createRecommendationRequestDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecommendationMapperTest {

    @InjectMocks
    private final RecommendationMapper recommendationMapper = Mappers.getMapper(RecommendationMapper.class);

    @Mock
    private PhoneCodeService phoneCodeService;

    @Test
    void shouldMapFullNameCorrectlyWhenInvokeFromDtoToEntity() {
        String fullName = "Full Name";
        RecommendationRequestDto recommendationRequestDto = createRecommendationRequestDto().withFullName(fullName).build();

        when(phoneCodeService.getPhoneCodeById(1L)).thenReturn(createPhoneCode());
        Recommendation recommendation = recommendationMapper.fromDtoToEntity(recommendationRequestDto);
        assertEquals(fullName, recommendation.getFullName());
    }

    @Test
    void shouldMapCompanyCorrectlyWhenInvokeFromDtoToEntity() {
        String company = "Other company";
        RecommendationRequestDto recommendationRequestDto = createRecommendationRequestDto().withCompany(company).build();

        when(phoneCodeService.getPhoneCodeById(1L)).thenReturn(createPhoneCode());
        Recommendation recommendation = recommendationMapper.fromDtoToEntity(recommendationRequestDto);
        assertEquals(company, recommendation.getCompany());
    }

    @Test
    void shouldMapPositionCorrectlyWhenInvokeFromDtoToEntity() {
        String position = "Other position";
        RecommendationRequestDto recommendationRequestDto = createRecommendationRequestDto().withPosition(position).build();

        when(phoneCodeService.getPhoneCodeById(1L)).thenReturn(createPhoneCode());
        Recommendation recommendation = recommendationMapper.fromDtoToEntity(recommendationRequestDto);
        assertEquals(position, recommendation.getPosition());
    }

    @Test
    void shouldMapPhoneCodeCorrectlyWhenInvokeFromDtoToEntity() {
        Long phoneCodeId = 1L;
        PhoneCode phoneCode = createPhoneCode();
        RecommendationRequestDto recommendationRequestDto = createRecommendationRequestDto().withPhoneCodeId(phoneCodeId).build();

        when(phoneCodeService.getPhoneCodeById(1L)).thenReturn(phoneCode);

        Recommendation recommendation = recommendationMapper.fromDtoToEntity(recommendationRequestDto);
        assertEquals(phoneCode, recommendation.getPhoneCode());
    }

    @Test
    void shouldMapPhoneNumberCorrectlyWhenInvokeFromDtoToEntity() {
        String phoneNumber = "291112233";
        RecommendationRequestDto recommendationRequestDto = createRecommendationRequestDto().withPhoneNumber(phoneNumber).build();

        when(phoneCodeService.getPhoneCodeById(1L)).thenReturn(createPhoneCode());
        Recommendation recommendation = recommendationMapper.fromDtoToEntity(recommendationRequestDto);
        assertEquals(phoneNumber, recommendation.getPhoneNumber());
    }

    @Test
    void shouldMapEmailCorrectlyWhenInvokeFromDtoToEntity() {
        String email = "user@gmail.com";
        RecommendationRequestDto recommendationRequestDto = createRecommendationRequestDto().withEmail(email).build();

        when(phoneCodeService.getPhoneCodeById(1L)).thenReturn(createPhoneCode());
        Recommendation recommendation = recommendationMapper.fromDtoToEntity(recommendationRequestDto);
        assertEquals(email, recommendation.getEmail());
    }

    @Test
    void shouldMapLinkedInCorrectlyWhenInvokeFromDtoToEntity() {
        String linkedIn = "linkedIn";
        RecommendationRequestDto recommendationRequestDto = createRecommendationRequestDto().withLinkedIn(linkedIn).build();

        when(phoneCodeService.getPhoneCodeById(1L)).thenReturn(createPhoneCode());
        Recommendation recommendation = recommendationMapper.fromDtoToEntity(recommendationRequestDto);
        assertEquals(linkedIn, recommendation.getLinkedIn());
    }

    @Test
    void shouldMapTelegramCorrectlyWhenInvokeFromDtoToEntity() {
        String telegram = "telegram";
        RecommendationRequestDto recommendationRequestDto = createRecommendationRequestDto().withTelegram(telegram).build();

        when(phoneCodeService.getPhoneCodeById(1L)).thenReturn(createPhoneCode());
        Recommendation recommendation = recommendationMapper.fromDtoToEntity(recommendationRequestDto);
        assertEquals(telegram, recommendation.getTelegram());
    }

    @Test
    void shouldMapViberCorrectlyWhenInvokeFromDtoToEntity() {
        String viber = "viber";
        RecommendationRequestDto recommendationRequestDto = createRecommendationRequestDto().withViber(viber).build();

        when(phoneCodeService.getPhoneCodeById(1L)).thenReturn(createPhoneCode());
        Recommendation recommendation = recommendationMapper.fromDtoToEntity(recommendationRequestDto);
        assertEquals(viber, recommendation.getViber());
    }

    @Test
    void shouldMapWhatsAppCorrectlyWhenInvokeFromDtoToEntity() {
        String whatsApp = "whatsApp";
        RecommendationRequestDto recommendationRequestDto = createRecommendationRequestDto().withWhatsApp(whatsApp).build();

        when(phoneCodeService.getPhoneCodeById(1L)).thenReturn(createPhoneCode());
        Recommendation recommendation = recommendationMapper.fromDtoToEntity(recommendationRequestDto);
        assertEquals(whatsApp, recommendation.getWhatsApp());
    }

    @Test
    void shouldMapRecommendationsCorrectlyWhenInvokeFromDtoToEntity() {
        String recommendations = "recommendations";
        RecommendationRequestDto recommendationRequestDto = createRecommendationRequestDto().withRecommendations(recommendations).build();

        when(phoneCodeService.getPhoneCodeById(1L)).thenReturn(createPhoneCode());
        Recommendation recommendation = recommendationMapper.fromDtoToEntity(recommendationRequestDto);
        assertEquals(recommendations, recommendation.getRecommendations());
    }

    @Test
    void shouldMapFullNameCorrectlyWhenInvokeFromEntityToDto() {
        Recommendation recommendation = createRecommendation().build();

        RecommendationResponseDto recommendationResponseDto = recommendationMapper.fromEntityToDto(recommendation);
        assertEquals(recommendationResponseDto.fullName(), recommendation.getFullName());
    }

    @Test
    void shouldMapCompanyCorrectlyWhenInvokeFromEntityToDto() {
        Recommendation recommendation = createRecommendation().build();

        RecommendationResponseDto recommendationResponseDto = recommendationMapper.fromEntityToDto(recommendation);
        assertEquals(recommendationResponseDto.company(), recommendation.getCompany());
    }

    @Test
    void shouldMapPositionCorrectlyWhenInvokeFromEntityToDto() {
        Recommendation recommendation = createRecommendation().build();

        RecommendationResponseDto recommendationResponseDto = recommendationMapper.fromEntityToDto(recommendation);
        assertEquals(recommendationResponseDto.position(), recommendation.getPosition());
    }

    @Test
    void shouldMapPhoneCodeIdCorrectlyWhenInvokeFromEntityToDto() {
        Recommendation recommendation = createRecommendation().build();

        RecommendationResponseDto recommendationResponseDto = recommendationMapper.fromEntityToDto(recommendation);
        assertEquals(recommendationResponseDto.phoneCodeId(), recommendation.getPhoneCode().getId());
    }

    @Test
    void shouldMapPhoneCodeCorrectlyWhenInvokeFromEntityToDto() {
        Recommendation recommendation = createRecommendation().build();

        RecommendationResponseDto recommendationResponseDto = recommendationMapper.fromEntityToDto(recommendation);
        assertEquals(recommendationResponseDto.phoneCode(), recommendation.getPhoneCode().getCode());
    }

    @Test
    void shouldMapPhoneNumberCorrectlyWhenInvokeFromEntityToDto() {
        Recommendation recommendation = createRecommendation().build();

        RecommendationResponseDto recommendationResponseDto = recommendationMapper.fromEntityToDto(recommendation);
        assertEquals(recommendationResponseDto.phoneNumber(), recommendation.getPhoneNumber());
    }

    @Test
    void shouldMapEmailCorrectlyWhenInvokeFromEntityToDto() {
        Recommendation recommendation = createRecommendation().build();

        RecommendationResponseDto recommendationResponseDto = recommendationMapper.fromEntityToDto(recommendation);
        assertEquals(recommendationResponseDto.email(), recommendation.getEmail());
    }

    @Test
    void shouldMapLinkedInCorrectlyWhenInvokeFromEntityToDto() {
        Recommendation recommendation = createRecommendation().build();

        RecommendationResponseDto recommendationResponseDto = recommendationMapper.fromEntityToDto(recommendation);
        assertEquals(recommendationResponseDto.linkedIn(), recommendation.getLinkedIn());
    }

    @Test
    void shouldMapTelegramCorrectlyWhenInvokeFromEntityToDto() {
        Recommendation recommendation = createRecommendation().build();

        RecommendationResponseDto recommendationResponseDto = recommendationMapper.fromEntityToDto(recommendation);
        assertEquals(recommendationResponseDto.telegram(), recommendation.getTelegram());
    }

    @Test
    void shouldMapViberCorrectlyWhenInvokeFromEntityToDto() {
        Recommendation recommendation = createRecommendation().build();

        RecommendationResponseDto recommendationResponseDto = recommendationMapper.fromEntityToDto(recommendation);
        assertEquals(recommendationResponseDto.viber(), recommendation.getViber());
    }

    @Test
    void shouldMapWhatsAppCorrectlyWhenInvokeFromEntityToDto() {
        Recommendation recommendation = createRecommendation().build();

        RecommendationResponseDto recommendationResponseDto = recommendationMapper.fromEntityToDto(recommendation);
        assertEquals(recommendationResponseDto.whatsApp(), recommendation.getWhatsApp());
    }

    @Test
    void shouldMapRecommendationsCorrectlyWhenInvokeFromEntityToDto() {
        Recommendation recommendation = createRecommendation().build();

        RecommendationResponseDto recommendationResponseDto = recommendationMapper.fromEntityToDto(recommendation);
        assertEquals(recommendationResponseDto.recommendations(), recommendation.getRecommendations());
    }
}
