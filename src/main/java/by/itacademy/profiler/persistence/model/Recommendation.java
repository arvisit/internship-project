package by.itacademy.profiler.persistence.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Builder(setterPrefix = "with")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "recommendations")
public class Recommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "full_name", length = 40)
    private String fullName;

    @Column(name = "company", length = 40)
    private String company;

    @Column(name = "position", length = 40)
    private String position;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "phone_code_id")
    private PhoneCode phoneCode;

    @Column(name = "phone_number", length = 25)
    private String phoneNumber;

    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "linked_in")
    private String linkedIn;

    @Column(name = "telegram", length = 50)
    private String telegram;

    @Column(name = "viber", length = 50)
    private String viber;

    @Column(name = "whats_app", length = 50)
    private String whatsApp;

    @Column(name = "recommendation")
    private String recommendations;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Recommendation that = (Recommendation) o;

        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(fullName, that.fullName)) return false;
        if (!Objects.equals(company, that.company)) return false;
        if (!Objects.equals(position, that.position)) return false;
        if (!Objects.equals(phoneCode, that.phoneCode)) return false;
        if (!Objects.equals(phoneNumber, that.phoneNumber)) return false;
        if (!Objects.equals(email, that.email)) return false;
        if (!Objects.equals(linkedIn, that.linkedIn)) return false;
        if (!Objects.equals(telegram, that.telegram)) return false;
        if (!Objects.equals(viber, that.viber)) return false;
        if (!Objects.equals(whatsApp, that.whatsApp)) return false;
        return Objects.equals(recommendations, that.recommendations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullName, company, position, phoneCode,
                phoneNumber, email, linkedIn, telegram, viber, whatsApp, recommendations);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Recommendation{");
        sb.append("id=").append(id);
        sb.append(", fullName='").append(fullName).append('\'');
        sb.append(", company='").append(company).append('\'');
        sb.append(", position='").append(position).append('\'');
        sb.append(", phoneCode=").append(phoneCode);
        sb.append(", phoneNumber='").append(phoneNumber).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", linkedIn='").append(linkedIn).append('\'');
        sb.append(", telegram='").append(telegram).append('\'');
        sb.append(", viber='").append(viber).append('\'');
        sb.append(", whatsApp='").append(whatsApp).append('\'');
        sb.append(", recommendations='").append(recommendations).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
