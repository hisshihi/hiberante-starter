package org.hiss.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Embeddable
@ToString
@EqualsAndHashCode
public class LocaleInfo {

    private String lang;
    private String description;

}
