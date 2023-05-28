package by.itacademy.profiler.usecasses.mapper;

import by.itacademy.profiler.persistence.model.Position;
import by.itacademy.profiler.usecasses.dto.PositionDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        builder = @Builder(disableBuilder = true))
public interface PositionMapper {

    List<PositionDto> toListDto(List<Position> positions);
}

