package by.itacademy.profiler.usecasses.mapper;

import by.itacademy.profiler.persistence.model.Country;
import by.itacademy.profiler.persistence.model.PhoneCode;
import by.itacademy.profiler.usecasses.dto.PhoneCodeDto;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

import static by.itacademy.profiler.util.PhoneCodeTestData.createPhoneCode;
import static org.junit.jupiter.api.Assertions.*;

class PhoneCodeMapperTest {

    private final PhoneCodeMapper phoneCodeMapper = Mappers.getMapper(PhoneCodeMapper.class);
    @Test
    void shouldMapCorrectlyAllFieldWhenInvokeFromEntityToDto() {
        List<PhoneCode> phoneCodeList = List.of(createPhoneCode());
        List<PhoneCodeDto> phoneCodeDtoList = phoneCodeMapper.toDto(phoneCodeList);

        assertEquals(phoneCodeList.get(0).getId(),phoneCodeDtoList.get(0).id());
        assertEquals(phoneCodeList.get(0).getCode(),phoneCodeDtoList.get(0).code());
        assertEquals(phoneCodeList.get(0).getCountry(),phoneCodeDtoList.get(0).country());
    }
}