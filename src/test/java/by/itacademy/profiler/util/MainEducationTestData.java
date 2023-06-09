package by.itacademy.profiler.util;

import by.itacademy.profiler.persistence.model.MainEducation;
import by.itacademy.profiler.usecasses.dto.MainEducationRequestDto;
import by.itacademy.profiler.usecasses.dto.MainEducationResponseDto;

import java.time.Year;

public final class MainEducationTestData {

    private static final Year DEFAULT_END_YEAR = Year.of(2020);
    private static final Year DEFAULT_START_YEAR = Year.of(2015);
    private static final String DEFAULT_INSTITUTION_NAME = "Institution Name";
    private static final String DEFAULT_FACULTY_NAME = "Faculty Name";
    private static final String DEFAULT_SPECIALTY_NAME = "Specialty Name";

    private MainEducationTestData() {}

    public static MainEducation.MainEducationBuilder createMainEducation() {
        return MainEducation.builder()
                .withId(1L)
                .withSequenceNumber(1)
                .withPeriodFrom(DEFAULT_START_YEAR)
                .withPeriodTo(DEFAULT_END_YEAR)
                .withPresentTime(false)
                .withInstitution(DEFAULT_INSTITUTION_NAME)
                .withFaculty(DEFAULT_FACULTY_NAME)
                .withSpecialty(DEFAULT_SPECIALTY_NAME);
    }

    public static MainEducationResponseDto.MainEducationResponseDtoBuilder createMainEducationResponseDto() {
        return MainEducationResponseDto.builder()
                .withSequenceNumber(1)
                .withPeriodFrom(DEFAULT_START_YEAR)
                .withPeriodTo(DEFAULT_END_YEAR)
                .withPresentTime(false)
                .withInstitution(DEFAULT_INSTITUTION_NAME)
                .withFaculty(DEFAULT_FACULTY_NAME)
                .withSpecialty(DEFAULT_SPECIALTY_NAME);
    }

    public static MainEducationRequestDto.MainEducationRequestDtoBuilder createMainEducationRequestDto() {
        return MainEducationRequestDto.builder()
                .withSequenceNumber(1)
                .withPeriodFrom(DEFAULT_START_YEAR)
                .withPeriodTo(DEFAULT_END_YEAR)
                .withPresentTime(false)
                .withInstitution(DEFAULT_INSTITUTION_NAME)
                .withFaculty(DEFAULT_FACULTY_NAME)
                .withSpecialty(DEFAULT_SPECIALTY_NAME);
    }
}
