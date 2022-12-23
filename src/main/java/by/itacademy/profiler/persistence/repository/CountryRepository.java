package by.itacademy.profiler.persistence.repository;

import by.itacademy.profiler.persistence.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
}
