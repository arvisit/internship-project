package by.itacademy.profiler.persistence.repository;

import by.itacademy.profiler.persistence.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PositionRepository extends JpaRepository<Position, Long> {
}
