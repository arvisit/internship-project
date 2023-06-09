package by.itacademy.profiler.usecasses.mapper;

import by.itacademy.profiler.persistence.model.Course;
import by.itacademy.profiler.persistence.model.MainEducation;
import by.itacademy.profiler.usecasses.dto.EducationResponseDto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static by.itacademy.profiler.util.CourseTestData.createCourse;
import static by.itacademy.profiler.util.MainEducationTestData.createMainEducation;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class EducationMapperTest {

    @InjectMocks
    private final EducationMapper educationMapper = Mappers.getMapper(EducationMapper.class);

    @Mock
    private MainEducationMapper mainEducationMapper;

    @Mock
    private CourseMapper courseMapper;

    @Test
    void shouldMapCorrectlyListsWhenInvokeFromEntitiesToDto() {
        List<MainEducation> mainEducations = List.of(createMainEducation().build());
        List<Course> courses = List.of(createCourse().build());

        EducationResponseDto educations = educationMapper.fromEntitiesToDto(mainEducations, courses);

        assertEquals(mainEducations.size(), educations.mainEducations().size());
        assertEquals(courses.size(), educations.courses().size());
    }

}
