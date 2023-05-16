package by.itacademy.profiler.usecasses;

import by.itacademy.profiler.persistence.model.Language;
import by.itacademy.profiler.usecasses.dto.LanguageResponseDto;

import java.util.List;

public interface LanguageService {

    List<LanguageResponseDto> getLanguages();

    boolean isLanguagesExistByIds(List<Long> languageIds);

    Language getLanguageById(Long languageId);

}
