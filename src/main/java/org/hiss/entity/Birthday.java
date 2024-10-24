package org.hiss.entity;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public record Birthday(LocalDate birthday) {

    // Определение возраста
    public long getAge() {
        return ChronoUnit.YEARS.between(birthday, LocalDate.now());
    }

}
