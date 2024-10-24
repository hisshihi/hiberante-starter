package org.hiss.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.hiss.entity.Birthday;

import java.sql.Date;
import java.util.Optional;

// Добавил авторматическую конвертацию для дня рождения
@Converter(autoApply = true)
public class BirthdayConverter implements AttributeConverter<Birthday, Date> {

    @Override
    public Date convertToDatabaseColumn(Birthday birthday) {
        return Optional.ofNullable(birthday)
                .map(Birthday::birthday)
                .map(Date::valueOf)
                .orElse(null);
    }

    @Override
    public Birthday convertToEntityAttribute(Date date) {
        return Optional.ofNullable(date)
                .map(Date::toLocalDate)
                .map(Birthday::new)
                .orElse(null);
    }

}
