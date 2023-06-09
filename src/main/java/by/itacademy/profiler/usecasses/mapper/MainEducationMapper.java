package by.itacademy.profiler.usecasses.mapper;

import by.itacademy.profiler.persistence.model.MainEducation;
import by.itacademy.profiler.usecasses.dto.MainEducationRequestDto;
import by.itacademy.profiler.usecasses.dto.MainEducationResponseDto;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        builder = @Builder(disableBuilder = true))
public interface MainEducationMapper {

    @Mapping(target = "id", ignore = true)
    MainEducation fromDtoToEntity(MainEducationRequestDto requestDto);

    MainEducationResponseDto fromEntityToDto(MainEducation entity);
}
