package by.itacademy.profiler.usecasses.mapper;

import by.itacademy.profiler.persistence.model.Award;
import by.itacademy.profiler.usecasses.dto.AwardDto;
import by.itacademy.profiler.util.AdditionalInfoTestData;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AwardMapperTest {

    private final AwardMapper awardMapper = Mappers.getMapper(AwardMapper.class);


    @Test
    void shouldMapCorrectlyAllFieldsWhenInvokeFromDtoToEntity() {
        AwardDto requestDto = AdditionalInfoTestData.createAwardDto().build();
        Award entity = awardMapper.fromDtoToEntity(requestDto);

        assertEquals(requestDto.date(), entity.getDate());
        assertEquals(requestDto.description(), entity.getDescription());
        assertEquals(requestDto.title(), entity.getTitle());
        assertEquals(requestDto.issuer(), entity.getIssuer());
        assertEquals(requestDto.link(), entity.getLink());
    }

    @Test
    void shouldMapCorrectlyAllFieldsWhenInvokeFromEntityToDto() {
        Award entity = AdditionalInfoTestData.createAwardEntity().build();
        AwardDto responseDto = awardMapper.fromEntityToDto(entity);

        assertEquals(entity.getDate(), responseDto.date());
        assertEquals(entity.getDescription(), responseDto.description());
        assertEquals(entity.getTitle(), responseDto.title());
        assertEquals(entity.getIssuer(), responseDto.issuer());
        assertEquals(entity.getLink(), responseDto.link());
    }
}
