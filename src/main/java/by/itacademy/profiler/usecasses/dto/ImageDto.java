package by.itacademy.profiler.usecasses.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link by.itacademy.profiler.persistence.model.Image} entity
 */
public record ImageDto(String uuid) implements Serializable {
}