package by.itacademy.profiler.usecasses;

import by.itacademy.profiler.usecasses.dto.CountryDto;

import java.util.List;

public interface CountryService {
    List<CountryDto> getCountries();
    boolean isCountryExist(Long id);
}
