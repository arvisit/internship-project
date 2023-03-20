package by.itacademy.profiler.usecasses.impl;

import by.itacademy.profiler.persistence.model.Country;
import by.itacademy.profiler.persistence.repository.CountryRepository;
import by.itacademy.profiler.usecasses.CountryService;
import by.itacademy.profiler.usecasses.dto.CountryDto;
import by.itacademy.profiler.usecasses.mapper.CountryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {

    public static final String COUNTRY_NAME = "countryName";
    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;

    @Override
    public List<CountryDto> getCountries() {
        List<Country> countries = countryRepository.findAll(Sort.by(Sort.Order.asc(COUNTRY_NAME)));
        log.debug("Getting {} countries from Database", countries.size());
        return countryMapper.toDto(countries);
    }

    @Override
    public boolean isCountryExist(Long id) {
        return countryRepository.existsById(id);
    }
}
