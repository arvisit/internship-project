package by.itacademy.profiler.usecasses.mapper;

import static by.itacademy.profiler.util.CountryTestData.createCountry;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import by.itacademy.profiler.persistence.model.Country;
import by.itacademy.profiler.usecasses.dto.CountryDto;

class CountryMapperTest {

    private final CountryMapper countryMapper = Mappers.getMapper(CountryMapper.class);

    @Test
    void shouldMapCorrectlyAllFieldsWhenInvokeFromEntityToDto() {
        List<Country> countries = List.of(createCountry().withId(1L).withCountryName("Afghanistan").build(),
                createCountry().withId(2L).withCountryName("Angola").build());
        List<CountryDto> countryDtos = countryMapper.toDto(countries);

        List<Long> countryIds = countries.stream().map(Country::getId).toList();
        List<Long> countryDtoIds = countryDtos.stream().map(CountryDto::id).toList();
        List<String> countryNames = countries.stream().map(Country::getCountryName).toList();
        List<String> countryDtoNames = countryDtos.stream().map(CountryDto::countryName).toList();

        assertEquals(countryIds, countryDtoIds);
        assertEquals(countryNames, countryDtoNames);
    }
}
