package by.itacademy.profiler.usecasses.mapper;

import by.itacademy.profiler.persistence.model.AdditionalInformation;
import by.itacademy.profiler.usecasses.dto.AdditionalInformationRequestDto;
import by.itacademy.profiler.usecasses.dto.AdditionalInformationResponseDto;
import by.itacademy.profiler.util.AdditionalInfoTestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AdditionalInformationMapperTest {
    @InjectMocks
    private final AdditionalInformationMapper additionalInformationMapper = Mappers.getMapper(AdditionalInformationMapper.class);

    @Mock
    private AwardMapper awardMapper;

    @Test
    void shouldMapCorrectlyAllFieldsWhenInvokeFromDtoToEntity() {
        AdditionalInformationRequestDto requestDto = AdditionalInfoTestData.createAdditionalInformationRequestDto().build();
        AdditionalInformation entity = additionalInformationMapper.fromDtoToEntity(requestDto);

        assertEquals(requestDto.additionalInfo(),entity.getAdditionalInfo());
        assertEquals(requestDto.hobby(),entity.getHobby());
    }

    @Test
    void shouldMapCorrectlyAllFieldsWhenInvokeFromEntityToDto() {
        AdditionalInformation entity = AdditionalInfoTestData.createAdditionalInformationEntity().build();
        AdditionalInformationResponseDto responseDto = additionalInformationMapper.fromEntityToDto(entity);

        assertEquals(entity.getAdditionalInfo(),responseDto.additionalInfo());
        assertEquals(entity.getHobby(),responseDto.hobby());
    }
}