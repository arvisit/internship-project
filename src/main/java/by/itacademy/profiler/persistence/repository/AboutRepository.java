package by.itacademy.profiler.persistence.repository;

import by.itacademy.profiler.persistence.model.About;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AboutRepository extends JpaRepository<About, Long> {

    @Query("select c.about from CurriculumVitae c where c.uuid = :uuid and c.user.email = :username")
    Optional<About> findByUuidAndUsername(@Param("uuid") String uuid, @Param("username") String username);
}