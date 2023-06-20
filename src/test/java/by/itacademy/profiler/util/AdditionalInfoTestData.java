package by.itacademy.profiler.util;

import by.itacademy.profiler.persistence.model.AdditionalInformation;
import by.itacademy.profiler.persistence.model.Award;
import by.itacademy.profiler.usecasses.dto.AdditionalInformationRequestDto;
import by.itacademy.profiler.usecasses.dto.AdditionalInformationResponseDto;
import by.itacademy.profiler.usecasses.dto.AwardDto;

import java.time.YearMonth;
import java.util.List;


public final class AdditionalInfoTestData {
    public static final String ADDITIONAL_INFORMATION_CV_UUID = "123e4567-e89b-12d3-a456-426614174001";
    public static final String CV_ADDITIONAL_INFORMATION_URL_TEMPLATE = String.format("/api/v1/cvs/%s/additional-information", ADDITIONAL_INFORMATION_CV_UUID);
    public static final String REGEXP_VALIDATE_ADDITIONAL_INFORMATION_ERROR = "Invalid Additional information";
    public static final String MAXLENGTH_ADDITIONAL_INFORMATION_ERROR = "Additional information is too long, the max number of symbols is 150";
    public static final String FIELD_ADDITIONAL_INFORMATION_NOT_NULL_ERROR = "Additional information must not be null";
    public static final String REGEXP_VALIDATE_HOBBY_ERROR = "Invalid hobby";
    public static final String MAXLENGTH_VALIDATE_HOBBY_ERROR = "Hobby is too long, the max number of symbols is 100";
    public static final String REGEXP_VALIDATE_TITLE_ERROR = "Invalid title";
    public static final String FIELD_TITLE_NOT_NULL_ERROR = "Title must not be null";
    public static final String MAXLENGTH_VALIDATE_TITLE_ERROR = "Title is too long, the max number of symbols is 30";

    public static final String REGEXP_VALIDATE_ISSUER_ERROR = "Invalid issuer";
    public static final String MAXLENGTH_VALIDATE_ISSUER_ERROR = "Issuer is too long, the max number of symbols is 25";
    public static final String REGEXP_VALIDATE_DESCRIPTION_ERROR = "Invalid Description";
    public static final String MAXLENGTH_VALIDATE_DESCRIPTION_ERROR = "Description is too long, the max number of symbols is 70";

    private AdditionalInfoTestData() {
    }

    public static AwardDto.AwardDtoBuilder createAwardDto() {
        return AwardDto.builder()
                .withDescription("test description")
                .withDate(YearMonth.of(2023, 3))
                .withIssuer("test issuer")
                .withTitle("test title")
                .withLink("https://example.com/rnd");
    }

    public static Award.AwardBuilder createAwardEntity() {
        return Award.builder()
                .withDescription("test description")
                .withDate(YearMonth.of(2023, 3))
                .withIssuer("test issuer")
                .withTitle("test title")
                .withLink("https://example.com/rnd");
    }

    public static AdditionalInformationRequestDto.AdditionalInformationRequestDtoBuilder createAdditionalInformationRequestDto() {
        return AdditionalInformationRequestDto.builder()
                .withAdditionalInfo("Test additional info")
                .withHobby("Test hobby")
                .withAwards(List.of(createAwardDto().build()));
    }

    public static AdditionalInformation.AdditionalInformationBuilder createAdditionalInformationEntity() {
        return AdditionalInformation.builder()
                .withId(1L)
                .withAdditionalInfo("Test additional info")
                .withHobby("Test hobby")
                .withAwards(List.of(createAwardEntity().build()));
    }

    public static AdditionalInformationResponseDto.AdditionalInformationResponseDtoBuilder createAdditionalInformationResponseDto() {
        return AdditionalInformationResponseDto.builder()
                .withAdditionalInfo("Test additional info")
                .withHobby("Test hobby")
                .withAwards(List.of(createAwardDto().build()));
    }

}
