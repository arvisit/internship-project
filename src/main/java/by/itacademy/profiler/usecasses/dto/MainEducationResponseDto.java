package by.itacademy.profiler.usecasses.dto;

import java.io.Serializable;
import java.time.Year;

import lombok.Builder;

@Builder(setterPrefix = "with")
public record MainEducationResponseDto(
        Integer sequenceNumber,
        Year periodFrom, Year periodTo,
        Boolean presentTime,
        String institution, String faculty,
        String specialty
) implements Serializable {
}
