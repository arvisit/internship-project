package by.itacademy.profiler.persistence.repository;

import by.itacademy.profiler.persistence.model.About;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AboutRepository extends JpaRepository<About, Long> {
}