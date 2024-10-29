package org.hiss.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@ToString(exclude = "users")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "users")
@Builder
@Entity
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    /*
     * mappedBy - орагнизация связи
     * */
    @OneToMany(mappedBy = "companyId")
//    @JoinColumn(name = "company_id")
    private Set<User> users;

}
