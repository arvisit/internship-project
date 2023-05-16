package by.itacademy.profiler.usecasses.impl;

import by.itacademy.profiler.persistence.model.Skill;
import by.itacademy.profiler.persistence.repository.SkillRepository;
import by.itacademy.profiler.usecasses.SkillService;
import by.itacademy.profiler.usecasses.dto.SkillResponseDto;
import by.itacademy.profiler.usecasses.mapper.SkillMapper;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SkillServiceImpl implements SkillService {

    private final SkillRepository skillRepository;
    private final SkillMapper skillMapper;

    @Override
    @Transactional(readOnly = true)
    public List<SkillResponseDto> getSkills(@Nullable Long positionId) {

        List<Skill> skills;

        if (positionId == null) {
            skills = skillRepository.findAllByOrderByName();
        } else {
            skills = skillRepository.findSkillsByPositionsIdOrderByName(positionId);
        }

        return skills.stream()
                .map(skillMapper::fromEntityToDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isSkillsExistByIds(List<Long> skillIds) {
        return skillRepository.existsAllByIds(skillIds, skillIds.size());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Skill> getListOfSkillsByIds(List<Long> skillIds) {
        return skillRepository.findAllById(skillIds);
    }
}
