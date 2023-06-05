package by.itacademy.profiler.usecasses.mapper;

import by.itacademy.profiler.persistence.model.Institution;
import by.itacademy.profiler.usecasses.dto.InstitutionResponseDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        builder = @Builder(disableBuilder = true))
public interface InstitutionMapper {
    InstitutionResponseDto fromEntityToDto(Institution institution);
}
