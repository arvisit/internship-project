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
}