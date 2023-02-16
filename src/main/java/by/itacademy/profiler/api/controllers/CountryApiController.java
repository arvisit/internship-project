package by.itacademy.profiler.api.controllers;

import by.itacademy.profiler.usecasses.CountryService;
import by.itacademy.profiler.usecasses.dto.CountryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/countries")
public class CountryApiController {

    private final CountryService countryService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CountryDto> getCountries() {
        return countryService.getCountries();
    }
}
