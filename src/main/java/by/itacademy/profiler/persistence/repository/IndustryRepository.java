package by.itacademy.profiler.persistence.repository;

import by.itacademy.profiler.persistence.model.Industry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IndustryRepository extends JpaRepository<Industry, Long> {

    List<Industry> findAllByOrderById();

    Industry getIndustryById(Long industryId);

}
