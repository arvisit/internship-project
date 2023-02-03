package by.itacademy.profiler.persistence.repository;

import by.itacademy.profiler.persistence.model.Contacts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactsRepository extends JpaRepository<Contacts, Long> {
}