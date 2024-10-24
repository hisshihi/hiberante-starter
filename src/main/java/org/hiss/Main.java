package org.hiss;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hiss.converter.BirthdayConverter;
import org.hiss.entity.Birthday;
import org.hiss.entity.Role;
import org.hiss.entity.User;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        // Конвертация для дня рождения
        configuration.addAttributeConverter(new BirthdayConverter());
        configuration.configure();

        try (SessionFactory sessionFactory = configuration.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            // Создаём сущность
            User user = User.builder()
                    .username("hiss1@gmail.com")
                    .firstname("Hiss")
                    .birthDate(new Birthday(LocalDate.of(2002, 10, 18)))
                    .lastname("Dev")
                    .role(Role.ADMIN)
                    .build();
            // Сохраняем её
            session.save(user);
            //Чтобы отправлять запросы в бд, нужно делать коммит вручную
            session.getTransaction().commit();
        }
    }
}