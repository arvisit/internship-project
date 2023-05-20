package by.itacademy.profiler.persistence.repository;

import by.itacademy.profiler.persistence.model.Sphere;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SphereRepository extends JpaRepository<Sphere, Long> {

    List<Sphere> findAllByOrderById();

}
