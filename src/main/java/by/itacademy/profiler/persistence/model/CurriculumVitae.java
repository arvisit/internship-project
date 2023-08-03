package by.itacademy.profiler.persistence.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
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

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private Contacts contacts;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private About about;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private AdditionalInformation additionalInformation;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private CvStatus status = CvStatus.DRAFT;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "cv_id")
    private List<CvLanguage> languages = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "cvs_skills",
            joinColumns = @JoinColumn(name = "cv_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id"))
    private List<Skill> skills = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "cv_id", nullable = false)
    private List<Experience> experience = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "cv_id", nullable = false)
    private List<MainEducation> mainEducations = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "cv_id", nullable = false)
    private List<Course> courses = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "cv_id", nullable = false)
    private List<Recommendation> recommendations = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CurriculumVitae that = (CurriculumVitae) o;

        if (!id.equals(that.id)) return false;
        if (!uuid.equals(that.uuid)) return false;
        if (!user.equals(that.user)) return false;
        if (!Objects.equals(image, that.image)) return false;
        if (!Objects.equals(name, that.name)) return false;
        if (!Objects.equals(surname, that.surname)) return false;
        if (!Objects.equals(position, that.position)) return false;
        if (!Objects.equals(country, that.country)) return false;
        if (!Objects.equals(city, that.city)) return false;
        if (!Objects.equals(isReadyToRelocate, that.isReadyToRelocate)) return false;
        if (!Objects.equals(isReadyForRemoteWork, that.isReadyForRemoteWork)) return false;
        if (!Objects.equals(contacts, that.contacts)) return false;
        if (!Objects.equals(about, that.about)) return false;
        if (status != that.status) return false;
        if (!Objects.equals(languages, that.languages)) return false;
        if (!Objects.equals(skills, that.skills)) return false;
        if (!Objects.equals(mainEducations, that.mainEducations)) return false;
        if (!Objects.equals(courses, that.courses)) return false;
        if (!Objects.equals(recommendations, that.recommendations)) return false;
        if (!Objects.equals(additionalInformation, that.additionalInformation)) return false;
        return Objects.equals(experience, that.experience);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, uuid, user, image, name, surname, position, country, city, isReadyToRelocate,
                isReadyForRemoteWork, contacts, about, status, languages, skills, experience, mainEducations, courses, recommendations, additionalInformation);
    }

    @Override
    public String toString() {
        return "CV{" +
                "id=" + id +
                ", uuid='" + uuid + '\'' +
                ", user=" + user +
                ", image=" + image +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", position=" + position.toString() +
                ", country=" + country +
                ", city='" + city + '\'' +
                ", isReadyToRelocate=" + isReadyToRelocate +
                ", isReadyForRemoteWork=" + isReadyForRemoteWork +
                ", contacts=" + contacts +
                ", about=" + about +
                ", languages=" + languages.toString() +
                ", skills=" + skills.toString() +
                ", experience=" + experience.toString() +
                ", mainEducations=" + mainEducations.toString() +
                ", courses=" + courses.toString() +
                ", recommendations=" + recommendations.toString() +
                ", additionalInformation=" + additionalInformation.toString() +
                ", status=" + status +
                '}';
    }
}