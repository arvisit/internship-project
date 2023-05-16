package by.itacademy.profiler.usecasses.annotation;

import by.itacademy.profiler.usecasses.SkillService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class SkillsListValidator implements ConstraintValidator<SkillsListValidation, List<Long>> {

    private final SkillService skillsService;

    @Override
    public boolean isValid(List<Long> skillsId, ConstraintValidatorContext context) {
        if (skillsId == null || skillsId.isEmpty()) {
            return true;
        }
        return skillsService.isSkillsExistByIds(skillsId);
    }
}
