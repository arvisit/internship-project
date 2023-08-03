package by.itacademy.profiler.util;

import by.itacademy.profiler.persistence.model.Recommendation;
import by.itacademy.profiler.usecasses.dto.RecommendationRequestDto;
import by.itacademy.profiler.usecasses.dto.RecommendationResponseDto;

import java.util.List;

import static by.itacademy.profiler.util.PhoneCodeTestData.createPhoneCode;

public class RecommendationTestData {

    public static final String CV_UUID_FOR_RECOMMENDATION = "123e4567-e89b-12d3-a456-426614174001";
    public static final String CV_RECOMMENDATION_URL_TEMPLATE = String.format("/api/v1/cvs/%s/recommendations", CV_UUID_FOR_RECOMMENDATION);
    public static final Integer EXPECTED_SIZE_OF_RECOMMENDATION_LIST = 3;

    public static RecommendationRequestDto.RecommendationRequestDtoBuilder createRecommendationRequestDto() {
        return RecommendationRequestDto.builder()
                .withFullName("Full Name")
                .withCompany("Company")
                .withPosition("Position")
                .withPhoneCodeId(1L)
                .withPhoneNumber("291112255")
                .withEmail("qwe@gmail.com")
                .withLinkedIn("linkedIn")
                .withTelegram("telegram")
                .withViber("viber")
                .withWhatsApp("whatsApp")
                .withRecommendations("http://url");
    }

    public static RecommendationResponseDto.RecommendationResponseDtoBuilder createRecommendationResponseDto() {
        return RecommendationResponseDto.builder()
                .withId(1L)
                .withFullName("Full Name")
                .withCompany("Company")
                .withPosition("Position")
                .withPhoneCodeId(1L)
                .withPhoneCode(375)
                .withPhoneNumber("291112255")
                .withEmail("qwe@gmail.com")
                .withLinkedIn("linkedIn")
                .withTelegram("telegram")
                .withViber("viber")
                .withWhatsApp("whatsApp")
                .withRecommendations("http://url");
    }

    public static Recommendation.RecommendationBuilder createRecommendation() {
        return Recommendation.builder()
                .withId(1L)
                .withFullName("Full Name")
                .withCompany("Company")
                .withPosition("Position")
                .withPhoneCode(createPhoneCode())
                .withPhoneNumber("291112255")
                .withEmail("qwe@gmail.com")
                .withLinkedIn("linkedIn")
                .withTelegram("telegram")
                .withViber("viber")
                .withWhatsApp("whatsApp")
                .withRecommendations("http://url");
    }

    public static List<RecommendationRequestDto> createListOfRecommendationRequestDto() {
        return List.of(
                createRecommendationRequestDto().withFullName("Another Full Name").build(),
                createRecommendationRequestDto().withCompany("Another Company").build(),
                createRecommendationRequestDto().withPosition("Another Position").build()
        );
    }

    public static List<RecommendationResponseDto> createListRecommendationResponseDto() {
        return List.of(
                createRecommendationResponseDto().withFullName("Another Full Name").build(),
                createRecommendationResponseDto().withCompany("Another Company").build(),
                createRecommendationResponseDto().withPosition("Another Position").build()
        );
    }
}
