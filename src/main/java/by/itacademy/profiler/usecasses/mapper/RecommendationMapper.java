package by.itacademy.profiler.usecasses.mapper;

import by.itacademy.profiler.persistence.model.Recommendation;
import by.itacademy.profiler.usecasses.PhoneCodeService;
import by.itacademy.profiler.usecasses.dto.RecommendationRequestDto;
import by.itacademy.profiler.usecasses.dto.RecommendationResponseDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        builder = @Builder(disableBuilder = true))
public abstract class RecommendationMapper {

    @Autowired
    protected PhoneCodeService phoneCodeService;

    @Mapping(target = "phoneCodeId", source = "recommendation.phoneCode.id")
    @Mapping(target = "phoneCode", source = "recommendation.phoneCode.code")
    public abstract RecommendationResponseDto fromEntityToDto(Recommendation recommendation);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "phoneCode", expression = "java(phoneCodeService.getPhoneCodeById(recommendationRequestDto.phoneCodeId()))")
    public abstract Recommendation fromDtoToEntity(RecommendationRequestDto recommendationRequestDto);

}
