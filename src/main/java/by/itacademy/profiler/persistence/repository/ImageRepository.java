package by.itacademy.profiler.persistence.repository;

import by.itacademy.profiler.persistence.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findByUuid(String uuid);
}