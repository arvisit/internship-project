package by.itacademy.profiler.usecasses.mapper;

import by.itacademy.profiler.persistence.model.Institution;
import by.itacademy.profiler.usecasses.dto.InstitutionResponseDto;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static by.itacademy.profiler.util.InstitutionsTestData.*;
import static org.junit.jupiter.api.Assertions.*;

class InstitutionMapperTest {

    private final InstitutionMapper institutionMapper = Mappers.getMapper(InstitutionMapper.class);

    @Test
    void shouldMapCorrectlyAllFieldWhenInvokeFromEntityToDto() {
        Institution institution = createInstitution().build();
        InstitutionResponseDto institutionResponseDto = institutionMapper.fromEntityToDto(institution);

        assertEquals(institution.getId(), institutionResponseDto.id());
        assertEquals(institution.getName(), institutionResponseDto.name());
    }
}