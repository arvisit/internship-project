package by.itacademy.profiler.usecasses.mapper;

import by.itacademy.profiler.persistence.model.PhoneCode;
import by.itacademy.profiler.usecasses.dto.PhoneCodeDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PhoneCodeMapper {

    List<PhoneCodeDto> toDto(List<PhoneCode> phoneCodes);
}
