package by.itacademy.profiler.util;

import by.itacademy.profiler.usecasses.dto.EducationRequestDto;
import by.itacademy.profiler.usecasses.dto.EducationResponseDto;

import java.util.List;

import static by.itacademy.profiler.util.CourseTestData.createCourseRequestDto;
import static by.itacademy.profiler.util.CourseTestData.createCourseResponseDto;
import static by.itacademy.profiler.util.MainEducationTestData.createMainEducationRequestDto;
import static by.itacademy.profiler.util.MainEducationTestData.createMainEducationResponseDto;

public final class EducationTestData {

    public static final String CV_UUID_FOR_EDUCATIONS = "123e4567-e89b-12d3-a456-426614174001";
    public static final String CV_EDUCATIONS_URL_TEMPLATE = "/api/v1/cvs/{uuid}/educations";
    
    private EducationTestData() {};

    public static EducationRequestDto.EducationRequestDtoBuilder createEducationRequestDto() {
        return EducationRequestDto.builder()
                .withMainEducations(List.of(createMainEducationRequestDto().build()))
                .withCourses(List.of(createCourseRequestDto().build()));
    }

    public static EducationResponseDto.EducationResponseDtoBuilder createEducationResponseDto() {
        return EducationResponseDto.builder()
                .withMainEducations(List.of(createMainEducationResponseDto().build()))
                .withCourses(List.of(createCourseResponseDto().build()));
    }
}
