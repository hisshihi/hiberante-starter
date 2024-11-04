package org.hiss.entity;

import jakarta.persistence.Entity;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Manager extends User {

    private String projectName;

    @Builder
    public Manager(Long id, String username, PersonalInfo personalInfo, Role role, Company companyId, Profile profile, List<UserChat> userChats, String projectName) {
        super(id, username, personalInfo, role, companyId, profile, userChats);
        this.projectName = projectName;
    }
}
