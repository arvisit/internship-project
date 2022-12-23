package by.itacademy.profiler.usecasses.mapper;

import by.itacademy.profiler.persistence.model.Country;
import by.itacademy.profiler.usecasses.dto.CountryDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CountryMapper {

    List<CountryDto> toDto(List<Country> countries);
}
