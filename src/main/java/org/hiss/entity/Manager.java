package org.hiss.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@PrimaryKeyJoinColumn(name = "id") // Обозначаем, что связываем первычный ключ объекта с Супер классом
public class Manager extends User {

    private String projectName;

    @Builder
    public Manager(Long id, String username, PersonalInfo personalInfo, Role role, Company companyId, Profile profile, List<UserChat> userChats, String projectName) {
        super(id, username, personalInfo, role, companyId, profile, userChats);
        this.projectName = projectName;
    }
}
