package by.itacademy.profiler.usecasses.mapper;

import by.itacademy.profiler.persistence.model.CvLanguage;
import by.itacademy.profiler.persistence.model.Skill;
import by.itacademy.profiler.usecasses.dto.CompetenceResponseDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        builder = @Builder(disableBuilder = true),
        uses = {SkillMapper.class, CvLanguageMapper.class})
public interface CompetenceMapper {

    CompetenceResponseDto fromEntitiesToDto(List<Skill> skills, List<CvLanguage> languages);

}
