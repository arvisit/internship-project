package by.itacademy.profiler.usecasses.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link by.itacademy.profiler.persistence.model.Country} entity
 */
public record CountryDto(Long id, String countryName) implements Serializable {
}