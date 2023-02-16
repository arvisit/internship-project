package by.itacademy.profiler.usecasses.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link by.itacademy.profiler.persistence.model.About} entity
 */
public record AboutDto(@Length(max = 450, message = "Description is too long, the max number of symbols is 450")
                       @NotBlank(message = "Field must not be empty")
                       String description,
                       @URL
                       @Length(max = 255)
                       String selfPresentation) implements Serializable {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AboutDto aboutDto = (AboutDto) o;
        return Objects.equals(description, aboutDto.description) && Objects.equals(selfPresentation, aboutDto.selfPresentation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, selfPresentation);
    }
}