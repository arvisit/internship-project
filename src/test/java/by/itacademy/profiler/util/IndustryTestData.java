package by.itacademy.profiler.util;

import by.itacademy.profiler.persistence.model.Industry;
import by.itacademy.profiler.usecasses.dto.IndustryResponseDto;

public final class IndustryTestData {

    public static final String INDUSTRY_URL_TEMPLATE = "/api/v1/industries";
    public static final Integer EXPECTED_NUMBER_OF_INDUSTRIES = 25;

    private IndustryTestData() {
    }

    public static IndustryResponseDto.IndustryResponseDtoBuilder createIndustryResponseDto() {
        return IndustryResponseDto.builder().withId(1L).withName("IT");
    }

    public static Industry.IndustryBuilder createIndustry(){
        return Industry.builder().withId(1L).withName("IT");
    }

}
