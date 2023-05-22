package by.itacademy.profiler.persistence.repository;

import by.itacademy.profiler.persistence.model.Experience;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExperienceRepository extends JpaRepository<Experience, Long> {
}
