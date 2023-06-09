package by.itacademy.profiler.usecasses.mapper;

import by.itacademy.profiler.persistence.model.Course;
import by.itacademy.profiler.persistence.model.MainEducation;
import by.itacademy.profiler.usecasses.dto.EducationResponseDto;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        builder = @Builder(disableBuilder = true),
        uses = {MainEducationMapper.class, CourseMapper.class})
public interface EducationMapper {

    EducationResponseDto fromEntitiesToDto(List<MainEducation> mainEducations, List<Course> courses);
}
