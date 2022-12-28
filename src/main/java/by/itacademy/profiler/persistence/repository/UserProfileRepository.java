package by.itacademy.profiler.persistence.repository;

import by.itacademy.profiler.persistence.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
}