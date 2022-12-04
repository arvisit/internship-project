package by.itacademy.profiler.persistence.model;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "roles")
public record Role(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column
        Long id,

        @Column
        @Enumerated(EnumType.STRING)
        RoleNameEnum name) {
}