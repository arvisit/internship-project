package by.itacademy.profiler.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import by.itacademy.profiler.persistence.model.Language;

import java.util.List;

public interface LanguageRepository extends JpaRepository<Language, Long> {

    List<Language> findAllByOrderByName();

}
