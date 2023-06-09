package by.itacademy.profiler.usecasses.mapper;

import by.itacademy.profiler.persistence.model.MainEducation;
import by.itacademy.profiler.usecasses.dto.MainEducationRequestDto;
import by.itacademy.profiler.usecasses.dto.MainEducationResponseDto;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static by.itacademy.profiler.util.MainEducationTestData.createMainEducation;
import static by.itacademy.profiler.util.MainEducationTestData.createMainEducationRequestDto;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MainEducationMapperTest {

    private final MainEducationMapper mainEducationMapper = Mappers.getMapper(MainEducationMapper.class);

    @Test
    void shouldMapCorrectlyAllFieldsWhenInvokeFromEntityToDto() {
        MainEducation entity = createMainEducation().build();
        MainEducationResponseDto responseDto = mainEducationMapper.fromEntityToDto(entity);

        assertEquals(entity.getSequenceNumber(), responseDto.sequenceNumber());
        assertEquals(entity.getPeriodFrom(), responseDto.periodFrom());
        assertEquals(entity.getPeriodTo(), responseDto.periodTo());
        assertEquals(entity.getPresentTime(), responseDto.presentTime());
        assertEquals(entity.getInstitution(), responseDto.institution());
        assertEquals(entity.getFaculty(), responseDto.faculty());
        assertEquals(entity.getSpecialty(), responseDto.specialty());
    }

    @Test
    void shouldMapCorrectlyAllFieldsWhenInvokeFromDtoToEntity() {
        MainEducationRequestDto requestDto = createMainEducationRequestDto().build();
        MainEducation entity = mainEducationMapper.fromDtoToEntity(requestDto);

        assertEquals(requestDto.sequenceNumber(), entity.getSequenceNumber());
        assertEquals(requestDto.periodFrom(), entity.getPeriodFrom());
        assertEquals(requestDto.periodTo(), entity.getPeriodTo());
        assertEquals(requestDto.presentTime(), entity.getPresentTime());
        assertEquals(requestDto.institution(), entity.getInstitution());
        assertEquals(requestDto.faculty(), entity.getFaculty());
        assertEquals(requestDto.specialty(), entity.getSpecialty());
    }
}
