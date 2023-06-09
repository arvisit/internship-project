package by.itacademy.profiler.util;

import by.itacademy.profiler.persistence.model.Course;
import by.itacademy.profiler.usecasses.dto.CourseRequestDto;
import by.itacademy.profiler.usecasses.dto.CourseResponseDto;

import java.time.YearMonth;

public final class CourseTestData {

    private static final YearMonth DEFAULT_END_YEAR_MONTH = YearMonth.of(2020, 5);
    private static final YearMonth DEFAULT_START_YEAR_MONTH = YearMonth.of(2020, 1);
    private static final String DEFAULT_SCHOOL_NAME = "School Name";
    private static final String DEFAULT_COURSE_NAME = "Course Name";
    private static final String DEFAULT_DESCRIPTION = "Descriptive description";
    private static final String DEFAULT_CERTIFICATE_URL = "http://example.com/link";

    private CourseTestData() {}

    public static Course.CourseBuilder createCourse() {
        return Course.builder()
                .withId(1L)
                .withSequenceNumber(1)
                .withPeriodFrom(DEFAULT_START_YEAR_MONTH)
                .withPeriodTo(DEFAULT_END_YEAR_MONTH)
                .withPresentTime(false)
                .withSchool(DEFAULT_SCHOOL_NAME)
                .withCourseName(DEFAULT_COURSE_NAME)
                .withDescription(DEFAULT_DESCRIPTION)
                .withCertificateUrl(DEFAULT_CERTIFICATE_URL);
    }

    public static CourseResponseDto.CourseResponseDtoBuilder createCourseResponseDto() {
        return CourseResponseDto.builder()
                .withSequenceNumber(1)
                .withPeriodFrom(DEFAULT_START_YEAR_MONTH)
                .withPeriodTo(DEFAULT_END_YEAR_MONTH)
                .withPresentTime(false)
                .withSchool(DEFAULT_SCHOOL_NAME)
                .withCourseName(DEFAULT_COURSE_NAME)
                .withDescription(DEFAULT_DESCRIPTION)
                .withCertificateUrl(DEFAULT_CERTIFICATE_URL);
    }

    public static CourseRequestDto.CourseRequestDtoBuilder createCourseRequestDto() {
        return CourseRequestDto.builder()
                .withSequenceNumber(1)
                .withPeriodFrom(DEFAULT_START_YEAR_MONTH)
                .withPeriodTo(DEFAULT_END_YEAR_MONTH)
                .withPresentTime(false)
                .withSchool(DEFAULT_SCHOOL_NAME)
                .withCourseName(DEFAULT_COURSE_NAME)
                .withDescription(DEFAULT_DESCRIPTION)
                .withCertificateUrl(DEFAULT_CERTIFICATE_URL);
    }
}
