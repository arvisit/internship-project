package by.itacademy.profiler.usecasses.mapper;

import by.itacademy.profiler.persistence.model.Position;
import by.itacademy.profiler.usecasses.dto.PositionDto;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static by.itacademy.profiler.util.PositionTestData.createPosition;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PositionMapperTest {

    private final PositionMapper positionMapper = Mappers.getMapper(PositionMapper.class);

    @Test
    void shouldMapCorrectlyAllFieldWhenInvokeToListDto() {
        Position position = createPosition().build();
        List<PositionDto> mapperListDto = positionMapper.toListDto(List.of(position));
        assertEquals(position.getName(), mapperListDto.get(0).name());
        assertEquals(position.getId(), mapperListDto.get(0).id());
    }
}
