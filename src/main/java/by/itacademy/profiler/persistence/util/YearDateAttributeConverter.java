package by.itacademy.profiler.persistence.util;

import jakarta.persistence.AttributeConverter;

import java.sql.Date;
import java.time.Instant;
import java.time.MonthDay;
import java.time.Year;
import java.time.ZoneId;

import static by.itacademy.profiler.persistence.util.PersistenceConstants.EUROPE_MINSK;

public class YearDateAttributeConverter implements AttributeConverter<Year, Date> {

    @Override
    public Date convertToDatabaseColumn(Year attribute) {
        if (attribute != null) {
            return Date.valueOf(
                    attribute.atMonthDay(MonthDay.of(1, 1)));
        }
        return null;
    }

    @Override
    public Year convertToEntityAttribute(Date dbData) {
        if (dbData != null) {
            return Year.from(
                    Instant
                            .ofEpochMilli(dbData.getTime())
                            .atZone(ZoneId.of(EUROPE_MINSK))
                            .toLocalDate());
        }
        return null;
    }
}
