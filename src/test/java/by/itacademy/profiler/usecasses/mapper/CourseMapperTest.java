package by.itacademy.profiler.usecasses.mapper;

import by.itacademy.profiler.persistence.model.Course;
import by.itacademy.profiler.usecasses.dto.CourseRequestDto;
import by.itacademy.profiler.usecasses.dto.CourseResponseDto;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static by.itacademy.profiler.util.CourseTestData.createCourse;
import static by.itacademy.profiler.util.CourseTestData.createCourseRequestDto;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CourseMapperTest {

    private final CourseMapper courseMapper = Mappers.getMapper(CourseMapper.class);

    @Test
    void shouldMapCorrectlyAllFieldsWhenInvokeFromEntityToDto() {
        Course entity = createCourse().build();
        CourseResponseDto responseDto = courseMapper.fromEntityToDto(entity);

        assertEquals(entity.getSequenceNumber(), responseDto.sequenceNumber());
        assertEquals(entity.getPeriodFrom(), responseDto.periodFrom());
        assertEquals(entity.getPeriodTo(), responseDto.periodTo());
        assertEquals(entity.getPresentTime(), responseDto.presentTime());
        assertEquals(entity.getSchool(), responseDto.school());
        assertEquals(entity.getCourseName(), responseDto.courseName());
        assertEquals(entity.getDescription(), responseDto.description());
        assertEquals(entity.getCertificateUrl(), responseDto.certificateUrl());
    }

    @Test
    void shouldMapCorrectlyAllFieldsWhenInvokeFromDtoToEntity() {
        CourseRequestDto requestDto = createCourseRequestDto().build();
        Course entity = courseMapper.fromDtoToEntity(requestDto);

        assertEquals(requestDto.sequenceNumber(), entity.getSequenceNumber());
        assertEquals(requestDto.periodFrom(), entity.getPeriodFrom());
        assertEquals(requestDto.periodTo(), entity.getPeriodTo());
        assertEquals(requestDto.presentTime(), entity.getPresentTime());
        assertEquals(requestDto.school(), entity.getSchool());
        assertEquals(requestDto.courseName(), entity.getCourseName());
        assertEquals(requestDto.description(), entity.getDescription());
        assertEquals(requestDto.certificateUrl(), entity.getCertificateUrl());
    }
}
