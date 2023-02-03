package by.itacademy.profiler.persistence.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "contacts")
public class Contacts {

    @Id
    @Column(name = "cv_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "phone_code_id")
    private PhoneCode phoneCode;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column
    private String email;

    @Column
    private String skype;

    @Column
    private String linkedin;

    @Column(name = "portfolio_link")
    private String portfolio;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Contacts contacts)) return false;
        return Objects.equals(id, contacts.id) && Objects.equals(phoneCode, contacts.phoneCode) &&
                Objects.equals(phoneNumber, contacts.phoneNumber) && Objects.equals(email, contacts.email) &&
                Objects.equals(skype, contacts.skype) && Objects.equals(linkedin, contacts.linkedin) &&
                Objects.equals(portfolio, contacts.portfolio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, phoneCode, phoneNumber, email, skype, linkedin, portfolio);
    }

    @Override
    public String toString() {
        return "Contacts{" +
                "id=" + id +
                ", phoneCode=" + phoneCode +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", skype='" + skype + '\'' +
                ", linkedin='" + linkedin + '\'' +
                ", portfolio='" + portfolio + '\'' +
                '}';
    }
}
