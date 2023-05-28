package by.itacademy.profiler.util;

import by.itacademy.profiler.persistence.model.Position;
import by.itacademy.profiler.usecasses.dto.PositionDto;

public class PositionTestData {

    public static final String POSITION_URL_TEMPLATE = "/api/v1/positions";
    public static final Integer EXPECTED_NUMBER_OF_POSITIONS = 34;

    private PositionTestData() {
    }

    public static PositionDto.PositionDtoBuilder createPositionDto() {
        return PositionDto.builder().withId(1L).withName("Java developer");
    }

    public static Position.PositionBuilder createPosition() {
        return Position.builder().withId(1L).withName("Java developer");
    }
}
