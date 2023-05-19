package by.itacademy.profiler.api.controllers;

import by.itacademy.profiler.usecasses.CountryService;
import by.itacademy.profiler.usecasses.dto.CountryDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@Tag(name = "Country Controller", description = "API for working with countries")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/countries")
public class CountryApiController {

    private final CountryService countryService;
    @Operation(summary = "Get list of countries")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CountryDto> getCountries() {
        return countryService.getCountries();
    }
}
