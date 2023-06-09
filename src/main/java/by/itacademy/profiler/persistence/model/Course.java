package by.itacademy.profiler.persistence.model;

import by.itacademy.profiler.persistence.util.YearMonthDateAttributeConverter;

import java.time.YearMonth;
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
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "sequence_number", nullable = false)
    private Integer sequenceNumber;

    @Convert(converter = YearMonthDateAttributeConverter.class)
    @Column(name = "period_from", nullable = false, columnDefinition = "date")
    private YearMonth periodFrom;

    @Convert(converter = YearMonthDateAttributeConverter.class)
    @Column(name = "period_to", columnDefinition = "date")
    private YearMonth periodTo;

    @Column(name = "present_time", nullable = false)
    private Boolean presentTime;

    @Column(name = "school", nullable = false, length = 40)
    private String school;

    @Column(name = "course_name", nullable = false, length = 40)
    private String courseName;

    @Column(name = "description", length = 130)
    private String description;

    @Column(name = "certificate_url", length = 255)
    private String certificateUrl;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course other = (Course) o;
        return Objects.equals(certificateUrl, other.certificateUrl) && Objects.equals(courseName, other.courseName)
                && Objects.equals(description, other.description) && Objects.equals(id, other.id)
                && Objects.equals(periodFrom, other.periodFrom) && Objects.equals(periodTo, other.periodTo)
                && Objects.equals(presentTime, other.presentTime) && Objects.equals(school, other.school)
                && Objects.equals(sequenceNumber, other.sequenceNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(certificateUrl, courseName, description, id, periodFrom, periodTo, presentTime, school,
                sequenceNumber);
    }

    @Override
    public String toString() {
        return "Course{id=" + id +
                ", sequenceNumber=" + sequenceNumber +
                ", periodFrom=" + periodFrom +
                ", periodTo=" + periodTo +
                ", presentTime=" + presentTime +
                ", school='" + school + '\'' +
                ", courseName='" + courseName + '\'' +
                ", description='" + description + '\'' +
                ", certificateUrl='" + certificateUrl + '\'' +
                '}';
    }

}
