package by.itacademy.profiler.persistence.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "cvs")
public class CurriculumVitae {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "uuid")
    private String uuid;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "image_id")
    private Image image;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @ManyToOne
    @JoinColumn(name = "position_id")
    private Position position;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @Column(name = "city")
    private String city;

    @Column(name = "is_ready_to_relocate")
    private Boolean isReadyToRelocate;

    @Column(name = "is_ready_for_remote_work")
    private Boolean isReadyForRemoteWork;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CurriculumVitae curriculumVitae)) return false;
        return Objects.equals(id, curriculumVitae.id) && Objects.equals(uuid, curriculumVitae.uuid) &&
                Objects.equals(user, curriculumVitae.user) && Objects.equals(image, curriculumVitae.image) &&
                Objects.equals(name, curriculumVitae.name) && Objects.equals(surname, curriculumVitae.surname) &&
                Objects.equals(position, curriculumVitae.position) && Objects.equals(country, curriculumVitae.country) &&
                Objects.equals(city, curriculumVitae.city) && Objects.equals(isReadyToRelocate, curriculumVitae.isReadyToRelocate) &&
                Objects.equals(isReadyForRemoteWork, curriculumVitae.isReadyForRemoteWork);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, uuid, user, image, name, surname, position,
                country, city, isReadyToRelocate, isReadyForRemoteWork);
    }

    @Override
    public String toString() {
        return "CV{" +
                "id=" + id +
                ", uuid='" + uuid + '\'' +
                ", user=" + user +
                ", image='" + image + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", position='" + position + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", isReadyToRelocate='" + isReadyToRelocate + '\'' +
                ", isReadyForRemoteWork='" + isReadyForRemoteWork + '\'' +
                '}';
    }
}