package org.hiss.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Embeddable
public class PersonalInfo {

    private String firstName;
    private String lastName;

    private Birthday birthDate;

}
