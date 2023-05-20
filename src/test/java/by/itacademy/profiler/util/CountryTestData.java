package by.itacademy.profiler.util;

import by.itacademy.profiler.persistence.model.Country;
import by.itacademy.profiler.usecasses.dto.CountryDto;

public class CountryTestData {

    public static final String COUNTRIES_URL_TEMPLATE = "/api/v1/countries";
    public static final String DEFAULT_COUNTRY_NAME = "Afghanistan";
    public static final int NUMBER_OF_COUNTRIES_IN_DB = 183;

    private CountryTestData() {
    }

    public static Country.CountryBuilder createCountry() {
        return Country.builder()
                .withId(1L)
                .withCountryName(DEFAULT_COUNTRY_NAME);
    }

    public static CountryDto.CountryDtoBuilder createCountryDto() {
        return CountryDto.builder()
                .withId(1L)
                .withCountryName(DEFAULT_COUNTRY_NAME);
    }
}
