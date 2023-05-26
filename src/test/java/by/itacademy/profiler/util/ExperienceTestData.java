package by.itacademy.profiler.util;

import by.itacademy.profiler.persistence.model.Experience;
import by.itacademy.profiler.usecasses.dto.ExperienceRequestDto;
import by.itacademy.profiler.usecasses.dto.ExperienceResponseDto;

import java.time.YearMonth;
import java.util.List;

import static by.itacademy.profiler.util.CompetenceTestData.INVALID_CV_UUID;
import static by.itacademy.profiler.util.IndustryTestData.createIndustry;

public class ExperienceTestData {

    public static final String CV_UUID_FOR_EXPERIENCE = "123e4567-e89b-12d3-a456-426614174001";
    public static final String CV_EXPERIENCE_URL_TEMPLATE = String.format("/api/v1/cvs/%s/experience", CV_UUID_FOR_EXPERIENCE);
    public static final String INVALID_CV_EXPERIENCE_URL_TEMPLATE = String.format("/api/v1/cvs/%s/competences", INVALID_CV_UUID);

    public static final Integer EXPECTED_SIZE_OF_EXPERIENCE_LIST = 3;

    public static ExperienceRequestDto.ExperienceRequestDtoBuilder createExperienceRequestDto() {
        return ExperienceRequestDto.builder()
                .withSequenceNumber(1)
                .withPeriodFrom(YearMonth.parse("2020-10"))
                .withPeriodTo(YearMonth.parse("2022-01"))
                .withPresentTime(false)
                .withIndustryId(1L)
                .withCompany("Some Company")
                .withPosition("Some Position")
                .withDuties(List.of("duty1", "duty2"))
                .withAchievements("Some achievement")
                .withLink("http://url");
    }

    public static ExperienceResponseDto.ExperienceResponseDtoBuilder createExperienceResponseDto() {
        return ExperienceResponseDto.builder()
                .withId(1L)
                .withSequenceNumber(1)
                .withPeriodFrom(YearMonth.parse("2020-10"))
                .withPeriodTo(YearMonth.parse("2022-01"))
                .withPresentTime(false)
                .withIndustryId(1L)
                .withIndustryName("IT")
                .withCompany("Some Company")
                .withPosition("Some Position")
                .withDuties(List.of("duty1", "duty2"))
                .withAchievements("Some achievement")
                .withLink("http://url");
    }

    public static Experience.ExperienceBuilder createExperience() {
        return Experience.builder()
                .withId(1L)
                .withSequenceNumber(1)
                .withPeriodFrom(YearMonth.parse("2020-10"))
                .withPeriodTo(YearMonth.parse("2022-01"))
                .withPresentTime(false)
                .withIndustry(createIndustry().build())
                .withCompany("Some Company")
                .withPosition("Some Position")
                .withDuties(List.of("duty1", "duty2"))
                .withAchievements("Some achievement")
                .withLink("http://url");
    }

    public static List<ExperienceRequestDto> createListOfExperienceRequestDto() {
        return List.of(
                createExperienceRequestDto().withSequenceNumber(1).build(),
                createExperienceRequestDto().withSequenceNumber(2).withCompany("ABC").build(),
                createExperienceRequestDto().withSequenceNumber(3).withPosition("Spy").build()
        );
    }

    public static List<ExperienceResponseDto> createListExperienceResponseDto() {
        return List.of(
                createExperienceResponseDto().withSequenceNumber(1).build(),
                createExperienceResponseDto().withSequenceNumber(2).withCompany("ABC").build(),
                createExperienceResponseDto().withSequenceNumber(3).withPosition("Spy").build()
        );
    }
    public static String getValidExperienceRequestJson() {
        return "[" +
                "{\"sequenceNumber\":1,\"periodFrom\":\"2020-10\",\"periodTo\":\"2022-01\",\"presentTime\":false," +
                "\"industryId\":1,\"company\":\"Some Company\",\"position\":\"Some Position\",\"duties\":[\"duty1\",\"duty2\"]," +
                "\"achievements\":\"Some achievement\",\"link\":\"http://url\"}," +
                "{\"sequenceNumber\":2,\"periodFrom\":\"2020-10\",\"periodTo\":\"2022-01\",\"presentTime\":false," +
                "\"industryId\":1,\"company\":\"ABC\",\"position\":\"Some Position\",\"duties\":[\"duty1\",\"duty2\"]," +
                "\"achievements\":\"Some achievement\",\"link\":\"http://url\"}," +
                "{\"sequenceNumber\":3,\"periodFrom\":\"2020-10\",\"periodTo\":\"2022-01\",\"presentTime\":false," +
                "\"industryId\":1,\"company\":\"Some Company\",\"position\":\"Spy\",\"duties\":[\"duty1\",\"duty2\"]," +
                "\"achievements\":\"Some achievement\",\"link\":\"http://url\"}]";
    }

    public static String getInvalidExperienceRequestJsonWithInvalidPeriod() {
        return "[" +
                "{\"sequenceNumber\":1,\"periodFrom\":\"2020-1\",\"periodTo\":\"2022-01\",\"presentTime\":false," +
                "\"industryId\":1,\"company\":\"Some Company\",\"position\":\"Some Position\",\"duties\":[\"duty1\",\"duty2\"]," +
                "\"achievements\":\"Some achievement\",\"link\":\"http://url\"}," +
                "{\"sequenceNumber\":2,\"periodFrom\":\"20-10\",\"periodTo\":\"2022-01\",\"presentTime\":false," +
                "\"industryId\":1,\"company\":\"ABC\",\"position\":\"Some Position\",\"duties\":[\"duty1\",\"duty2\"]," +
                "\"achievements\":\"Some achievement\",\"link\":\"http://url\"},";
    }
}
