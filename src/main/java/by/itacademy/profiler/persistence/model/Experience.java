package by.itacademy.profiler.persistence.model;

import by.itacademy.profiler.persistence.util.YearMonthDateAttributeConverter;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.ElementCollection;
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

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Builder(setterPrefix = "with")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "experience")
public class Experience {
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "industry_id", nullable = false)
    private Industry industry;

    @Column(name = "company", nullable = false, length = 40)
    private String company;

    @Column(name = "position", nullable = false, length = 40)
    private String position;

    @ElementCollection
    @CollectionTable(name = "duties", joinColumns = @JoinColumn(name = "experience_id"))
    @Column(name = "duty_name", nullable = false, length = 120)
    private List<String> duties = new ArrayList<>();

    @Column(name = "achievements", length = 200)
    private String achievements;

    @Column(name = "link")
    private String link;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Experience that = (Experience) o;

        if (!id.equals(that.id)) return false;
        if (!sequenceNumber.equals(that.sequenceNumber)) return false;
        if (!periodFrom.equals(that.periodFrom)) return false;
        if (!Objects.equals(periodTo, that.periodTo)) return false;
        if (!presentTime.equals(that.presentTime)) return false;
        if (!industry.equals(that.industry)) return false;
        if (!company.equals(that.company)) return false;
        if (!position.equals(that.position)) return false;
        if (!duties.equals(that.duties)) return false;
        if (!Objects.equals(achievements, that.achievements)) return false;
        return Objects.equals(link, that.link);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sequenceNumber, periodFrom, periodTo, presentTime,
                industry, company, position, duties, achievements, link);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Experience{");
        sb.append("id=").append(id);
        sb.append(", sequenceNumber=").append(sequenceNumber);
        sb.append(", periodFrom=").append(periodFrom);
        sb.append(", periodTo=").append(periodTo);
        sb.append(", presentTime=").append(presentTime);
        sb.append(", industry=").append(industry);
        sb.append(", company='").append(company).append('\'');
        sb.append(", position='").append(position).append('\'');
        sb.append(", duties=").append(duties.toString());
        sb.append(", achievements='").append(achievements).append('\'');
        sb.append(", link='").append(link).append('\'');
        sb.append('}');
        return sb.toString();
    }

}
