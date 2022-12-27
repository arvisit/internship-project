package by.itacademy.profiler.usecasses.dto;

import by.itacademy.profiler.persistence.model.Country;

import java.io.Serializable;

public record PhoneCodeDto(Long id, Integer code, Country country) implements Serializable {
}