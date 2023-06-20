package by.itacademy.profiler.usecasses.mapper;

import by.itacademy.profiler.persistence.model.AdditionalInformation;
import by.itacademy.profiler.usecasses.dto.AdditionalInformationRequestDto;
import by.itacademy.profiler.usecasses.dto.AdditionalInformationResponseDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        builder = @Builder(disableBuilder = true),
        uses = AwardMapper.class)
public interface AdditionalInformationMapper {

    AdditionalInformation fromDtoToEntity(AdditionalInformationRequestDto requestDto);

    AdditionalInformationResponseDto fromEntityToDto(AdditionalInformation entity);
}
