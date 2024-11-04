package org.hiss.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"company", "profile", "userChats"})
@EqualsAndHashCode(of = "username")
@Entity
@Table(name = "users", schema = "public")
//@Access(AccessType.FIELD)
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User implements Comparable<User>, BaseEntity<Long> {

//    @Id
    /*
     * GenerationType.AUTO - просто использует IDENTITY или другое, в зависимости от диалекта бд
     * GenerationType.IDENTITY - самая предпочтительная
     * GenerationType.SEQEUNCE - самый обычный счётчик(есть не во всех бд). Можно создать его самому
     *   @SequenceGenerator - для генерации счётчика
     *   @GeneratedValue(generator = "user_gen", strategy = GenerationType.SEQUENCE)
     *   @SequenceGenerator(name = "user_gen", sequenceName = "users_id_seq", allocationSize = 1)
     * GenerationType.TABLE - если бд не поддерживает предыдущие методы, т.е. для старых бд, не поддерживающих автогенирацию либо счётчики
     * */
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    // Встраиваемый класс
//    @EmbeddedId
    // Переопеределение поля в встроенном классе
    @AttributeOverride(name = "birthDate", column = @Column(name = "birth_date"))
    private PersonalInfo personalInfo;

    // Добавил строковое представление роли пользователя
    @Enumerated(EnumType.STRING)
    // Если не хотим отправлять и получать это поле в бд
//    @Transient
    private Role role;

    /*
    * optional = false - получаем NOT NULL
    * fetch = FetchType.LAZY - каким образом получаем данные
    * cascade = CascadeType.DETACH - удаление из сессии вместе с зависимым объектом
    * cascade = CascadeType.PERSIST - сохранение вместе с зависимым объектом
    *   (но лучше так не делать, потому что объект company уже должен существовать на момент сохранения пользователя)
    * */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id")
    private Company companyId;

//    Зависимая сущность профиля
    @OneToOne(
            cascade = CascadeType.ALL,
            mappedBy = "user",
            fetch = FetchType.LAZY)
    private Profile profile;

//    @Builder.Default
    @OneToMany(mappedBy = "user")
    private List<UserChat> userChats = new ArrayList<>();

    @Override
    public int compareTo(User o) {
        return username.compareTo(o.username);
    }
}
