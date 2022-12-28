package by.itacademy.profiler.persistence.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "phone_codes")
public class PhoneCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "code")
    private Integer code;

    @OneToOne
    @JoinColumn(name = "country_id", referencedColumnName = "id")
    private Country country;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PhoneCode phoneCode)) return false;
        return Objects.equals(id, phoneCode.id) && Objects.equals(code, phoneCode.code)
                && Objects.equals(country, phoneCode.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, country);
    }

    @Override
    public String toString() {
        return "PhoneCode{" +
                "id = " + id + ", " +
                "code = " + code + ", " +
                "country = " + country + '\'' +
                "}";
    }
}
