package by.itacademy.profiler.persistence.repository;

import by.itacademy.profiler.persistence.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
@Query("SELECT p from UserProfile p,User u where p.id = u.id and u.email = :username")
    Optional<UserProfile> findByUsername (@Param("username") String username);
}