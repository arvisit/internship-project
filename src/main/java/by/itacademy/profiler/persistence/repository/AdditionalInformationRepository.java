package by.itacademy.profiler.persistence.repository;

import by.itacademy.profiler.persistence.model.AdditionalInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AdditionalInformationRepository extends JpaRepository<AdditionalInformation, Long> {

    @Query("select c.additionalInformation from CurriculumVitae c where c.uuid = :uuid and c.user.email = :username")
    Optional<AdditionalInformation> findByCvUuidAndUsername(@Param("uuid") String cvUuid,
            @Param("username") String username);
}
