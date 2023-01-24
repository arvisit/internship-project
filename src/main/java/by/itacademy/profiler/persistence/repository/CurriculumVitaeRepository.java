package by.itacademy.profiler.persistence.repository;

import by.itacademy.profiler.persistence.model.CurriculumVitae;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CurriculumVitaeRepository extends JpaRepository<CurriculumVitae, Long> {

    @Query("SELECT count (*) from CurriculumVitae c, User u where c.user = u and u.email = :username")
    Long findCountByUsername(@Param("username") String Username);
}
