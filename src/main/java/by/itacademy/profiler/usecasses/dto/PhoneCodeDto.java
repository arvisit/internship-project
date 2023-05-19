package by.itacademy.profiler.usecasses.dto;

import by.itacademy.profiler.persistence.model.Country;
import lombok.Builder;

@Builder(setterPrefix = "with")
public record PhoneCodeDto(Long id, Integer code, Country country) {
}