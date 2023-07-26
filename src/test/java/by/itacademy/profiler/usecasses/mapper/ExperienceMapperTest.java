package by.itacademy.profiler.usecasses.mapper;

import by.itacademy.profiler.persistence.model.Experience;
import by.itacademy.profiler.persistence.model.Industry;
import by.itacademy.profiler.usecasses.IndustryService;
import by.itacademy.profiler.usecasses.dto.ExperienceRequestDto;
import by.itacademy.profiler.usecasses.dto.ExperienceResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.YearMonth;
import java.util.List;

import static by.itacademy.profiler.util.ExperienceTestData.createExperience;
import static by.itacademy.profiler.util.ExperienceTestData.createExperienceRequestDto;
import static by.itacademy.profiler.util.IndustryTestData.createIndustry;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExperienceMapperTest {

    @InjectMocks
    private final ExperienceMapper experienceMapper = Mappers.getMapper(ExperienceMapper.class);

    @Mock
    private IndustryService industryService;

    @Test
    void shouldMapPeriodFromCorrectlyWhenInvokeFromDtoToEntity() {
        YearMonth yearMonth = YearMonth.of(2020, 11);
        ExperienceRequestDto experienceRequestDto = createExperienceRequestDto().withPeriodFrom(yearMonth).build();

        when(industryService.getIndustryById(1L)).thenReturn(createIndustry().build());
        Experience experience = experienceMapper.fromDtoToEntity(experienceRequestDto);
        assertEquals(yearMonth, experience.getPeriodFrom());
    }

    @Test
    void shouldMapPeriodToCorrectlyWhenInvokeFromDtoToEntity() {
        YearMonth yearMonth = YearMonth.of(2021, 2);
        ExperienceRequestDto experienceRequestDto = createExperienceRequestDto().withPeriodFrom(yearMonth).build();

        when(industryService.getIndustryById(1L)).thenReturn(createIndustry().build());
        Experience experience = experienceMapper.fromDtoToEntity(experienceRequestDto);
        assertEquals(yearMonth, experience.getPeriodFrom());
    }

    @Test
    void shouldMapIndustryCorrectlyWhenInvokeFromDtoToEntity() {
        Long sphereId = 2L;
        Industry industry = createIndustry().withId(sphereId).withName("Engineering").build();
        ExperienceRequestDto experienceRequestDto = createExperienceRequestDto().withIndustryId(sphereId).build();

        when(industryService.getIndustryById(sphereId)).thenReturn(industry);

        Experience experience = experienceMapper.fromDtoToEntity(experienceRequestDto);
        assertEquals(industry, experience.getIndustry());
    }

    @Test
    void shouldMapCompanyCorrectlyWhenInvokeFromDtoToEntity() {
        String company = "Other company";
        ExperienceRequestDto experienceRequestDto = createExperienceRequestDto().withCompany(company).build();
        when(industryService.getIndustryById(1L)).thenReturn(createIndustry().build());

        Experience experience = experienceMapper.fromDtoToEntity(experienceRequestDto);
        assertEquals(company, experience.getCompany());
    }

    @Test
    void shouldMapPositionCorrectlyWhenInvokeFromDtoToEntity() {
        String position = "Other position";
        ExperienceRequestDto experienceRequestDto = createExperienceRequestDto().withPosition(position).build();
        when(industryService.getIndustryById(1L)).thenReturn(createIndustry().build());

        Experience experience = experienceMapper.fromDtoToEntity(experienceRequestDto);
        assertEquals(position, experience.getPosition());
    }

    @Test
    void shouldMapDutiesCorrectlyWhenInvokeFromDtoToEntity() {
        List<String> duties = List.of("duty1", "duty2", "duty3");
        ExperienceRequestDto experienceRequestDto = createExperienceRequestDto().withDuties(duties).build();
        when(industryService.getIndustryById(1L)).thenReturn(createIndustry().build());

        Experience experience = experienceMapper.fromDtoToEntity(experienceRequestDto);
        assertEquals(duties, experience.getDuties());
        assertEquals(duties.get(2), experience.getDuties().get(2));
    }

    @Test
    void shouldMapIndustryNameCorrectlyWhenInvokeFromEntityToDto() {
        Experience experience = createExperience().build();

        ExperienceResponseDto experienceResponseDto = experienceMapper.fromEntityToDto(experience);
        assertEquals(experienceResponseDto.industryName(), experience.getIndustry().getName());
    }

    @Test
    void shouldMapIndustryIdCorrectlyWhenInvokeFromEntityToDto() {
        Experience experience = createExperience().build();

        ExperienceResponseDto experienceResponseDto = experienceMapper.fromEntityToDto(experience);
        assertEquals(experienceResponseDto.industryId(), experience.getIndustry().getId());
    }

    @Test
    void shouldMapSequenceNumberCorrectlyWhenInvokeFromEntityToDto() {
        Experience experience = createExperience().build();

        ExperienceResponseDto experienceResponseDto = experienceMapper.fromEntityToDto(experience);
        assertEquals(experienceResponseDto.sequenceNumber(), experience.getSequenceNumber());
    }

    @Test
    void shouldMapPeriodFromCorrectlyWhenInvokeFromEntityToDto() {
        Experience experience = createExperience().build();

        ExperienceResponseDto experienceResponseDto = experienceMapper.fromEntityToDto(experience);
        assertEquals(experienceResponseDto.periodFrom(), experience.getPeriodFrom());
    }

    @Test
    void shouldMapPeriodToCorrectlyWhenInvokeFromEntityToDto() {
        Experience experience = createExperience().build();

        ExperienceResponseDto experienceResponseDto = experienceMapper.fromEntityToDto(experience);
        assertEquals(experienceResponseDto.periodTo(), experience.getPeriodTo());
    }

    @Test
    void shouldMapPresentTimeCorrectlyWhenInvokeFromEntityToDto() {
        Experience experience = createExperience().build();

        ExperienceResponseDto experienceResponseDto = experienceMapper.fromEntityToDto(experience);
        assertEquals(experienceResponseDto.presentTime(), experience.getPresentTime());
    }

    @Test
    void shouldMapCompanyCorrectlyWhenInvokeFromEntityToDto() {
        Experience experience = createExperience().build();

        ExperienceResponseDto experienceResponseDto = experienceMapper.fromEntityToDto(experience);
        assertEquals(experienceResponseDto.company(), experience.getCompany());
    }

    @Test
    void shouldMapPositionCorrectlyWhenInvokeFromEntityToDto() {
        Experience experience = createExperience().build();

        ExperienceResponseDto experienceResponseDto = experienceMapper.fromEntityToDto(experience);
        assertEquals(experienceResponseDto.position(), experience.getPosition());
    }

    @Test
    void shouldMapDutiesCorrectlyWhenInvokeFromEntityToDto() {
        Experience experience = createExperience().build();

        ExperienceResponseDto experienceResponseDto = experienceMapper.fromEntityToDto(experience);
        assertEquals(experienceResponseDto.duties(), experience.getDuties());
        assertEquals(experienceResponseDto.duties().get(0), experience.getDuties().get(0));
    }
}
