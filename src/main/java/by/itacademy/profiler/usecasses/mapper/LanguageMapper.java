package by.itacademy.profiler.usecasses.mapper;

import by.itacademy.profiler.persistence.model.Language;
import by.itacademy.profiler.usecasses.dto.LanguageResponseDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        builder = @Builder(disableBuilder = true))
public interface LanguageMapper {

    LanguageResponseDto fromEntityToDto(Language language);

}
