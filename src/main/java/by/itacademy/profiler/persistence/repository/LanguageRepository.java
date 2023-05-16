package by.itacademy.profiler.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import by.itacademy.profiler.persistence.model.Language;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LanguageRepository extends JpaRepository<Language, Long> {

    List<Language> findAllByOrderByName();

    @Query("SELECT (count(l)=?2) FROM Language l where l.id IN (?1)")
    Boolean existsAllByIds(List<Long> languageIds, Integer size);

    Language getLanguageById(Long languageId);

}
