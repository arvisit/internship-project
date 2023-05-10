package by.itacademy.profiler.usecasses.impl;

import by.itacademy.profiler.persistence.model.Skill;
import by.itacademy.profiler.persistence.repository.SkillRepository;
import by.itacademy.profiler.usecasses.dto.SkillResponseDto;
import by.itacademy.profiler.usecasses.mapper.SkillMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static by.itacademy.profiler.util.SkillTestData.createSkill;
import static by.itacademy.profiler.util.SkillTestData.createSkillResponseDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SkillServiceTest {

    @InjectMocks
    private SkillServiceImpl skillService;

    @Mock
    private SkillMapper skillMapper;

    @Mock
    private SkillRepository skillRepository;

    @Test
    void testGetSkillsWithPositionIdEqualsNullShouldReturnAllSkills() {
        Skill skill = createSkill();
        SkillResponseDto skillResponseDto = createSkillResponseDto().build();

        when(skillRepository.findAllByOrderByName()).thenReturn(List.of(skill));
        when(skillMapper.fromEntityToDto(skill)).thenReturn(skillResponseDto);

        List<SkillResponseDto> skills = skillService.getSkills(null);
        assertEquals(1, skills.size());
    }

    @Test
    void testGetSkillsWithPositionIdShouldReturnSkillsByPosition() {
        Skill skill = createSkill();
        SkillResponseDto skillResponseDto = createSkillResponseDto().build();
        Long positionId = 1L;

        when(skillRepository.findSkillsByPositionsIdOrderByName(positionId)).thenReturn(List.of(skill));
        when(skillMapper.fromEntityToDto(skill)).thenReturn(skillResponseDto);

        List<SkillResponseDto> skills = skillService.getSkills(positionId);
        assertEquals(1L, skills.size());
    }

}
