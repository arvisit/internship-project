package by.itacademy.profiler.persistence.model;

import by.itacademy.profiler.persistence.util.YearMonthDateAttributeConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.YearMonth;
import java.util.Objects;

@Builder(setterPrefix = "with")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
@Table(name = "awards")
public class Award {

    @Column(name = "title", length = 30, nullable = false )
    private String title;

    @Convert(converter = YearMonthDateAttributeConverter.class)
    @Column(name = "date")
    private YearMonth date;

    @Column(name = "issuer", length = 25)
    private String issuer;

    @Column(name = "description", length = 70)
    private String description;

    @Column(name = "link", length = 255)
    private String link;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Award award = (Award) o;
        return getTitle() != null && Objects.equals(getTitle(), award.getTitle())
                && getDate() != null && Objects.equals(getDate(), award.getDate())
                && getIssuer() != null && Objects.equals(getIssuer(), award.getIssuer())
                && getDescription() != null && Objects.equals(getDescription(), award.getDescription())
                && getLink() != null && Objects.equals(getLink(), award.getLink());
    }

    @Override
    public int hashCode() {
        return Objects.hash(title,
                date,
                issuer,
                description,
                link);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "title = " + title + ", " +
                "date = " + date + ", " +
                "issuer = " + issuer + ", " +
                "description = " + description + ", " +
                "Link = " + link + "}";
    }
}
