package by.itacademy.profiler.usecasses;

import by.itacademy.profiler.persistence.model.Skill;
import by.itacademy.profiler.usecasses.dto.SkillResponseDto;

import java.util.List;

public interface SkillService {

    List<SkillResponseDto> getSkills(Long positionId);

    boolean isSkillsExistByIds(List<Long> skillIds);

    List<Skill> getListOfSkillsByIds(List<Long> skillIds);

}
