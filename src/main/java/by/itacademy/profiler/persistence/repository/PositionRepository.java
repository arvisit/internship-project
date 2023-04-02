package by.itacademy.profiler.persistence.repository;

import by.itacademy.profiler.persistence.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PositionRepository extends JpaRepository<Position, Long> {
}
