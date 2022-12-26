package by.itacademy.profiler.usecasses.mapper;

import by.itacademy.profiler.persistence.model.Position;
import by.itacademy.profiler.usecasses.dto.PositionDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PositionMapper {

    List<PositionDto> toListDto(List<Position> positions);
}

