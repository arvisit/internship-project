package by.itacademy.profiler.usecasses;

import by.itacademy.profiler.usecasses.dto.SkillResponseDto;

import java.util.List;

public interface SkillService {

    List<SkillResponseDto> getSkills(Long positionId);

}
