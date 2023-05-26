package by.itacademy.profiler.usecasses.mapper;

import by.itacademy.profiler.persistence.model.Industry;
import by.itacademy.profiler.usecasses.dto.IndustryResponseDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        builder = @Builder(disableBuilder = true))
public interface IndustryMapper {

    IndustryResponseDto fromEntityToDto(Industry industry);

}
