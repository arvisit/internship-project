package by.itacademy.profiler.usecasses.annotation;

import by.itacademy.profiler.usecasses.LanguageService;
import by.itacademy.profiler.usecasses.dto.CvLanguageRequestDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class LanguageListValidator implements ConstraintValidator<LanguageListValidation, List<CvLanguageRequestDto>> {

    private final LanguageService languageService;

    @Override
    public boolean isValid(List<CvLanguageRequestDto> languagesDto, ConstraintValidatorContext context) {
        if (languagesDto == null || languagesDto.isEmpty()) {
            return true;
        }
        List<Long> languagesId = languagesDto.stream()
                .map(CvLanguageRequestDto::id)
                .toList();
        return languageService.isLanguagesExistByIds(languagesId);
    }
}
