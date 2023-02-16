package by.itacademy.profiler.usecasses.annotation;

import by.itacademy.profiler.api.exception.CurriculumVitaeNotFoundException;
import by.itacademy.profiler.usecasses.CurriculumVitaeService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IsCvExistsValidator implements ConstraintValidator<IsCvExists, String> {

    private final CurriculumVitaeService curriculumVitaeService;

    @Override
    public boolean isValid(String uuid, ConstraintValidatorContext context) {
        if (curriculumVitaeService.isCurriculumVitaeExists(uuid)) {
            return true;
        } else {
            throw new CurriculumVitaeNotFoundException(String.format("CV with UUID %s not found!!!", uuid));
        }
    }
}
