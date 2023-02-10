package by.itacademy.profiler.persistence.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "profiles")
public class UserProfile {

    @Id
    @Column(name = "user_id")
    private Long id;

    @Column
    private String name;

    @Column
    private String surname;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @Column(name = "email")
    private String email;

    @ManyToOne
    @JoinColumn(name = "phone_code_id")
    private PhoneCode phoneCode;

    @Column(name = "phone_number")
    private String cellPhone;

    @ManyToOne
    @JoinColumn(name = "position_id")
    private Position position;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "image_id", unique = true)
    private Image profileImage;

    @Column(name = "unique_student_identifier", length = 30)
    private String uniqueStudentIdentifier;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserProfile that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) &&
                Objects.equals(surname, that.surname) && Objects.equals(country, that.country) &&
                Objects.equals(email, that.email) && Objects.equals(phoneCode, that.phoneCode) &&
                Objects.equals(cellPhone, that.cellPhone) && Objects.equals(position, that.position) &&
                Objects.equals(profileImage, that.profileImage) &&
                Objects.equals(uniqueStudentIdentifier, that.uniqueStudentIdentifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, country, email, phoneCode, cellPhone, position, profileImage,
                uniqueStudentIdentifier);
    }
}