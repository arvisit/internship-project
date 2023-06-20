package by.itacademy.profiler.usecasses.dto;

import lombok.Builder;

import java.util.List;

@Builder(setterPrefix = "with")
public record AdditionalInformationResponseDto(
        String additionalInfo,

        String hobby,

        List<AwardDto> awards
) {
}
