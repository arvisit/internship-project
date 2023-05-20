package by.itacademy.profiler.usecasses.mapper;

import by.itacademy.profiler.persistence.model.Sphere;
import by.itacademy.profiler.usecasses.dto.SphereResponseDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        builder = @Builder(disableBuilder = true))
public interface SphereMapper {

    SphereResponseDto fromEntityToDto(Sphere sphere);

}
