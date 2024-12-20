package org.hiss.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SortNatural;
import org.hibernate.proxy.HibernateProxy;

import java.util.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    /*
     * mappedBy - орагнизация связи
     * cascade = CascadeType.ALL - все операции совершаемые с команией также будут применимы к пользователям в этой компании
     * */
    @OneToMany(mappedBy = "companyId", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default // Указываем, чтобы установились дефолтные значения
    @MapKey(name = "username") // Указываем, какое поле будет ключом
    @SortNatural
    private Map<String, User> users = new TreeMap<>();
    //    Обозначаем, что это не самостоятельная сущность
    @ElementCollection
    @Builder.Default
    @CollectionTable(name = "company_locale", joinColumns = @JoinColumn(name = "company_id"))
//    @AttributeOverride(name = "lang", column = @Column(name = "language"))
//    private List<LocaleInfo> locales = new ArrayList<>();
    // Используем эту таблицу только на чтение
    @Column(name = "description")
    @MapKeyColumn(name = "lang") // Для element collection
    private Map<String, String> locales = new HashMap<>();

    public void addUser(User user) {
        users.put(user.getUsername(), user);
        user.setCompanyId(this);
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Company company = (Company) o;
        return getId() != null && Objects.equals(getId(), company.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
