package by.itacademy.profiler.usecasses.dto;

import java.io.Serializable;
import java.time.YearMonth;

import lombok.Builder;

@Builder(setterPrefix = "with")
public record CourseResponseDto(
        Integer sequenceNumber,
        YearMonth periodFrom, YearMonth periodTo,
        Boolean presentTime,
        String school, String courseName,
        String description, String certificateUrl
) implements Serializable {
}
