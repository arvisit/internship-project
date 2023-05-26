package by.itacademy.profiler.usecasses.dto;

import lombok.Builder;

import java.io.Serializable;
import java.time.YearMonth;
import java.util.List;

@Builder(setterPrefix = "with")
public record ExperienceResponseDto(
        Long id, Integer sequenceNumber,
        YearMonth periodFrom, YearMonth periodTo,
        Boolean presentTime,
        Long industryId, String industryName,
        String company, String position,
        List<String> duties,
        String achievements, String link
) implements Serializable {
}
