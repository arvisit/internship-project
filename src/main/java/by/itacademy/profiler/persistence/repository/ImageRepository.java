package by.itacademy.profiler.persistence.repository;

import by.itacademy.profiler.persistence.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {

    Optional<Image> findByUuid(String uuid);

    @Query("SELECT i from Image i where i.uuid = :uuid and i.user.email = :username")
    Image findByUuidAndUsername(@Param("uuid") String uuid, @Param("username") String username);

    @Query("select case when count(c) > 0 then true else false end from CurriculumVitae c where lower(c.image.uuid) like lower(:uuid)")
    boolean isImageBelongCurriculumVitae(@Param("uuid") String uuid);

    @Query("select case when count(u) > 0 then true else false end from UserProfile u where lower(u.profileImage.uuid) like lower(:uuid)")
    boolean isImageBelongUserProfile(@Param("uuid") String uuid);
}