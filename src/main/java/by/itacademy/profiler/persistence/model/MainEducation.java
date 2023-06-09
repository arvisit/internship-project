package by.itacademy.profiler.persistence.model;

import by.itacademy.profiler.persistence.util.YearDateAttributeConverter;

import java.time.Year;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder(setterPrefix = "with")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "main_educations")
public class MainEducation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "sequence_number", nullable = false)
    private Integer sequenceNumber;

    @Convert(converter = YearDateAttributeConverter.class)
    @Column(name = "period_from", nullable = false, columnDefinition = "date")
    private Year periodFrom;

    @Convert(converter = YearDateAttributeConverter.class)
    @Column(name = "period_to", columnDefinition = "date")
    private Year periodTo;

    @Column(name = "present_time", nullable = false)
    private Boolean presentTime;

    @Column(name = "institution", nullable = false, length = 100)
    private String institution;

    @Column(name = "faculty", length = 40)
    private String faculty;

    @Column(name = "specialty", nullable = false, length = 100)
    private String specialty;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MainEducation other = (MainEducation) o;
        return Objects.equals(faculty, other.faculty) && Objects.equals(id, other.id)
                && Objects.equals(institution, other.institution) && Objects.equals(periodFrom, other.periodFrom)
                && Objects.equals(periodTo, other.periodTo) && Objects.equals(presentTime, other.presentTime)
                && Objects.equals(sequenceNumber, other.sequenceNumber) && Objects.equals(specialty, other.specialty);
    }

    @Override
    public int hashCode() {
        return Objects.hash(faculty, id, institution, periodFrom, periodTo, presentTime, sequenceNumber, specialty);
    }

    @Override
    public String toString() {
        return "MainEducation{id=" + id +
                ", sequenceNumber=" + sequenceNumber +
                ", periodFrom=" + periodFrom +
                ", periodTo=" + periodTo +
                ", presentTime=" + presentTime +
                ", institution='" + institution + '\'' +
                ", faculty='" + faculty + '\'' +
                ", specialty='" + specialty + '\'' +
                '}';
    }

}
