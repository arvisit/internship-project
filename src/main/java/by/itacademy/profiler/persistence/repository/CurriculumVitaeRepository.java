package by.itacademy.profiler.persistence.repository;

import by.itacademy.profiler.persistence.model.Course;
import by.itacademy.profiler.persistence.model.CurriculumVitae;
import by.itacademy.profiler.persistence.model.CvLanguage;
import by.itacademy.profiler.persistence.model.Experience;
import by.itacademy.profiler.persistence.model.MainEducation;
import by.itacademy.profiler.persistence.model.Skill;
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

    CurriculumVitae getReferenceByUuid(String uuid);

    @Query("SELECT c.languages FROM CurriculumVitae c WHERE c.uuid = ?1")
    List<CvLanguage> findAllCvLanguagesByCVUuid(String cvUuid);

    @Query("SELECT c.skills FROM CurriculumVitae c WHERE c.uuid = ?1")
    List<Skill> findAllCvSkillsByCvUuid(String cvUuid);

    @Query("SELECT c.experience FROM CurriculumVitae c WHERE c.uuid = ?1")
    List<Experience> findAllCvExperienceByCvUuid(String cvUuid);

    @Query("SELECT c.mainEducations FROM CurriculumVitae c WHERE c.uuid = ?1")
    List<MainEducation> findAllMainEducationsByCVUuid(String uuid);

    @Query("SELECT c.courses FROM CurriculumVitae c WHERE c.uuid = ?1")
    List<Course> findAllCoursesByCVUuid(String uuid);
}
