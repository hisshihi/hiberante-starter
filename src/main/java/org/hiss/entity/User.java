package org.hiss.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "users", schema = "public")
public class User {

    @Id
    /*
    * GenerationType.AUTO - просто использует IDENTITY или другое, в зависимости от диалекта бд
    * GenerationType.IDENTITY - самая предпочтительная
    * GenerationType.SEQEUNCE - самый обычный счётчик(есть не во всех бд). Можно создать его самому
    *   @SequenceGenerator - для генерации счётчика
    *   @GeneratedValue(generator = "user_gen", strategy = GenerationType.SEQUENCE)
    *   @SequenceGenerator(name = "user_gen", sequenceName = "users_id_seq", allocationSize = 1)
    * GenerationType.TABLE - если бд не поддерживает предыдущие методы, т.е. для старых бд, не поддерживающих автогенирацию либо счётчики
    * */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    // Встраиваемый класс
    @Embedded
    // Переопеределение поля в встроенном классе
    @AttributeOverride(name = "birthDate", column = @Column(name = "birth_date"))
    private PersonalInfo personalInfo;

    // Добавил строковое представление роли пользователя
    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        User user = (User) o;
        return getUsername() != null && Objects.equals(getUsername(), user.getUsername());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
