package org.hiss.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
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
     * cascade = CascadeType.ALL - все операции совершаемые с команией также будут применимы к пользователям в этой компании
     * */
    @OneToMany(mappedBy = "companyId", cascade = CascadeType.ALL)
    @Builder.Default // Указываем, чтобы установились дефолтные значения
//    @JoinColumn(name = "company_id")
//    Чтобы избежать исключение на null, нужно использовать HasnSet(либо по случаю, чтобы при извлечении небыло пустых позиций)
    private Set<User> users = new HashSet<>();

    public void addUser(User user) {
        users.add(user);
        user.setCompanyId(this);
    }

}
