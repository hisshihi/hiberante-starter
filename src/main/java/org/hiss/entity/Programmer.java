package org.hiss.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@PrimaryKeyJoinColumn(name = "id") // Обозначаем, что связываем первычный ключ объекта с Супер классом
public class Programmer extends User {



    @Enumerated(EnumType.STRING)
    private Language language;

    @Builder
    public Programmer(Long id, String username, PersonalInfo personalInfo, Role role, Company companyId, Profile profile, List<UserChat> userChats, Language language) {
        super(id, username, personalInfo, role, companyId, profile, userChats);
        this.language = language;
    }
}
