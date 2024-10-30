package org.hiss.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
@Entity
public class Profile {

    @Id
    @Column(name = "user_id")
    private Long id;
    private String street;
    private String language;

    @OneToOne
    @ToString.Exclude
    @PrimaryKeyJoinColumn
    private User user;

    public void setUser(User user) {
        user.setProfile(this);
        this.user = user;
        this.id = user.getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Profile profile = (Profile) o;
        return Objects.equals(id, profile.id) && Objects.equals(street, profile.street) && Objects.equals(language, profile.language);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, street, language);
    }
}
