package by.itacademy.profiler.usecasses.mapper;

import by.itacademy.profiler.persistence.model.Skill;
import by.itacademy.profiler.usecasses.dto.SkillResponseDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, builder = @Builder(disableBuilder = true))
public interface SkillMapper {

    SkillResponseDto fromEntityToDto(Skill skill);

}
