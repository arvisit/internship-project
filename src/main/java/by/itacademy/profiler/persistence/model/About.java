package by.itacademy.profiler.persistence.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Entity
@Table
public class About {
    @Id
    @Column(name = "cv_id", nullable = false)
    private Long id;

    @Column(nullable = false, length = 450)
    private String description;

    @Column(name = "self_presentation")
    private String selfPresentation;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        About about = (About) o;
        return Objects.equals(id, about.id) && Objects.equals(description, about.description) && Objects.equals(selfPresentation, about.selfPresentation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, selfPresentation);
    }

    @Override
    public String toString() {
        return "About{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", selfPresentation='" + selfPresentation + '\'' +
                '}';
    }
}