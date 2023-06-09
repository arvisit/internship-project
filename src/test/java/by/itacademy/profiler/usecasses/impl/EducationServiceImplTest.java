package by.itacademy.profiler.usecasses.impl;

import by.itacademy.profiler.persistence.model.Course;
import by.itacademy.profiler.persistence.model.MainEducation;
import by.itacademy.profiler.usecasses.CurriculumVitaeService;
import by.itacademy.profiler.usecasses.dto.CourseRequestDto;
import by.itacademy.profiler.usecasses.dto.EducationRequestDto;
import by.itacademy.profiler.usecasses.dto.EducationResponseDto;
import by.itacademy.profiler.usecasses.dto.MainEducationRequestDto;
import by.itacademy.profiler.usecasses.mapper.CourseMapper;
import by.itacademy.profiler.usecasses.mapper.EducationMapper;
import by.itacademy.profiler.usecasses.mapper.MainEducationMapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static by.itacademy.profiler.util.CourseTestData.createCourse;
import static by.itacademy.profiler.util.CurriculumVitaeTestData.CV_UUID;
import static by.itacademy.profiler.util.EducationTestData.createEducationRequestDto;
import static by.itacademy.profiler.util.EducationTestData.createEducationResponseDto;
import static by.itacademy.profiler.util.MainEducationTestData.createMainEducation;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EducationServiceImplTest {

    @InjectMocks
    private EducationServiceImpl educationService;

    @Mock
    private CurriculumVitaeService curriculumVitaeService;

    @Mock
    private EducationMapper educationMapper;

    @Mock
    private CourseMapper courseMapper;

    @Mock
    private MainEducationMapper mainEducationMapper;

    @Test
    void shouldSaveEducationsToCvWhenInvokeSave() {
        EducationRequestDto educationRequestDto = createEducationRequestDto().build();
        EducationResponseDto educationResponseDto = createEducationResponseDto().build();

        MainEducation mainEducation = createMainEducation().build();
        Course course = createCourse().build();

        when(mainEducationMapper.fromDtoToEntity(any(MainEducationRequestDto.class))).thenReturn(mainEducation);
        when(courseMapper.fromDtoToEntity(any(CourseRequestDto.class))).thenReturn(course);
        when(curriculumVitaeService.saveMainEducationsToCv(anyString(), anyList())).thenReturn(List.of(mainEducation));
        when(curriculumVitaeService.saveCoursesToCv(anyString(), anyList())).thenReturn(List.of(course));
        when(educationMapper.fromEntitiesToDto(anyList(), anyList())).thenReturn(educationResponseDto);

        assertDoesNotThrow(() -> educationService.save(educationRequestDto, CV_UUID));
    }
}
