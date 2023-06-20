package by.itacademy.profiler.usecasses.mapper;

import by.itacademy.profiler.persistence.model.Award;
import by.itacademy.profiler.usecasses.dto.AwardDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        builder = @Builder(disableBuilder = true))
public interface AwardMapper {

    Award fromDtoToEntity(AwardDto requestDto);

    AwardDto fromEntityToDto(Award entity);
}
