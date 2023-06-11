package by.itacademy.profiler.usecasses.impl;

import by.itacademy.profiler.persistence.model.Course;
import by.itacademy.profiler.persistence.model.MainEducation;
import by.itacademy.profiler.usecasses.CurriculumVitaeService;
import by.itacademy.profiler.usecasses.EducationService;
import by.itacademy.profiler.usecasses.dto.EducationRequestDto;
import by.itacademy.profiler.usecasses.dto.EducationResponseDto;
import by.itacademy.profiler.usecasses.mapper.CourseMapper;
import by.itacademy.profiler.usecasses.mapper.EducationMapper;
import by.itacademy.profiler.usecasses.mapper.MainEducationMapper;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EducationServiceImpl implements EducationService {

    private final CurriculumVitaeService curriculumVitaeService;
    private final MainEducationMapper mainEducationMapper;
    private final CourseMapper courseMapper;
    private final EducationMapper educationMapper;

    @Override
    @Transactional
    public EducationResponseDto save(EducationRequestDto educationRequestDto, String cvUuid) {
        List<MainEducation> mainEducations = educationRequestDto.mainEducations().stream()
                .map(mainEducationMapper::fromDtoToEntity)
                .toList();
        List<MainEducation> savedMainEducations = curriculumVitaeService.saveMainEducationsToCv(cvUuid, mainEducations);

        List<Course> courses = educationRequestDto.courses().stream()
                .map(courseMapper::fromDtoToEntity)
                .toList();
        List<Course> savedCourses = curriculumVitaeService.saveCoursesToCv(cvUuid, courses);

        return educationMapper.fromEntitiesToDto(savedMainEducations, savedCourses);
    }

    @Override
    @Transactional(readOnly = true)
    public EducationResponseDto getEducationByCvUuid(String cvUuid) {
        List<MainEducation> mainEducations = curriculumVitaeService.getMainEducationsByCvUuid(cvUuid);
        List<Course> courses = curriculumVitaeService.getCoursesByCvUuid(cvUuid);

        return educationMapper.fromEntitiesToDto(mainEducations, courses);
    }
}
