package by.itacademy.profiler.persistence.repository;

import by.itacademy.profiler.persistence.model.CurriculumVitae;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CurriculumVitaeRepository extends JpaRepository<CurriculumVitae, Long> {

    @Query("SELECT count (*) from CurriculumVitae c, User u where c.user = u and u.email = :username")
    Long findCountByUsername(@Param("username") String username);

    @Query("SELECT c from CurriculumVitae c, User u where c.user.id = u.id and u.email = :username")
    List<CurriculumVitae> findByUsername(@Param("username") String username);

    @Query("SELECT c FROM CurriculumVitae c WHERE c.uuid = :uuid AND c.user.email = :username")
    CurriculumVitae findByUuidAndUsername(@Param("uuid") String uuid, @Param("username") String username);

    @Query("select (count(c) > 0) from CurriculumVitae c where c.uuid = :uuid and c.user.email = :email")
    boolean existsByUuidAndUsername(@Param("uuid") String uuid, @Param("email") String email);
}
