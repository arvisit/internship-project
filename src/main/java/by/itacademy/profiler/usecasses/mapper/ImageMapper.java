package by.itacademy.profiler.usecasses.mapper;

import by.itacademy.profiler.persistence.model.Image;
import by.itacademy.profiler.usecasses.dto.ImageDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ImageMapper {
    ImageDto imageToImageDto(Image image);
}