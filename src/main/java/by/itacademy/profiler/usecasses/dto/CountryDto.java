package by.itacademy.profiler.usecasses.dto;

import java.io.Serializable;

import lombok.Builder;

/**
 * A DTO for the {@link by.itacademy.profiler.persistence.model.Country} entity
 */
@Builder(setterPrefix = "with")
public record CountryDto(Long id, String countryName) implements Serializable {
}